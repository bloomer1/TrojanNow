package com.example.rahulagarwal.trojannowfl2; /**
 * Created by rmu on 4/13/2015.
 */
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class PostActivity extends Activity{

    //long postId;


    private EditText  etext=null;
    private Button post;
    private boolean is_annonymous = false;
    private boolean is_temperature = false;
    CheckBox annonymous,temperature;
    //private String text;
    //private

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




    public void clickpost(View view){

        is_annonymous = annonymous.isChecked();
        is_temperature = temperature.isChecked();
        Log.d("Rahul",String.valueOf(is_annonymous));
        Log.d("Rahul", String.valueOf(is_temperature));
        Log.d("Rahul", etext.getText().toString());
        String text = etext.getText().toString();
        String temperature = null;


        Toast.makeText(getApplicationContext(), "posted...",
                Toast.LENGTH_SHORT).show();

        //get tempearue data

        if(is_temperature == true){


        }
        Toast.makeText(getApplicationContext(), "posted...",
                Toast.LENGTH_SHORT).show();
        savePostAndRetrievePosts(text,is_annonymous,temperature);
    }


    //Added by rahulagarwal
    //Method stub for saving current post and Retrieve all posts on the server
    //1-1 connector for Post User Story
    void savePostAndRetrievePosts(String text, boolean is_annonymous, String temperature){




    }


    //Adds a post
    void addPost(String content, boolean isAnnonymous){



    }

    //Deletes a Post
    void removePost(String content){

    }
}
