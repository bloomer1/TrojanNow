package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import java.util.*;
import java.lang.*;

public class User_Posts extends Component {

    private String  sensorType;
    private String username; // username of user who wrote post
    private List<Post> posts; // list of posts by this user

    // Constructor which specifies name of the component
    public User_Posts(String name) {
        super(name);
        posts = new ArrayList<Post>();
    }

    // add a new post
    public void Add_Post(Message M) {

    }

    // get a post based on its id
    public Post Get_Post(int id) {
        return posts.get(id);
    }

    // delete a post based on its id
    public void Delete_Post(int id) {
        posts.remove(id);
    }

    // delete a post based on its content
    public void Delete_Post(String post) {
        posts.remove(post);
    }

    // set a post to have new text
    public void Set_Post(int id, String newText) {

    }

    //retrieves sensor info from Android sensor API
    void getSensorInfo(){

    }
}
