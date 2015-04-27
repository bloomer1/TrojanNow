package com.example.rahulagarwal.trojannowfl2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.rahulagarwal.trojannowfl2.model.Weather;


public class PostActivity extends Activity{



    TextView postView,postView2;
    LinearLayout l1;
    final TextView[] myTextViews = new TextView[50];
    LinearLayout linearLayout;
    int a, b,c,d;

    private String userID;
    private EditText  etext=null;
    private Button post;
    private boolean is_annonymous = false;
    private boolean is_temperature = false;
    CheckBox annonymous,temperature;
    String text;
    String opentemperature = null;
    boolean threadDone = false;


    // ADDED by RICHARD MU 4/13/15: this way, we can construct a new post dynamically
    // We may also have to make several constructors if needed


    //Constructor
    public PostActivity() {

    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post);

        etext = (EditText)findViewById(R.id.text_content);
        annonymous = (CheckBox) findViewById(R.id.user_annonymous);
        temperature = (CheckBox) findViewById(R.id.user_weather);

        Intent previous = getIntent();
        userID = previous.getStringExtra("userID");
    }


    /**
     Added by rahhulagarwal
     */
    public void clickpost(View view){

        is_annonymous = annonymous.isChecked();
        is_temperature = temperature.isChecked();
        Log.d("Rahul",String.valueOf(is_annonymous));
        Log.d("Rahul", String.valueOf(is_temperature));
        Log.d("Rahul", etext.getText().toString());
        String text = etext.getText().toString();

        Post_Util posts = new Post_Util("Post_Util", userID, this);

        if(is_temperature == true){
            GetCoord gc = new GetCoord(PostActivity.this);
            String []gcoord  = new String[2];
            if(gc.canGetLocation()) {

                gcoord[0] = String.valueOf(gc.getLatitude());
                gcoord[1] = String.valueOf(gc.getLongitude());

                Log.d("Latitude",gcoord[0]);
                Log.d("Longitude",gcoord[1]);

            }

            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{gcoord[0],gcoord[1]});
            //while(opentemperature == null);
        } else {
            if(is_temperature == false){
                opentemperature = null;
            }
        }
        posts.savePostAndRetrievePosts(text, getApplicationContext(), is_annonymous, opentemperature);
    }

    //added by rahulagarwal
    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0],params[1]));
            Log.d("Rahulinweather", data);

            try {
                weather = JSONWeatherParser.getWeather(data);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }




        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);


            Log.d("Temperature ",Math.round((weather.temperature.getTemp() - 273.15)) + " C" );
            opentemperature = Math.round((weather.temperature.getTemp() - 273.15)) + " C";
            Log.d("opentemperature", opentemperature);






        }








    }

    //Added by rahulagarwal
    //Method stub for saving current post and Retrieve all posts on the server
    //1-1 connector for Post User Story
    /*void savePostAndRetrievePosts(String text, boolean is_annonymous, String temperature){



        Log.d("textfrom function ", text);
        Log.d("isAnnonymous", String.valueOf(is_annonymous));
        Log.d("temperature", String.valueOf(temperature));


    }*/


    /*
     ADDED BY RICHARD MU: PLEASE USE THE DATA FROM HERE TO DISPLAY ALL POSTS ON THE GUI!
     THIS FUNCTION IS CALLED ANYTIME A USER DOES A POST AND THE BACKEND READS THE DATA BACK
     NOTE THIS ARRAY HAS ALREADY BEEN SORTED BY TIME OF POST, WITH LATEST POSTS PRINTED FIRST
     */
    void updatePosts(JSONArray jsonData) {




        int []arr = {R.id.p1,R.id.p2,R.id.p3,R.id.p4,R.id.p5,
                R.id.p6,R.id.p7,R.id.p8,R.id.p9,R.id.p10,
                R.id.p11,R.id.p12,R.id.p13,R.id.p14,R.id.p15,
                R.id.p16,R.id.p17,R.id.p18,R.id.p19,R.id.p20,
                R.id.p21,R.id.p22,R.id.p23,R.id.p24,R.id.p25,
                R.id.p26,R.id.p27,R.id.p28,R.id.p29,R.id.p30,
                R.id.p31,R.id.p32,R.id.p33,R.id.p34,R.id.p35,
                R.id.p36,R.id.p37,R.id.p38,R.id.p39,R.id.p40,
                R.id.p41,R.id.p42,R.id.p43,R.id.p44,R.id.p45,
                R.id.p46,R.id.p47,R.id.p48,R.id.p49,R.id.p50
        };


        for (int i = jsonData.length()-1; i >= 0; i--) {
            System.out.println("-------------------------------------------------------");
            try {


                JSONObject onePost = jsonData.getJSONObject(i);
                String info = onePost.getString("info");
                String [] data = info.split(",");
                String anon = data[0].substring(data[0].indexOf(":")+1);
                String temp = data[1].substring(data[1].indexOf(":")+1);
                System.out.println("User ID: " + onePost.getString("user"));
                System.out.println("Post: " + onePost.getString("text"));
                System.out.println("Anonymous: " + anon);
                System.out.println("Temperature: " + temp);
                System.out.println("PostID: " + onePost.getString("pk"));

                try {




                    postView = (TextView) findViewById(arr[i]);

                    postView.setText(onePost.getString("text"));

                }
                catch(Exception e){
                    e.printStackTrace();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        System.out.println("-------------------------------------------------------");
    }




    //Adds a post
    void addPost(String content, boolean isAnnonymous){



    }

    //Deletes a Post
    void removePost(String content){

    }
}
