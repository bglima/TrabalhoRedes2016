import java.io.InputStream;
import java.util.Scanner;

public class MessageReceiver implements Runnable {
	// Server input strem
	private InputStream inputStream;
	
	public MessageReceiver(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void run() {
		Scanner s = new Scanner(this.inputStream);
		System.out.println("Reading server messages: ");
		while(s.hasNextLine()) {
			System.out.println(s.nextLine());
		}
	}
	
	

}
