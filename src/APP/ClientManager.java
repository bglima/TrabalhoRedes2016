package APP;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import MAP.TileMap;

public class ClientManager implements Runnable {
	private InputStream clientStream;
	private PrintStream outputStream;
	private Server server;	

	public ClientManager(InputStream clientStream, PrintStream outputStream, Server server) {
		this.clientStream = clientStream;
		this.outputStream = outputStream;
		this.server = server;
	}
	
	public void run() {
		Scanner scanner = new Scanner(this.clientStream);
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			this.server.showMessage(nextLine);
			
			// Check if message is a control message to proccess it
			String delimiters = ":|,";
			String [] tokens = nextLine.split(delimiters);
			if(tokens[0].equals("control")) {
				switch( tokens[1] ) {
					case "addPlayer":
						System.out.println("Adding "+tokens[2]+" to online players list.");
						server.addPlayer(tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), outputStream);
						break;
					case "moveRight":
						System.out.println("Moving "+tokens[2]+" right. Target X,Y = "+tokens[3]+","+tokens[4]);
						server.movePlayer("right", tokens[2]);
						break;
					case "moveUp":
						System.out.println("Moving "+tokens[2]+" up. Target X,Y = "+tokens[3]+","+tokens[4]);
						server.movePlayer("right", tokens[2]);
						break;
					case "moveLeft":
						System.out.println("Moving "+tokens[2]+" left. Target X,Y = "+tokens[3]+","+tokens[4]);
						server.movePlayer("left", tokens[2]);
						break;
					case "moveDown":
						System.out.println("Moving "+tokens[2]+" down. Target X,Y = "+tokens[3]+","+tokens[4]);
						server.movePlayer("down", tokens[2]);
						break;
				}
			}
		}
		scanner.close();
	}
}
