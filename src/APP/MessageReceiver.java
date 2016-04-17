package APP;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JTextArea;

public class MessageReceiver implements Runnable {
	// Server input strem
	private InputStream inputStream;
	private JTextArea messageArea;
	
	public MessageReceiver(InputStream inputStream, JTextArea messageArea) {
		this.inputStream = inputStream;	
		this.messageArea = messageArea;
	}
	
	@Override
	public void run() {
		Scanner s = new Scanner(this.inputStream);
		System.out.println("Reading server messages: ");
		while(s.hasNextLine()) {
			String nextLine = s.nextLine();
			System.out.println(nextLine);
			messageArea.append(nextLine+"\n");
		}
	}

}