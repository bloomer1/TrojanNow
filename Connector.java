package com.example.rahulagarwal.trojannowfl; /**
 * Created by rmu on 4/13/2015.
 *
 * Description: Connector superclass with attributes and methods used by all Connectors
 */
import java.util.*;

public class Connector extends Thread {

    private String name; // name of the connector
    protected List<Component> topComponents; // list of components that send requests via this connector
    protected List<Component> bottomComponents; // list of components that send notifications via this connector

    // Default constructor
    public Connector() {
        name = "No name";
        topComponents = new ArrayList<Component>();
        bottomComponents = new ArrayList<Component>();
        System.out.println("Connector " + name + " created");
    }

    // Constructor which specifies the name of the connector
    public Connector(String name) {
        this.name = name;
        topComponents = new ArrayList<Component>();
        bottomComponents = new ArrayList<Component>();
        System.out.println("Connector " + this.name + " created");
    }

    // returns name of the connector
    public String get_Name() {
        return name;
    }

    // Add a component that will send requests/receive notifications via this connector
    public void addComponentBottom(Component comp) {
        bottomComponents.add(comp);
    }

    // Add a component that will send notifications/receive requests via this connector
    public void addComponentTop(Component comp) {
        topComponents.add(comp);
    }

    // Run this connector's thread
    public void run() {
        // Kicks off any modules a connector needs to execute
        System.out.println("Connector " + name + " thread running");
    }

    // handle any messages that are created by components attached to this connector
    public void handle() {

    }
}
