package com.example.rahulagarwal.trojannowfl; /**
 * Created by rmu on 4/13/2015.
 *
 * Description: Component superclass with attributes and methods used by all Components
 */
import java.util.*;

public class Component extends Thread {

    private String name; // name of the component
    protected List<Message> events; // list of all messages (requests/notifications) sent/received

    // Default constructor
    public Component() {
        name = "No name";
        events = new ArrayList<Message>();
        System.out.println("Component " + name + " created");
    }

    // Constructor where one can specify name of the component
    public Component(String name) {
        this.name = name;
        events = new ArrayList<Message>();
        System.out.println("Component " + this.name + " created");
    }

    // Constructor where one can specify anything else in addition to the component's name
    public Component(String name, String junk) {
        this.name = name;
        events = new ArrayList<Message>();
        System.out.println("Component " + name + " created and along with " + junk);
    }

    // returns the name of the component
    public String get_Name() {
        return name;
    }

    // checks to see if there are any requests or notifications which the Component has yet to handle
    public boolean anyMessages(String connectorName) {
        for (Message m : events) {
            if (m.isFinished() == 0 && m.getConnectorName().equals(connectorName)) {
                return true;
            }
        }
        return false;
    }

    // Run this Component's thread
    public void run() {
        // Kicks off any modules a component needs to execute
        System.out.println("Component " + name + " thread running");
    }
}
