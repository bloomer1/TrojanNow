/**
 * Created by rmu on 4/13/2015.
 *
 * Description: Subclass of Message that deals only with notifications (messages sent in response
 * to a request received earlier)
 */

public class Notification extends Message{

    // Default constructor
    public Notification() {
        super();
    }

    // Constructor that also specifies the name of the notification message
    public Notification(String Name) {
        super(Name);
    }
}
