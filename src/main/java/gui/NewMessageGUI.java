package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewMessageGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblSubject;
	private JButton btnNewButton;
	private JLabel lblText;
	private JLabel lblUser;
	private JTextField textFieldReceiver;
	private JLabel lblSender;
	private JTextPane textPaneSubject;
	private JTextPane textPaneMessage;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMessageGUI frame = new NewMessageGUI(null);
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
	public NewMessageGUI(String user) {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Message")); //$NON-NLS-1$ //$NON-NLS-2$
		BLFacade facade = MainGUI.getBusinessLogic();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblSender = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Receiver"));
		lblSender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSender.setBounds(28, 58, 97, 14);
		contentPane.add(lblSender);
		
		lblSubject = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Subject"));
		lblSubject.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSubject.setBounds(28, 134, 168, 14);
		contentPane.add(lblSubject);
		
		btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(facade.isRegistered(user)) {
					if(textPaneMessage.getText().equals("")) {
						lblError.setVisible(true);
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("MessageEmpty"));
					}
					else {
						facade.sendMessage(user, textFieldReceiver.getText(),textPaneSubject.getText() , textPaneMessage.getText());
						btnGoBack_actionPerformed(e);
					}
				}
				else {
					lblError.setVisible(true);
				}
			}
		});
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setForeground(SystemColor.infoText);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(273, 58, 151, 58);
		contentPane.add(btnNewButton);
		
		lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+": "+user);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUser.setBounds(10, 11, 186, 14);
		contentPane.add(lblUser);
		
		lblText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Message"));
		lblText.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblText.setBounds(30, 200, 360, 22);
		contentPane.add(lblText);
		
		textFieldReceiver = new JTextField();
		textFieldReceiver.setBounds(10, 79, 186, 31);
		contentPane.add(textFieldReceiver);
		textFieldReceiver.setColumns(10);
		
		textPaneSubject = new JTextPane();
		textPaneSubject.setBounds(10, 152, 414, 38);
		contentPane.add(textPaneSubject);
		
		textPaneMessage = new JTextPane();
		textPaneMessage.setBounds(10, 223, 414, 189);
		contentPane.add(textPaneMessage);
		
		lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReceiverNotFound"));
		lblError.setForeground(Color.RED);
		lblError.setBounds(273, 116, 151, 14);
		lblError.setVisible(false);
		contentPane.add(lblError);
	}
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
