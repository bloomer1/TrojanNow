package com.example.rahulagarwal.trojannowfl2;

/**
 * Created by rmu on 4/15/2015.
 */
import java.util.*;

public class Component {

    private String name; // name of the component

    // Default constructor
    public Component() {
        name = "No name";
        System.out.println("A component has been created");
    }

    // Constructor where one can specify name of the component
    public Component(String name) {
        this.name = name;
        System.out.println("Component " + this.name + " created");
    }

    // Constructor where one can specify anything else in addition to the component's name
    public Component(String name, String junk) {
        this.name = name;
        System.out.println("Component " + name + " created and along with " + junk);
    }

    // returns the name of the component
    public String get_Name() {
        return name;
    }
}