package APP;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import PLAYER.Player;

/*
 * Class that serve as client
 */
public class Client {
	private String ip; // = "127.0.0.1";
	private int port;  // = 4321;
	private Socket socket;
	private Player player;
	private MessageReceiver messageReceiver;
	private PrintStream out;	
	public Client(String ip, int port){
		this.ip = ip;
		this.port = port;
	}	
	// Set the player for this client
	public void setPlayer(Player player) { this.player = player; }
	public Player getPlayer() { return player; }
	// 
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
		out.println(player.getPlayerName() + ", " + player.getCharSet() + ": " +  message);
	}
	
	// Send a player control message to server
	public void sendControlMessage(String controlMessage) {
		out.println("control:"+controlMessage);
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
