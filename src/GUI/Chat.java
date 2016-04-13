package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Chat {

	private JFrame frame;
	private JTextField textField;
	private JTextField txtStart;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Non editable textfield from server\r\n");
		textField.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField.setEditable(false);
		textField.setBounds(25, 33, 358, 135);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		txtStart = new JTextField();
		txtStart.setHorizontalAlignment(SwingConstants.CENTER);
		txtStart.setToolTipText("MessageTexField\r\n");
		txtStart.setFont(new Font("Verdana", Font.PLAIN, 16));
		txtStart.setText("Start");
		txtStart.setBounds(25, 185, 259, 27);
		frame.getContentPane().add(txtStart);
		txtStart.setColumns(10);
		
		JButton btnSend = new JButton("Send\r\n");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText( textField.getText() + txtStart.getText());
				txtStart.setText("");				
			}
		});
		btnSend.setBounds(294, 190, 89, 23);
		frame.getContentPane().add(btnSend);
	}
}
