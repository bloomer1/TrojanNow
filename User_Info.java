package com.example.rahulagarwal.trojannowfl; /**
 * Created by rmu on 4/13/2015.
 */
import java.util.*;

public class User_Info extends Component {

    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String contactno;
    private String ip_address;
    private String location;
    private List<User_Posts> user_posts;

    public User_Info(String name) {
        super(name);
        ip_address = "127.0.0.1";
        user_posts = new ArrayList<User_Posts>();
    }

    public String Get_IP() {
        return ip_address;
    }

    public String Get_Username() {
        return username;
    }

    public String Get_Fname() {
        return first_name;
    }

    public String Get_Lname() {
        return last_name;
    }

    public String Get_Name() {
        return first_name + " " + last_name;
    }

    public String Get_Location() {
        return location;
    }

    public List<User_Posts> Get_UserPosts() {
        return user_posts;
    }

    //registers a user for the app
    public void register() {
        //calls
    }
    //logs in an user
    public void login(){

    }
    //logs out an user
    public void logout(){

    }
    //access Sensory information

    public void accessSensorInfo(){


    }
    //shares stauts information of the user
    public void shareStatus(){

    }
    //deletes an already posted status
    public void deleteStatus(){

    }
}
