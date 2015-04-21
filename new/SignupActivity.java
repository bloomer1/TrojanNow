package com.example.rahulagarwal.trojannowfl2;

/**
 * Created by rmu on 4/15/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.*;

/**
 * Created by rmu on 4/13/2015.
 */
public class SignupActivity extends Activity {


    private EditText username=null;
    private EditText password=null;
    private EditText email = null;
    private Button register;
    public String uname;
    public String pword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });

        username = (EditText)findViewById(R.id.reg_username);
        password = (EditText)findViewById(R.id.reg_password);
        email = (EditText)findViewById(R.id.reg_email);
    }

    public void register(View view){
        Log.i("Rahul",username.getText().toString());
        Log.i("Rahul", password.getText().toString());
        Log.i("Rahul",email.getText().toString());

        uname = username.getText().toString();
        pword = password.getText().toString();

        Signup_Util signup = new Signup_Util("SignUp_Util");

        signup.signUP(uname,pword);//connector
    }

}
