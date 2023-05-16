package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

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
	private JTextPane textPane;

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
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(6, 64, 500, 154);
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
					facade.openTicket(text, user);
					jButtonClose_actionPerformed(e);
				}
			}
		});
		btnOpenTiquet.setBounds(198, 246, 120, 39);
		contentPane.add(btnOpenTiquet);
		
		btnSuggestRemoval = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SuggestRemoval"));
		btnSuggestRemoval.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnSuggestRemoval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				if(text.equals("")) {
					lblError.setVisible(true);
				}
				else {
					SuggestRemovalGUI suggestRemovalGUI= new SuggestRemovalGUI(user, text);
					suggestRemovalGUI.setVisible(true);
					jButtonClose_actionPerformed(e);
				}
			}
		});
		btnSuggestRemoval.setBounds(328, 247, 178, 39);
		contentPane.add(btnSuggestRemoval);
		
		btnSuggestEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SuggestEvent"));
		btnSuggestEvent.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSuggestEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=textPane.getText();
				if(text.equals("")) {
					lblError.setVisible(true);
				}
				else {
					SuggestEventGUI suggestEventGUI= new SuggestEventGUI(null, text, user);
					suggestEventGUI.setVisible(true);
					jButtonClose_actionPerformed(e);
				}
			}
		});
		btnSuggestEvent.setBounds(10, 246, 178, 39);
		contentPane.add(btnSuggestEvent);
		
		lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
		lblError.setFont(lblError.getFont().deriveFont(lblError.getFont().getStyle() | Font.BOLD));
		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 221, 202, 14);
		contentPane.add(lblError);
		lblError.setVisible(false);
		
		lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+":"+ user);
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUsername.setBounds(336, 11, 170, 14);
		contentPane.add(lblUsername);
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
