package com.example.rahulagarwal.trojannowfl2;

/**
 * Created by rmu on 4/23/2015.
 * Purpose: Backend Component which processes user posts and places them in the backend server
 * In addition, this component is responsible for pulling the previously written posts from the
 * backend and displaying those in the frontend
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.TreeMap;

public class Post_Util extends Component {

    private String userID; // the userID of the user logged in
    PostActivity frontend; // the corresponding frontend component (GUI) which displays the posts

    // constructor
    public Post_Util(String Name, String userID,PostActivity pos)
    {
        super(Name);
        this.userID = userID;
        frontend = pos;
    }

    // RPC Connector from the frontend component PostActivity which calls this component
    // when the user hits the Post button
    void savePostAndRetrievePosts(String text, Context con, boolean is_annonymous, String temperature){
        Log.d("user id ",userID);
        Log.d("textfrom function ", text);
        Log.d("isAnnonymous", String.valueOf(is_annonymous));
        Log.d("temperature", String.valueOf(temperature));
        // connector which communicates with the backend to update it with new posts
        // and pull previous posts from it
        SyncWithServer poster = new SyncWithServer();
        poster.setUp(text,is_annonymous,temperature,con);
        poster.execute();
    }

    /*
     * Connector which communicates with the server to update it with new posts
     * as well as pull posts previous posts to be displayed
     */
    private class SyncWithServer extends AsyncTask<Void, Void, String> {

        String text;
        boolean is_anonymous; // true if the anonymous checkbox is checked
        String temperature; // N/A if the temperature checkbox is not checked
        private Context con;

        /*
         * Parses the data returned from the REST call and processes it into
         * a string, which can then be converted to a JSON object.
         */
        protected String ParseDataIntoString(HttpEntity entity) throws IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n;
            byte []buffer = new byte[4096];
            n = in.read(buffer);
            if (n < 0) {
                return null;
            }
            out.append(new String(buffer, 0 ,n));
            return out.toString();
        }

        /*
         * set variables given if their corresponding checkboxes are checked
         */
        private void setUp(String text, boolean isChecked, String temperature, Context con) {
            this.text = text;
            is_anonymous = isChecked;
            this.temperature = temperature;
            this.con = con;
        }

        /*
         * Since we want to display the username who posted rather than the userID, this
         * function will pull the username from the backend given the userID
         */
        private String getUsername(int userID) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            for (int i = 9; i < 14; i++) {
                String path = "http://cs578.roohy.me/user/";
                path = path + Integer.toString(i) + "/";
                HttpGet httpGet = new HttpGet(path);
                try {
                    // Issue the REST GET call to the server to get the JSON for a particular user
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    // convert returned response into a string
                    text = ParseDataIntoString(entity);
                    try {
                        // now convert this string into a JSON object
                        JSONObject obj = new JSONObject(text);
                        String id = obj.getString("uid");
                        // if we find a JSON whose uid is equal to the provided user ID,
                        // we know that this is the user, so we return the username,
                        // which is the substring in the name field before the '#' delimiter
                        if (id.equals(Integer.toString(userID))) {
                            return obj.getString("name").substring(0,obj.getString("name").indexOf("#"));
                        }
                    } catch (JSONException e) {
                        return e.getLocalizedMessage();
                    }
                }
                catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }
            return null;
        }

        /*
         * Main function of the connector. This one will convert the user post
         * into a JSON object, and pass that object to the backend via an HTTP POST
         * REST call. In addition, this function will pull previously posted user posts
         * from the backend server via an HTTP GET REST call so they can be displayed
         */
        protected String doInBackground(Void... params) {
            String output = "Post Failed";
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            try {

                // form the url to which the HTTP POST call will be made
                String path = "http://cs578.roohy.me/status/list/";
                path = path + userID + "/";
                HttpPost request = new HttpPost(path);
                request.addHeader("content-type", "application/json");
                request.addHeader("Accept","application/json");
                JSONObject obj = new JSONObject();

                // fill the JSON object post details.
                obj.put("text", text);
                obj.put("info", "Anonym:" + (is_anonymous ? "True" : "False") + ",Temperature:" + ((temperature == null) ? "N/A" : temperature));
                obj.put("user", userID);

                StringEntity jsonString = null;
                jsonString = new StringEntity(obj.toString());
                request.setEntity(jsonString);
                // Actual REST call to POST data to server
                HttpResponse resp = httpClient.execute(request);
                if (resp.getStatusLine().getStatusCode() != 200 && resp.getStatusLine().getStatusCode() != 201) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + resp.getStatusLine().getStatusCode());
                }

            output = "Posted Successfully";
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // now we will extract all previously made posts as JSON objects and display them
            JSONArray allPosts = new JSONArray();
            TreeMap<Integer,String> userIdtoName = new TreeMap<Integer,String>();
            /*
             Issue HTTP GET request to get all posts written and pass their data back to frontend
             */
            for (int i = 9; i < 14; i++) {
                String path = "http://cs578.roohy.me/status/list/";
                path = path + Integer.toString(i) + "/";
                // search all users' accounts for posts they've made.
                HttpGet httpGet = new HttpGet(path);
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = ParseDataIntoString(entity);
                    JSONArray jsonData = new JSONArray(text);
                    // If a user has no posts, there's no point in passing nothing to the frontend
                    if (jsonData.length() > 0) {
                        userIdtoName.put(i,getUsername(i));
                        for (int j = 0; j < jsonData.length(); j++) {
                            System.out.println("putting in pk " + jsonData.getJSONObject(j).getString("pk"));
                            allPosts.put(jsonData.getJSONObject(j));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Now that we have gathered all the posts as JSON objects, display the posts to
            // the frontend component
            frontend.updatePosts(allPosts,userIdtoName);

            return output;
        }

        protected void onPostExecute(String results) {
            Toast.makeText(con, results,
                    Toast.LENGTH_SHORT).show();
        }
    }

}

