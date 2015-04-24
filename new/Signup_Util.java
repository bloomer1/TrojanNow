package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.util.ArrayList;

public class Signup_Util extends Component {

    // ADDED by RICHARD MU 4/13/15: constructor
    public Signup_Util(String name) {
        super(name);
    }

    //Signs up the user
    public  void signUP(String uname, String pword) {
        SyncWithServer adder = new SyncWithServer();
        adder.addUserPass(uname+pword);
        adder.execute();
    }

    private class SyncWithServer extends AsyncTask<Void, Void, String> {

        private ArrayList<String> url = new ArrayList<String>();
        protected int UserId = 0;
        private String UserPass;
        private String emptyName;

        public void addUserPass(String UserPass) {
            this.UserPass = UserPass;
            String serverUrl;
            for (int i = 9; i < 14; i++) {
                serverUrl = "http://cs578.roohy.me/user/";
                url.add(serverUrl + Integer.toString(i) + "/");
            }
        }

        protected String ParseDataIntoString(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n;
            byte []buffer = new byte[256];
            n = in.read(buffer);
            out.append(new String(buffer, 0 ,n));
            return out.toString();
        }

        @Override
        //runs in bckground thread...
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
                    text = ParseDataIntoString(entity);
                    try {
                        JSONObject obj = new JSONObject(text);
                        String name = obj.getString("name");
                        if (name.equals(emptyName)) {
                            System.out.println("Found empty element at i = " + (i+9));
                            UserId = i+9;
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        //runs in ui/main thread
        protected void onPostExecute(String results) {

            if (results.equals("found")) {
                System.out.println("User successfully added");
            } else {
                System.out.println("User NOT added");
            }
        }
    }
}
