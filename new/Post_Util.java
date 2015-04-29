package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/23/2015.
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

    private String userID;
    PostActivity frontend;

    public Post_Util(String Name, String userID,PostActivity pos)
    {
        super(Name);
        this.userID = userID;
        frontend = pos;
    }

    void savePostAndRetrievePosts(String text, Context con, boolean is_annonymous, String temperature){
        Log.d("user id ",userID);
        Log.d("textfrom function ", text);
        Log.d("isAnnonymous", String.valueOf(is_annonymous));
        Log.d("temperature", String.valueOf(temperature));
        SyncWithServer poster = new SyncWithServer();
        poster.setUp(text,is_annonymous,temperature,con);
        poster.execute();
    }

    private class SyncWithServer extends AsyncTask<Void, Void, String> {

        String text;
        boolean is_anonymous;
        String temperature;
        private Context con;


        protected String ParseDataIntoString(HttpEntity entity) throws IllegalStateException, IOException {
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

        private void setUp(String text, boolean isChecked, String temperature, Context con) {
            this.text = text;
            is_anonymous = isChecked;
            this.temperature = temperature;
            this.con = con;
        }

        private String getUsername(int userID) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            for (int i = 9; i < 14; i++) {
                String path = "http://cs578.roohy.me/user/";
                path = path + Integer.toString(i) + "/";
                System.out.println("path is " + path);
                HttpGet httpGet = new HttpGet(path);
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = ParseDataIntoString(entity);
                    try {
                        JSONObject obj = new JSONObject(text);
                        String id = obj.getString("uid");
                        System.out.println("id is " + id + " and userID is " + userID);
                        if (id.equals(Integer.toString(userID))) {
                            System.out.println("found match and name is " + obj.getString("name").substring(0,obj.getString("name").indexOf("#")));
                            return obj.getString("name").substring(0,obj.getString("name").indexOf("#"));
                        }
                    } catch (JSONException e) {
                        System.out.println("in another exception");
                    }
                }
                catch (Exception e) {
                    System.out.println("in an exception");
                    e.getLocalizedMessage();
                }
            }
            return null;
        }

        protected String doInBackground(Void... params) {
            String output = "Post Failed";
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();

            //try {

                /*
                 As we only have ~50 statuses before we run out of server space, let's keep the
                 actual posting functionality commented out to save space. You will see that I already
                 have 4 posts for user9 for you to work with. So for now, the only functionality running
                 is getting the data from the server for you to display graphically. You can see the posts at
                 http://cs578.roohy.me/status/list/9/
                 */
                /*String path = "http://cs578.roohy.me/status/list/";
                path = path + userID + "/";
                HttpPost request = new HttpPost(path);
                System.out.println("path is " + path);
                request.addHeader("content-type", "application/json");
                request.addHeader("Accept","application/json");
                JSONObject obj = new JSONObject();

                obj.put("text", text);
                obj.put("info", "Anonym:" + (is_anonymous ? "True" : "False") + ",Temperature:" + ((temperature == null) ? "N/A" : temperature));
                obj.put("user", userID);

                StringEntity jsonString = null;
                jsonString = new StringEntity(obj.toString());
                request.setEntity(jsonString);
                HttpResponse resp = httpClient.execute(request);
                if (resp.getStatusLine().getStatusCode() != 200 && resp.getStatusLine().getStatusCode() != 201) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + resp.getStatusLine().getStatusCode());
                }*/

            output = "Posted Successfully";
            //} catch (JSONException e) {
            //    e.printStackTrace();
            //} catch (UnsupportedEncodingException e) {
            //    e.printStackTrace();
            //} catch (ClientProtocolException e) {
            //    e.printStackTrace();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}

            JSONArray allPosts = new JSONArray();
            TreeMap<Integer,String> userIdtoName = new TreeMap<Integer,String>();
            /*
             Issue HTTP GET request to get all posts written and pass their data back to frontend
             */
            for (int i = 9; i < 14; i++) {
                String path = "http://cs578.roohy.me/status/list/";
                path = path + Integer.toString(i) + "/";
                HttpGet httpGet = new HttpGet(path);
                try {
                    HttpResponse response = httpClient.execute(httpGet, localContext);
                    HttpEntity entity = response.getEntity();
                    text = ParseDataIntoString(entity);
                    JSONArray jsonData = new JSONArray(text);
                    // If a user has no posts, there's no point in passing nothing to the frontend
                    if (jsonData.length() > 0) {
                        //frontend.updatePosts(jsonData,getUsername(i));
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
            System.out.println("allPosts size is " + allPosts.length());
            for (Integer i : userIdtoName.keySet()) {
                System.out.print("i = " + i.intValue());
                System.out.println(", username is " + userIdtoName.get(i));
            }
            frontend.updatePosts(allPosts,userIdtoName);

            return output;
        }

        protected void onPostExecute(String results) {
            Toast.makeText(con, results,
                    Toast.LENGTH_SHORT).show();
        }
    }

}

