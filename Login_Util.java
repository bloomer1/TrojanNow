package com.example.rahulagarwal.trojannowfl;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rmu on 4/13/2015.
 */
public class Login_Util extends Component {

    

//    private EditText username=null;
//    private EditText password=null;
//    private TextView attempts;
//    private Button login;
//    private int counter = 3;

    // ADDED by RICHARD MU 4/13/15: constructor
    public Login_Util(String Name)
    {
        super(Name);
    }

    //logs the user in the application
    public void login(View view, Context con, EditText username, EditText password, Button login) {



        if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Toast.makeText(con, "Redirecting...",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(con, "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            //attempts.setBackgroundColor(Color.RED);
            //counter--;
            //attempts.setText(Integer.toString(counter));
            //if (counter == 0) {
              //  login.setEnabled(false);
            //}


        }

    }
    //logs out the user from the application

    public void logout(){

    }


}
