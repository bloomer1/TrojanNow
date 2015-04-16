package com.example.rahulagarwal.trojannowfl;

/**
 * Created by rmu on 4/13/2015.
 *
 * Description: This is the class that actually executes the skeleton code
 *
 * Responsibility: Execute!
 */

public class Run {

    public static void main(String [] args) {

        Printer("TEST: Setup the architecture environment");

        // Setup the architecture environment
        Architecture arch = new Architecture();

        Printer("TEST: Setup the components");

        // Create all components
        Component AI = new Account_Info("Account_Info");
        Component DB = new Db_Util("Db_Util");
        Component DS = new Dispatcher("Dispatcher");
        Component LU = new Login_Util("Login_Util");
        Component MU = new MakeRequest_Util("MakeRequest_Util");
        Component SN = new Sensor("Sensor");
       // Component SU = new SignupActivity("Signup_Util");
        Component UI = new User_Info("User_Info");
        Component UP = new User_Posts("User_Posts");
        Component V = new User_Validation("User_Validation");
        Component WS = new Weather_Service("Weather_Service");

        // add components to the running environment
        arch.addComponent(AI);
        arch.addComponent(DB);
        arch.addComponent(DS);
        arch.addComponent(LU);
        arch.addComponent(MU);
        arch.addComponent(SN);
        arch.addComponent(UP);
        //arch.addComponent(SU);
        arch.addComponent(UI);
        arch.addComponent(V);
        arch.addComponent(WS);

        Printer("TEST: Now start the architecture environment");

        // Start the components and connectors
        arch.start();
    }

    public static void Printer(String str) {
        System.out.println();
        System.out.println("*********************************************");
        System.out.println(str);
        System.out.println("*********************************************");
        System.out.println();
    }
}
