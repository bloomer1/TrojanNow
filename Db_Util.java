package com.example.rmu.csci_578finalproject;

/**
 * Created by rmu on 4/15/2015.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.lang.*;

public class Db_Util extends Component {

    //store data in the Database

    Connection con;

    // ADDED by RICHARD MU 4/13/15: constructor
    public Db_Util(String name) {
        super(name);
    }

    void storeData(){

    }

    // extract data from the database

    ResultSet extractData(){

        ResultSet rs = null;
        return rs;
    }

    public  static void openConnection(){


    }

    public static void closeConnection(){

    }
}
