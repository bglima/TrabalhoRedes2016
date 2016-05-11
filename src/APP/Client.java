package APP;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

/*
 * Class that serve as client
 */
public class Client {
	private String ip; // = "127.0.0.1";
	private int port; // = 4321;
	private Socket socket;
	private String name;
	private String hero;
	private MessageReceiver messageReceiver;
	private PrintStream out;	
	public Client(String ip, int port, String name, String hero){
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.hero = hero;
	}	
	// Estabilish connection with server
	public boolean connectClient() {
		// Create a socket for the client
		try {
			// Create a socket
			socket = new Socket(ip, port);
			// Instantiate an output stream for the client
			out = new PrintStream(socket.getOutputStream());				
		} catch (UnknownHostException e) {
			System.out.println("Unknown hostname: "+this.ip);
			return false;
		} catch (IOException e) {
			System.out.println("Couldn't get I/O for: "+this.ip);
			return false;
		}	
		System.out.println("Client has connected to server!");
		return true;
	}
	// Send a message to server:
	public void sendMessage(String message){
		out.println(name + ", " + hero + ": " +  message);
	}
	// Close connection with Server
	public void disconnectClient() {
		out.close();
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	// Get the socket
	public Socket getSocket() {
		return socket;
	}
}
