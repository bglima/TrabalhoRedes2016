import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Class that act as server in our application
 */
public class Server {
	ServerSocket server = null;
	Socket client = null;
	BufferedReader in = null;
	int port = 4321;
	public void listenSocket() {
		// Trying to open a socket in specified port
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not listen from port " + port);
			System.out.println("Is this already being used?");
			System.exit(-1);
		}
		// Waiting for some client to connect
		try {
			System.out.println("Listening to server...");
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed from "+port+".");
			System.exit(-1);			
		}
		// Create a input file to read client messages
		try {
			in = new BufferedReader( new InputStreamReader (client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Read failed from client...");
			System.exit(-1);
		}	
		// After first data arrives, do the following
		while(true){
			try{
				Thread.sleep(2000);
				String line = in.readLine();
		        System.out.println("Message arrived: "+line);
		  } catch (IOException e) {
				System.out.println("Read failed from client...");
		  } catch (InterruptedException e) {
				e.printStackTrace();
		  }
	   }
	}
	public static void main(String[] args) {
		Server myServer = new Server();
		myServer.listenSocket();
	}
}

