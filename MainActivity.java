package com.example.rmu.csci_578finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private EditText  username=null;
    private EditText password=null;
    private TextView attempts;
    private Button login;
    // int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        //attempts = (TextView)findViewById(R.id.textView5);
        //attempts.setText(Integer.toString(counter));
        login = (Button)findViewById(R.id.button1);

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
        Component SU = new Signup_Util("Signup_Util");
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
        arch.addComponent(SU);
        arch.addComponent(UI);
        arch.addComponent(V);
        arch.addComponent(WS);

        Printer("TEST: Now start the architecture environment");

        // Start the components and connectors
        arch.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void login(View view) throws Exception{
        Login_Util lu = new Login_Util("Login_Util");
        lu.login(view,getApplicationContext(),username,password,login);
    }

    public void signup(View view){
        Intent i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void Printer(String str) {
        System.out.println();
        System.out.println("*********************************************");
        System.out.println(str);
        System.out.println("*********************************************");
        System.out.println();
    }
}