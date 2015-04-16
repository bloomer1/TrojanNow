package com.example.rahulagarwal.trojannowfl; /**
 * Created by rmu on 4/13/2015.
 */
import java.util.Date;

public class Post {

    long postId;
    String content;
    Date timestamp;
    String location;
    boolean isAnnonymous;

    // ADDED by RICHARD MU 4/13/15: this way, we can construct a new post dynamically
    // We may also have to make several constructors if needed
    public Post() {

    }

    //Adds a post
    void addPost(String content, boolean isAnnonymous){



    }

    //Deletes a Post
    void removePost(String content){

    }
}
