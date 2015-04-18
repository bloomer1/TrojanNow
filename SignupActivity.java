package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.util.*;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONStringer;;
import android.os.AsyncTask;
import android.view.View.OnClickListener;

/**
 * Created by rmu on 4/13/2015.
 */
public class SignupActivity extends Activity {


    private EditText username=null;
    private EditText password=null;
    private EditText email = null;
    private Button register;
    public String uname;
    public String pword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });

        username = (EditText)findViewById(R.id.reg_username);
        password = (EditText)findViewById(R.id.reg_password);
        email = (EditText)findViewById(R.id.reg_email);





    }

    public void register(View view){
        Log.i("Rahul",username.getText().toString());
        Log.i("Rahul", password.getText().toString());
        Log.i("Rahul",email.getText().toString());
        uname = username.getText().toString();
        pword = password.getText().toString();
        SyncWithServer adder = new SyncWithServer();
        adder.addUserPass(uname+pword);
        adder.execute();
    }

    private class SyncWithServer extends AsyncTask <Void, Void, String> {

        private ArrayList<String> url = new ArrayList<String>();
        protected int UserId = 0;
        private String UserPass;
        private String emptyName;

        public void addUserPass(String UserPass) {
            this.UserPass = UserPass;
            String serverUrl;
            for (int i = 9; i < 14; i++) {
                serverUrl = "http://cs578.roohy.me/user/";
                url.add(serverUrl+Integer.toString(i)+"/");
            }
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

            String output = "not found";
            String text = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                HttpGet httpGet = new HttpGet(url.get(i));
                emptyName = "user";
                emptyName = emptyName + Integer.toString(i+9);
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = getASCIIContentFromEntity(entity);
                    try {
                        JSONObject obj = new JSONObject(text);
                        String name = obj.getString("name");
                        if (name.equals(emptyName)) {
                            System.out.println("Found empty element at i = " + (i+9));
                            UserId = i+9;
                            break;
                        }
                    } catch (JSONException e) {

                    }
                }
                catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }

            try {

                // COMMENT OUT THIS IF YOU WANT TO RESET ALL VALUES
                // BACK TO THE DEFAULT OF user9, user10, ..., user13
                String sURL = "http://cs578.roohy.me/user/";
                sURL = sURL + Integer.toString(UserId) + "/";
                HttpPut putRequest = new HttpPut(sURL);

                // UNCOMMENT THIS IF YOU WANT TO RESET ALL VALUES
                // BACK TO THE DEFAULT OF user9, user10, ..., user13
                // JUST MAKE SURE TO SPECIFY WHICH USER (9, 10, ..., 13) you want to set back to default
                //HttpPut putRequest = new HttpPut("http://cs578.roohy.me/user/9/");

                putRequest.addHeader("Content-Type", "application/json");
                putRequest.addHeader("Accept", "application/json");
                JSONObject keyArg = new JSONObject();

                // COMMENT OUT THIS IF YOU WANT TO RESET ALL VALUES
                // BACK TO THE DEFAULT OF user9, user10, ..., user13
                keyArg.put("pk", UserId);
                keyArg.put("uid", UserId);
                keyArg.put("name", UserPass);

                // UNCOMMENT THIS IF YOU WANT TO RESET ALL VALUES
                // BACK TO THE DEFAULT OF user9, user10, ..., user13
                // JUST MAKE SURE TO SPECIFY WHICH USER you want to set back to default
                //keyArg.put("pk", 9);
                //keyArg.put("uid", 9);
                //keyArg.put("name", "user9");

                StringEntity input = null;
                try {
                    input = new StringEntity(keyArg.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                putRequest.setEntity(input);
                HttpResponse response = httpClient.execute(putRequest);
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }
                output = "found";
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }
        protected void onPostExecute(String results) {

            if (results.equals("found")) {
                System.out.println("User successfully added");
            } else {
                System.out.println("User NOT added");
            }
        }
    }

}
