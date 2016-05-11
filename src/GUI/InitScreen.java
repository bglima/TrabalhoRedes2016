package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import javax.swing.Timer;


import APP.Client;
import APP.MessageReceiver;
import GUI.GamePanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Button;

public class InitScreen {
	private JFrame frame;
	private JTextArea messageArea;
	private JTextField inputText;
	private Client client;
	private boolean connected = false;
	private JTextField txtName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitScreen window = new InitScreen();
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
	public InitScreen() {
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 441, 648);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		messageArea = new JTextArea();
		messageArea.setToolTipText("Non editable textfield from server\r\n");
		messageArea.setFont(new Font("Verdana", Font.PLAIN, 11));
		messageArea.setEditable(false);
		messageArea.setBounds(10, 433, 410, 87);
		messageArea.append("Initializing...\n");
		if( connected ) {
			messageArea.append("Client connected successfuly\n");
		} else {
			messageArea.append("Error in connection. Is the server running?\n");
		}
		// Register the "messageArea" element to receiver
		registerReceiver(client.getSocket(), messageArea);
		frame.getContentPane().add(messageArea);
		messageArea.setFocusable(false);
		messageArea.setColumns(10);
		
		inputText = new JTextField();
		inputText.setHorizontalAlignment(SwingConstants.CENTER);
		inputText.setToolTipText("MessageTexField\r\n");
		inputText.setFont(new Font("Verdana", Font.PLAIN, 16));
		inputText.setBounds(10, 529, 309, 27);
		frame.getContentPane().add(inputText);
		inputText.setColumns(10);
		
		JButton btnSend = new JButton("Send\r\n");
		// Whenever "btnSend" is clicked, send a message to server
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( connected ) {
					client.sendMessage(inputText.getText());
				} 
				inputText.setText("");				
			}
		});
		btnSend.setBounds(331, 533, 89, 23);
		frame.getContentPane().add(btnSend);
		
		JPanel gamePanel = new GamePanel();
		gamePanel.setBounds(10, 10, 416, 416);
		frame.getContentPane().add(gamePanel);
		
		txtName = new JTextField();
		txtName.setBounds(54, 569, 121, 22);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblPlayerName = new JLabel("Name:");
		lblPlayerName.setBounds(12, 569, 44, 23);
		frame.getContentPane().add(lblPlayerName);
		
		String[] comboList = {"char1.png", "char2.png", "char3.png", "char4.png", "char5.png"};
		JComboBox comboBox = new JComboBox(comboList);
		comboBox.setBounds(230, 569, 89, 22);
		frame.getContentPane().add(comboBox);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(331, 567, 89, 24);
		frame.getContentPane().add(btnConnect);
		
		JLabel lblHero = new JLabel("Hero:");
		lblHero.setBounds(187, 572, 32, 16);
		frame.getContentPane().add(lblHero);
	}
}
