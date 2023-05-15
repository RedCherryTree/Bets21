package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpenTiquetGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblInfo;
	private JLabel lblError;
	private JLabel lblUsername;
	private JButton btnSuggestEvent;
	private JButton btnOpenTiquet;
	private JButton btnSuggestRemoval;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenTiquetGUI frame = new OpenTiquetGUI("XX");
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
	public OpenTiquetGUI(String user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(6, 64, 425, 154);
		contentPane.add(textPane);
		
		lblInfo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TicketInfo")+".");
		lblInfo.setForeground(Color.DARK_GRAY);
		lblInfo.setFont(lblInfo.getFont().deriveFont(lblInfo.getFont().getStyle() | Font.BOLD));
		lblInfo.setBounds(6, 46, 414, 14);
		contentPane.add(lblInfo);
		
		btnOpenTiquet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("OpenTicket"));
		btnOpenTiquet.setFont(btnOpenTiquet.getFont().deriveFont(btnOpenTiquet.getFont().getStyle() | Font.BOLD));
		btnOpenTiquet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				if(text.equals("")) {
					lblError.setVisible(true);
				}
				else {
					
				}
			}
		});
		btnOpenTiquet.setBounds(149, 246, 140, 39);
		contentPane.add(btnOpenTiquet);
		
		btnSuggestRemoval = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SuggestRemoval"));
		btnSuggestRemoval.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnSuggestRemoval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				if(text.equals("")) {
					lblError.setVisible(true);
				}
				else {
					
				}
			}
		});
		btnSuggestRemoval.setBounds(299, 246, 132, 39);
		contentPane.add(btnSuggestRemoval);
		
		btnSuggestEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SuggestEvent"));
		btnSuggestEvent.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnSuggestEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				if(text.equals("")) {
					lblError.setVisible(true);
				}
				else {
					
				}
			}
		});
		btnSuggestEvent.setBounds(10, 246, 129, 39);
		contentPane.add(btnSuggestEvent);
		
		lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
		lblError.setFont(lblError.getFont().deriveFont(lblError.getFont().getStyle() | Font.BOLD));
		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 221, 202, 14);
		contentPane.add(lblError);
		lblError.setVisible(false);
		
		lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+":"+ user);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUsername.setBounds(271, 11, 170, 14);
		contentPane.add(lblUsername);
	}
}
