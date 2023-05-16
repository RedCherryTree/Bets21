package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class ReadTicketGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblClient;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadTicketGUI frame = new ReadTicketGUI("", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReadTicketGUI(String user, String desciption) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblClient = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Client")+": "+user);
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblClient.setBounds(10, 0, 217, 34);
		contentPane.add(lblClient);
		
		textPane = new JTextPane();
		textPane.setBorder(new LineBorder(Color.BLACK, 1));
		textPane.setBounds(10, 45, 414, 205);
		contentPane.add(textPane);
	}
}
