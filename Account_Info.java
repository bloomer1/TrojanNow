package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import java.util.Date;
import java.util.*;
import java.lang.*;

public class Account_Info extends Component {

    long accID;
    Date lastLogin;
    long totalPost;
    List<Post> postHistory;

    // ADDED by RICHARD MU 4/13/15: constructor
    public Account_Info(String name) {
        super(name);
        totalPost = 0;
        postHistory = new ArrayList<Post>();
    }

    //returns accountId of the user
    long getID(){

        return accID;
    }

    // gives count of total posts of the user
    public long getTotalPost(){

        return totalPost;
    }
    //get all the post of the user
    public List<Post> getAllPost(){

        return postHistory;
    }
}
