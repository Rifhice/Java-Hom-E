package sensor;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class SensorConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static String DEFAULT_HOST = "localhost";
  final public static int DEFAULT_PORT = 1112;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  SensorClient client;

  
  //Constructors ****************************************************

  
  public SensorConsole(String host, int port) 
  {
    try 
    {
      client= new SensorClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Erreur: Impossible d'installer la connexion !"
                + " Client terminé.");
      System.exit(1);
    }
  }
  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Erreur inattendue lors de la lecture de la console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }
  
  public void handleMessage(String message) {
	  System.out.println(message);
  }
  
  public SensorClient getClient() {
	  return client;
  }
  
  //Class methods ***************************************************
  
  public static void main(String[] args) {  
	  /*
	  SensorConsole server = new SensorConsole(DEFAULT_HOST, DEFAULT_PORT);
	  ContinuousVariable v1 = new ContinuousVariable("Temperature", "La temperature", "°C", 0, 20, 1, 15);
	  DiscreteVariable v2 = new DiscreteVariable("Temperature", "La temperature 2", "N/A", new ArrayList<String>(Arrays.asList("Low","Medium","High")), "Low");
	  server.client.setSensor(new Sensor(server.getClient(), "zpfzef", "dzeezhfhzfh", new ArrayList<Variable>(Arrays.asList(v1,v2))));
	  */
	  SensorConsole server = new SensorConsole(DEFAULT_HOST, DEFAULT_PORT);
	  //ContinuousVariable v1 = new ContinuousVariable("Temperature", "La temperature", "°C", 0, 20, 1, 15);
	  DiscreteValue v2 = new DiscreteValue(new ArrayList<String>(Arrays.asList("true","false")), "false");
	  EnvironmentVariable ev = new EnvironmentVariable("Presence", "Presence de quelqu'un dans la piece", "N/A",v2);
	  System.out.println(ev);
	  server.client.setSensor(new Sensor(server.getClient(), "Presence", "Putain ca marche !", ev));
  }
}
//End of ConsoleChat class
