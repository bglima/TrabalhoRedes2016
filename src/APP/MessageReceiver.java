package APP;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JTextArea;

import GUI.GamePanel;
import PLAYER.Player;

public class MessageReceiver implements Runnable {
	// Server input strem
	private InputStream inputStream;
	private JTextArea messageArea;
	private GamePanel gamePanel;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public MessageReceiver(InputStream inputStream, JTextArea messageArea, GamePanel gamePanel) {
		this.inputStream = inputStream;	
		this.messageArea = messageArea;
		this.gamePanel = gamePanel;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void run() {
		Scanner s = new Scanner(this.inputStream);
		System.out.println("Reading server messages: ");
		while(s.hasNextLine()) {
			String nextLine = s.nextLine();
			
			// Check if message is a control message
			String delimiters = ":|,";
			String [] tokens = nextLine.split(delimiters);

			// If it's a common text
			if( ! tokens[0].equals("control")) {
				System.out.println(nextLine);
				messageArea.append(nextLine+"\n");
			} 
			// Otherwise, it's a control message
			else {
				System.out.println(nextLine);
				switch( tokens[1] ) {
					case "addPlayer":
						Player player = new Player(gamePanel.getTileMap(), tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
						gamePanel.addPlayer(player);
					default:
						gamePanel.movePlayer(tokens[2], tokens[1] );
						break;
				}
			}
		}
	}

}