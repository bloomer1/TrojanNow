package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by rmu on 4/13/2015.
 */
public class Login_Util extends Component {

//    private EditText username=null;
//    private EditText password=null;
//    private TextView attempts;
//    private Button login;
//    private int counter = 3;
      public String uname;
      public String pword;
      public String loginInfo;
      public Context context;

    // ADDED by RICHARD MU 4/13/15: constructor
    public Login_Util(String Name)
    {
        super(Name);
    }

    //logs the user in the application
    public void login(View view, Context con, EditText username, EditText password, Button login) throws Exception {

        context = con;
        uname = username.getText().toString();
        pword = password.getText().toString();
        loginInfo = uname+pword;
        checkLogin(con);
        /*if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Toast.makeText(con, "Redirecting...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(con, "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            //attempts.setBackgroundColor(Color.RED);
            //counter--;
            //attempts.setText(Integer.toString(counter));
            //if (counter == 0) {
            //  login.setEnabled(false);
            //}


        }*/

    }

    public void checkLogin(Context con) {

        SyncWithServer checker = new SyncWithServer();
        String serverUrl;
        for (int i = 9; i < 14; i++) {
            serverUrl = "http://cs578.roohy.me/user/";
            checker.addUrl(serverUrl+Integer.toString(i)+"/");
        }
        checker.addContext(con);
        checker.execute();
    }

    //logs out the user from the application

    public void logout(){

    }

    private class SyncWithServer extends AsyncTask <Void, Void, String> {

        private ArrayList<String> url = new ArrayList<String>();
        private Context con;
        public void addUrl(String url) {
            this.url.add(url);
        }

        public void addContext(Context con) {
            this.con = con;
        }

        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n;
            byte []buffer = new byte[256];
            n = in.read(buffer);
            out.append(new String(buffer, 0 ,n));
            return out.toString();
        }
        @Override
        protected String doInBackground(Void... params) {
            String text = null;
            String output = "not found";
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            for (int i = 0; i < 5; i++) {
                HttpGet httpGet = new HttpGet(url.get(i));
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = getASCIIContentFromEntity(entity);
                    try {
                        JSONObject obj = new JSONObject(text);
                        String name = obj.getString("name");
                        if (loginInfo.equals(name)) {
                            output = "found";
                            break;
                        }
                    } catch (JSONException e) {

                    }
                }
                catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }

            return output;
        }
        protected void onPostExecute(String results) {

            if (results.equals("found")) {
                Toast.makeText(con, "Redirecting...",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(con, "Wrong Credentials",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
