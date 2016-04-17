package APP;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Class that act as server in our application
 */
public class Server {
	private int port = 4321;
	private List <PrintStream> clientStreamList = new ArrayList <PrintStream> ();
	
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
			// Print client IP
			System.out.println("New connection with client " + client.getInetAddress().getHostAddress());
			// Add client output stream to list
			PrintStream ps = new PrintStream(client.getOutputStream());
			clientStreamList.add(ps);
			// Create a new ClientManager for current client
			ClientManager manager = new ClientManager(client.getInputStream(), this);
			// And runs it in another thread
			Thread thread = new Thread(manager);
			thread.start();
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

