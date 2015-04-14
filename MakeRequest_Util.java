/**
 * Created by rmu on 4/13/2015.
 */
import org.json.JSONObject;
import java.net.URL;

public class MakeRequest_Util extends Component {

    String serverAddress;
    URL requestString;
    JSONObject requestData;
    JSONObject responseData;

    // ADDED by RICHARD MU 4/13/15: constructor
    public MakeRequest_Util(String name) {
        super(name);
    }

    //sends the client request to the server
    void sendRequest(){

    }

    //gets response form the server
    void getResponse(){

    }

    /**
     * Any confguration required for sending request to the server
     */
    void configure(){

    }
}
