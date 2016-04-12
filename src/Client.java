import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * Class that serve as client
 */
public class Client {
	public static void main(String [] args) throws UnknownHostException, IOException {
		String ip = "127.0.0.1";
		int port = 4321;	
		
		// Create a socket for the client
		Socket socket = new Socket(ip, port);
		System.out.println("Client has connected to server!");
		
		// Create a thread to handle server messages
		MessageReceiver messageReceiver = new MessageReceiver(socket.getInputStream());
		Thread serverListener = new Thread(messageReceiver);
		serverListener.start();
		
		// Create a scanner for the keyborad input
		Scanner keyboard = new Scanner(System.in);	
		// Instantiate an output stream for the client
		PrintStream out = new PrintStream(socket.getOutputStream());		
		// While user is typing and pressing enter
		while (keyboard.hasNextLine()) {
			out.println(keyboard.nextLine());
		}		
		
		// Close  stream, scanner and client connection
		out.close();
		keyboard.close();
		socket.close();
	}
}
