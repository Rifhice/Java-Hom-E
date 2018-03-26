package sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
class ServerSensor {
	
	SensorClient clientSensor;
	
	public ServerSensor(int port,SensorClient clientSensor) {
		try {
			this.clientSensor = clientSensor;
			String fromClient;
	        ServerSocket server = new ServerSocket(port);
	
	        System.out.println("wait for connection on port " + port);
	        boolean run = true;
	        while(run) {
	            Socket client = server.accept();
	            System.out.println("got connection on port 8080");
	            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
	            
	            while(true) {
		            fromClient = in.readLine();
		            System.out.println("received: " + fromClient);
		            clientSensor.handleMessageFromSensor(fromClient);
	            }
	            
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void main(String args[]) throws Exception {

    }
}