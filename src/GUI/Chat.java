package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

import APP.Client;
import APP.MessageReceiver;

public class Chat {
	private JFrame frame;
	public JTextArea messageArea;
	private JTextField inputText;
	private Client client;
	private boolean connected = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat window = new Chat();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chat() {
		client = new Client("127.0.0.1", 4321 );
		connected = client.connectClient();
		initialize();
	}
	
	public void registerReceiver(Socket socket, JTextArea messageArea){
		try {
			// Create a message receiver from the same ip
			MessageReceiver messageReceiver = new MessageReceiver(socket.getInputStream(), messageArea);
			Thread serverListener = new Thread(messageReceiver);
			serverListener.start();
		} catch (IOException e) {
			System.out.println("IO Error: " + e);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		messageArea = new JTextArea();
		messageArea.setToolTipText("Non editable textfield from server\r\n");
		messageArea.setFont(new Font("Verdana", Font.PLAIN, 11));
		messageArea.setEditable(false);
		messageArea.setBounds(25, 33, 358, 135);
		
		messageArea.append("Initializing...\n");
		if( connected ) {
			messageArea.append("Client connected successfuly\n");
		} else {
			messageArea.append("Error in connection. Is the server running?\n");
		}
		registerReceiver(client.getSocket(), messageArea);
		
		frame.getContentPane().add(messageArea);
		messageArea.setColumns(10);
		
		inputText = new JTextField();
		inputText.setHorizontalAlignment(SwingConstants.CENTER);
		inputText.setToolTipText("MessageTexField\r\n");
		inputText.setFont(new Font("Verdana", Font.PLAIN, 16));
		inputText.setBounds(25, 185, 259, 27);
		frame.getContentPane().add(inputText);
		inputText.setColumns(10);
		
		JButton btnSend = new JButton("Send\r\n");
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( connected ) {
					client.sendMessage(inputText.getText());
				} 
				inputText.setText("");				
			}
		});
		btnSend.setBounds(294, 190, 89, 23);
		frame.getContentPane().add(btnSend);
	}
}
