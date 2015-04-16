package com.example.rahulagarwal.trojannowfl;

/**
 * Created by rmu on 4/13/2015.
 *
 * Description: Subclass of Message that deals only with requests (messages initially sent from one component
 * to another)
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
