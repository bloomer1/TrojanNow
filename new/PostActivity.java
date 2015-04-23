package com.example.rahulagarwal.trojannowfl2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;

import com.example.rahulagarwal.trojannowfl2.model.Weather;


public class PostActivity extends Activity{




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



            savePostAndRetrievePosts(text, is_annonymous, opentemperature);



        }

             else {
                 if(is_temperature == false){
                     opentemperature = null;
                 }
             savePostAndRetrievePosts(text, is_annonymous, opentemperature);

         }

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

          //  savePostAndRetrievePosts(text, is_annonymous, opentemperature);


            Toast.makeText(getApplicationContext(), "posted...",
                    Toast.LENGTH_SHORT).show();

        }








    }

    //Added by rahulagarwal
    //Method stub for saving current post and Retrieve all posts on the server
    //1-1 connector for Post User Story
    void savePostAndRetrievePosts(String text, boolean is_annonymous, String temperature){



    Log.d("textfrom function ", text);
    Log.d("isAnnonymous", String.valueOf(is_annonymous));
    Log.d("temperature", String.valueOf(temperature));


    }















    //Adds a post
    void addPost(String content, boolean isAnnonymous){



    }

    //Deletes a Post
    void removePost(String content){

    }
}
