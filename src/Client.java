import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * Class that serve as client
 */
public class Client {
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String host = "127.0.0.1";	// also known as localhost
	public void listenSocket(){
	   //Create socket connection
	   try{
	     socket = new Socket(host, 4321);
	     out = new PrintWriter(socket.getOutputStream(), true);
	   } catch (UnknownHostException e) {
	     System.out.println("Unknown host: "+host);
	     System.exit(1);
	   } catch  (IOException e) {
	     System.out.println("No I/O");
	     System.exit(1);
	   }
	   // Keep sending the same message to the server, every one 1 second
	   while(true) {
		   out.println("Hello, server! I am the client.");
		   try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	   }
	}
	
	public static void main(String [] args) {
		Client myClient = new Client();
		myClient.listenSocket();
	}
}
