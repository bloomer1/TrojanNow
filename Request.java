package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
public class Request extends Message {

    // Default constructor
    public Request() {
        super();
    }

    // Constructor that specifies the name of the request
    public Request(String Name) {
        super(Name);
    }
}
