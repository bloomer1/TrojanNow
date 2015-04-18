package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class Post extends Activity{

    long postId;
    String content;
    Date timestamp;
    String location;
    boolean isAnnonymous;

    // ADDED by RICHARD MU 4/13/15: this way, we can construct a new post dynamically
    // We may also have to make several constructors if needed



    public Post() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post);








    }
    //Adds a post
    void addPost(String content, boolean isAnnonymous){



    }

    //Deletes a Post
    void removePost(String content){

    }
}
