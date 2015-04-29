package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 * Purpose: Backend Component which processes user login info and redirects
 * user to posts page or tells user that they have invalid credentials
 */
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.*;
import java.util.*;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.os.AsyncTask;

/**
 * Created by rmu on 4/13/2015.
 */
public class Login_Util extends Component {

    public String userloginID; // the user ID of the user attempting to login (assuming valid user)
    public String uname; // the user name that was passed in
    public String pword; // the password that the user passed in
    public String loginInfo; // the login information that is then passed to the server
    public Context context; // the login context (of the login page)

    // constructor
    public Login_Util(String Name)
    {
        super(Name);
    }

    //logs the user in the application by checking if the username and password are valid
    public void login(View view, Context con, EditText username, EditText password, Button login) throws Exception {

        context = con;
        uname = username.getText().toString(); // get username from login page's username field
        pword = password.getText().toString(); // get password from login page's password field
        loginInfo = uname+"#"+pword; // store the username and password provided with a '#' in between to match with how username and password are stored in the server
        checkLogin(con); // do the actual checking of username and password in server
    }

    /*
     * Performs the checking of user name and password by comparing what was passed in to every
     * possible user in the server.
     */
    public void checkLogin(Context con) {

        // Initialize the connector to the server, which is an AsyncTask object
        // that issues REST calls to the backend to access necessary info
        SyncWithServer checker = new SyncWithServer();
        String serverUrl;

        // First, we add all the possible user REST url's to be searched
        // to see if any match with the provided username and password
        for (int i = 9; i < 14; i++) {
            serverUrl = "http://cs578.roohy.me/user/";
            checker.addUrl(serverUrl+Integer.toString(i)+"/");
        }
        checker.addContext(con);
        // Now we execute the connector
        checker.execute();
    }

    /*
     * Connector which communicates with the server to extract user login info
     * If a match is found, we log the user in, otherwise, we tell him/her that they
     * do not have the correct credentials
     */
    private class SyncWithServer extends AsyncTask <Void, Void, String> {

        private ArrayList<String> url = new ArrayList<String>();
        private Context con;
        public void addUrl(String url) {
            this.url.add(url);
        }

        public void addContext(Context con) {
            this.con = con;
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
         * Main function of the connector. This one will convert the REST response
         * into a JSON object, and check if one of the login/password info for each user
         * in the system is equal to that which was provided.
         */
        @Override
        protected String doInBackground(Void... params) {
            String text = null;
            String output = "not found";
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            for (int i = 0; i < 5; i++) {
                // Use the URLs provided by the checkLogin which contain all the users
                HttpGet httpGet = new HttpGet(url.get(i));
                try {
                    // Issue the REST GET call to the server to get the JSON for a particular user
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    // convert returned response into a string
                    text = ParseDataIntoString(entity);
                    try {
                        // now convert this string into a JSON object
                        JSONObject obj = new JSONObject(text);
                        String name = obj.getString("name");
                        // check the JSON name field to see if it is equal to the given
                        // login and password info
                        if (loginInfo.equals(name)) {
                            userloginID = Integer.toString(i+9);
                            output = "found";
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

            return output;
        }
        // This function takes the result of doInBackground and
        // either redirects the user to Posts page or prints a statement
        // saying that the user has invalid credentials
        protected void onPostExecute(String results) {

            if (results.equals("found")) {

                Intent i = new Intent(con, PostActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("userID",userloginID);
                con.startActivity(i);
            } else {
                Toast.makeText(con, "Wrong Credentials",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
