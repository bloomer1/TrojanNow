/**
 * Created by rmu on 4/13/2015.
 *
 * Description: Class that encapsulates the attributes and functionality of how information is sent
 * between components through connectors.
 */
import java.util.*;

public class Message {

    private String name; // name of this message to be sent (e.g. Account Creation, write post, get temperature, etc)
    private String connectorName; // name of the connector through which this message will travel and be handled
    private int status; // status of the message (e.g. not handled, handled/finished)
    Map<String, Object> parameters; // mappings of all parameter names and their values contained within a message

    // Default constructor
    public Message() {
        status = 0;
        parameters = new HashMap<String, Object>();
    }

    // Constructor which also specifies the name of the message
    public Message(String name) {
        this.name = name;
        status = 0;
        parameters = new HashMap<String, Object>();
    }

    // Constructor which specifies both the name of the message as well as the connector it's being handled by
    public Message(String name, String connectorName) {
        this.name = name;
        this.connectorName = connectorName;
        parameters = new HashMap<String, Object>();
    }

    // return the name of the message
    public String getName() {
        return name;
    }

    // return the connector which will be handling the message
    public String getConnectorName() {
        return connectorName;
    }

    // add a parameter name and value(s) to the message
    public void addParameter(String parameterName, Object parameterValue) {
        parameters.put(parameterName, parameterValue);
    }

    // denote this message as having been handled
    public void markAsFinished() {
        status = 1;
    }

    // check to see if the message has been handled
    public int isFinished() {
        return status;
    }

    // check if a particular parameter name and value(s) exist in the message
    public boolean hasParameter(String Name) {
        return parameters.containsKey(Name);
    }

    // remove a particular parameter name and value(s) from the message
    public void removeParameter(String Name) {
        parameters.remove(Name);
    }

    // retrieve a parameter from the message
    public Object getParameter(String Name) {
        return parameters.get(Name);
    }
}
