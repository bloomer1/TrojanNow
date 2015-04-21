package com.example.rahulagarwal.environment;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;


import android.app.Activity;
import android.content.Context;


import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity /*implements SensorEventListener*/{

    //private SensorManager mSensorManager;
    private SensorManagerSimulator mSensorManager;
    private SensorEventListener mEventListenerTemperature;
   // private Sensor mTemperature;
    private TextView text;
    private StringBuilder msg = new StringBuilder(2048);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
       // mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
       // mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mSensorManager.connectSimulator();
        text = (TextView) findViewById(R.id.text);

      initLis();

    }

    private void initLis() {

        mEventListenerTemperature = new SensorEventListener() {

            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                text.setText("Temperature: " + values[0]);
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }







    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        //Register the listener for the Sensor Service

        mSensorManager.registerListener(mEventListenerTemperature, mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_FASTEST);



    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        // Be sure to unregister the sensor when the activity pauses.


    }
    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(mEventListenerTemperature);
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // The activity is about to be destroyed.





    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        text.setText("Loading temperature value....");


        Context context = getApplicationContext();

        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



/*
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float fahrenheit = sensorEvent.values[0] * 9 / 5 + 32;
        Context context = getApplicationContext();

        CharSequence text = "Hello toast!";
        text = String.valueOf(fahrenheit);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Log.d("rmu", String.valueOf(fahrenheit));
        msg.insert(0, "Got a sensor event: " + sensorEvent.values[0] + " Celsius (" +
                fahrenheit  + " F)\n");
        Log.d("Rahul11", "Activity created");
        //text.setText(String.valueOf(msg));

        //text.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    */
}
