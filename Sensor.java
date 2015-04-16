package com.example.rahulagarwal.trojannowfl;

/**
 * Created by rmu on 4/13/2015.
 *
 * Description: Component that collects info about the physical surroundings for which the user's
 * mobile device is in.
 */
public class Sensor extends Component {

    private String  sensorType;
    private double temperature; // temperature as measured by device API
    private int humidity; // humidity as measured by device API
    private int wind; // wind speed as measure by device API

    // Constructor which allows one to specify the name of the sensor
    public Sensor(String name) {
        super(name);
        temperature = 75;
        humidity = 65;
        wind = 5;
    }

    // return the temperature
    public double Get_Temp() {
        return temperature;
    }

    // return the humidity
    public int Get_Humid() {
        return humidity;
    }

    // return the wind speed
    public int Get_Wind() {
        return wind;
    }
}
