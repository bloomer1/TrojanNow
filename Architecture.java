/**
 * Created by rmu on 4/13/2015.
 */
import java.util.*;

public class Architecture {

    private String name; // name of environment
    private List<Component> componentList; // all components in environment
    private List<Connector> connectorList; // all connectors in environment

    // Default constructor
    public Architecture() {
        this.name = "";
        componentList = new ArrayList<Component>();
        connectorList = new ArrayList<Connector>();
        System.out.println("TrojaNow environment has been setup");
    }

    // Constructor where name of environment is specified
    public Architecture(String name) {
        this.name = name;
        componentList = new ArrayList<Component>();
        connectorList = new ArrayList<Connector>();
        System.out.println("TrojaNow environment " + this.name + " has been setup");
    }

    // Adding a component to the environment
    public void addComponent(Component comp) {
        componentList.add(comp);
        System.out.println("Component " + comp.get_Name() + " has been added to the environment " + name);
    }

    // Adding a connector to the environment
    public void addConnector(Connector conn) {
        connectorList.add(conn);
        System.out.println("Connector " + conn.get_Name() + " has been added to the environment " + name);
    }

    // Removing a component from the environment
    public void removeComponent(Component comp) {
        componentList.remove(comp);
        System.out.println("Component " + comp.get_Name() + " has been removed from the environment " + name);
    }

    // Removing a connector from the environment
    public void removeConnector(Connector conn) {
        connectorList.remove(conn);
        System.out.println("Connector " + conn.get_Name() + " has been removed from the environment " + name);
    }

    // Attaching a bottom component (i.e. one that receives a request and issues a notification/response) to a connector
    public void attach(Connector conn, Component comp) {
        conn.addComponentBottom(comp);
        System.out.println("Component " + comp.get_Name() + " has been added as a bottom component to the " + conn.get_Name() + " connector");
    }

    // Attaching a top component (i.e. one that issues a request and receives a notification/response) to a connector
    public void attach(Component comp, Connector conn) {
        conn.addComponentTop(comp);
        System.out.println("Component " + comp.get_Name() + " has been added as a top component to the " + conn.get_Name() + " connector");
    }

    // start all components and connectors (each represented as a thread)
    public void start() {
        for (Component comp : componentList) {
            comp.start();
        }
        for (Connector conn : connectorList) {
            conn.start();
        }
    }

    // Where all the code will be executed during runtime
    public void run() {
        // Write all Java listeners, events, and handlers HERE!

    }

    // Simple helper function for finding a component by name in the List of components
    private Component Find(String compName) {
        for (Component comp : componentList) {
            if (comp.get_Name().compareTo(compName) == 0) {
                return comp;
            }
        }
        return null;
    }

    public void Printer(String str) {
        System.out.println();
        System.out.println("*********************************************");
        System.out.println(str);
        System.out.println("*********************************************");
        System.out.println();
    }
}
