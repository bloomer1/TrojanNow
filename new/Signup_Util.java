package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 * Purpose: Backend component which signs a new user up by passing all their input
 * into the backend server to be saved when the user logs in again
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

    // constructor
    public Signup_Util(String name) {
        super(name);
    }

    // RPC Connector from the corresponding frontend component
    // which is invoked when the user passes in signup credentials.
    public  void signUP(String uname, String pword) {
        // Initialize connector
        SyncWithServer adder = new SyncWithServer();
        // pass the username and password the user made to the connector
        adder.addUserPass(uname+"#"+pword);
        adder.execute();
    }

    /*
     * connector with the backend which sends a REST call to save all user-input
     * to backend server
     */
    private class SyncWithServer extends AsyncTask<Void, Void, String> {

        private ArrayList<String> url = new ArrayList<String>();
        protected int UserId = 0;
        private String UserPass; // user name and password passed in from the sign up page
        private String emptyName;

        public void addUserPass(String UserPass) {
            this.UserPass = UserPass;
            String serverUrl;
            for (int i = 9; i < 14; i++) {
                serverUrl = "http://cs578.roohy.me/user/";
                url.add(serverUrl + Integer.toString(i) + "/");
            }
        }

        /*
         * Parses the data returned from the REST call and processes it into
         * a string, which can then be converted to a JSON object.
         */
        protected String ParseDataIntoString(HttpEntity entity) throws IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n;
            byte []buffer = new byte[256];
            n = in.read(buffer);
            out.append(new String(buffer, 0 ,n));
            return out.toString();
        }

        /*
         * Main function of the connector. This one will convert the user signup
         * info into a JSON object, and make an HTTP POST REST call which will
         * save the info to the backend server
         */
        protected String doInBackground(Void... params) {

            String output = "not found";
            String text = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            /*
             * first, we search the backend server to see where in the
             * server the new user info will be stored. This will
             * be the new user's userID.
             */
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
                        // Once we find an empty spot, we make that the userID
                        if (name.equals(emptyName)) {
                            UserId = i+9;
                            break;
                        }
                    } catch (JSONException e) {
                        return e.getLocalizedMessage();
                    }
                }
                catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }

            try {
                /*
                 * now place user info into the backend server by first
                 * forming the URL which will be used by REST to
                 * execute an HTTP POST
                 */
                String sURL = "http://cs578.roohy.me/user/";
                sURL = sURL + Integer.toString(UserId) + "/";
                HttpPut putRequest = new HttpPut(sURL);

                putRequest.addHeader("Content-Type", "application/json");
                putRequest.addHeader("Accept", "application/json");
                JSONObject keyArg = new JSONObject();

                /*
                 * Second, place the contents of the signup into a JSON object
                 * which will be POSTed
                 */
                keyArg.put("pk", UserId);
                keyArg.put("uid", UserId);
                keyArg.put("name", UserPass);


                StringEntity input = null;
                try {
                    input = new StringEntity(keyArg.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                putRequest.setEntity(input);
                // Execute actual HTTP POST REST call
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
