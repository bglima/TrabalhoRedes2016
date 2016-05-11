package APP;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import MAP.TileMap;
import PLAYER.Player;

/*
 * Class that act as server in our application
 */
public class Server {
	private int port = 4321;
	private List <PrintStream> clientStreamList = new ArrayList <PrintStream> ();
	private Map <String, PrintStream> streamMap = new HashMap<String, PrintStream>();
	private Map <String, Player> playersMap = new HashMap<String, Player>();
	
	public Server(int port) {
		this.port = port;
	}
	
	public void start() throws IOException {
		// Initiating serverSocket
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Listening to port "+this.port+"...");	
		// Main loop for new client arrival
		while(true)
		{
			// Listening to possible clients
			Socket client = serverSocket.accept();
			// Add client output stream to list
			PrintStream ps = new PrintStream(client.getOutputStream());
			clientStreamList.add(ps);
			// Create a new ClientManager for current client
			ClientManager manager = new ClientManager(client.getInputStream(), ps, this);
			// And runs it in another thread
			Thread thread = new Thread(manager);
			thread.start();
		}
	}
	
	public void addPlayer(String playerName, String charSet, int x, int y, int moveDirection, PrintStream playerStream) {
		Player newPlayer = new Player(null, playerName, charSet, x, y, moveDirection);
		playersMap.put(playerName, newPlayer);
		streamMap.put(playerName, playerStream);
		sendPlayerList(playerName);
	}
	
	// Each time a player moves, its data is's manually updated 
	//  So that we can keep track of players positions.
	// It's necessary beaceuse a new player must know those who
	//  are already connected.
	public void movePlayer(String direction, String playerName) {
		Player player = playersMap.get(playerName);
		switch( direction ) {
			case "right":
				player.setMoveDirection(1);
				player.setX( player.getX() + player.getSize());
				break;
			case "up":
				player.setMoveDirection(2);
				player.setY( player.getY() - player.getSize());
				break;
			case "left":
				player.setMoveDirection(3);
				player.setX( player.getX() - player.getSize());
				break;
			case "down":
				player.setMoveDirection(4);
				player.setY( player.getY() + player.getSize());
				break;
		}
	}
	
	// Message sent to client at first time connecting
	// Has as purpose, to inform wich players are online at server.
	public void sendPlayerList(String playerName) {
		PrintStream playerStream = streamMap.get(playerName);
		playerStream.println("Sending you information about other players...");
		for ( String name : playersMap.keySet() ) {
			Player player = playersMap.get(name);
			playerStream.println("control:"+
								"addPlayer"+","+
								 player.getPlayerName()+","+
								 player.getCharSet()+","+
								 player.getX()+","+
								 player.getY()+","+
								 player.getMoveDirection()
			);
		}
	}
	
	public void showMessage(String nextLine) {
		// Show message at server screen
		System.out.println("Message from client has arrived: "+nextLine);
		// Send message for each client in list
		for( PrintStream clientStream : clientStreamList ) {
			clientStream.println(nextLine);
		}
	}
	
	public static void main(String [] args) {
		Server server = new Server(4321);
		try {
			server.start();
		} catch (IOException e) {
			System.out.println("Not possible to start server. Is port "+server.port+" free?");
		}
	}
}

