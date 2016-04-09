import java.io.InputStream;
import java.util.Scanner;

public class ClientManager implements Runnable {
	private InputStream clientStream;
	private Server server;	

	public ClientManager(InputStream clientStream, Server server) {
		this.clientStream = clientStream;
		this.server = server;
	}
	
	public void run() {
		Scanner scanner = new Scanner(this.clientStream);
		while(scanner.hasNextLine()) {
			this.server.showMessage(scanner.nextLine());
		}
		scanner.close();
	}
}
