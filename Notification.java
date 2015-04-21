package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import java.lang.*;

public class Notification extends Message{

    // Default constructor
    public Notification() {
        super();
    }

    // Constructor that also specifies the name of the notification message
    public Notification(String Name) {
        super(Name);
    }
}
