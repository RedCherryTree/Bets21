package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BusinessLogicServer;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton btnRegister;
	private JButton btnLogin;
	private JButton btnGoBack;
	private JLabel lblNewLabel;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UsernameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));
		UsernameLabel.setBounds(41, 48, 128, 14);
		contentPane.add(UsernameLabel);
		
		JLabel PasswordLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		PasswordLabel.setBounds(41, 114, 128, 14);
		contentPane.add(PasswordLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(215, 45, 86, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(215, 111, 86, 20);
		contentPane.add(passwordField);
		
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String user=usernameField.getText();
				String pass=passwordField.getText();
				boolean b = facade.isLogin(user, pass);
				if(b) {
					btnRegister_actionPerformed(e);
					if(facade.isAdmin(user)) {
                     AdminGUI admin= new AdminGUI(user);
                     admin.setVisible(true);
                     btnRegister_actionPerformed(e);
					}
                     else {
                    	 UserGUI main=new UserGUI(user);
                    	 main.setVisible(true);
                    	 btnRegister_actionPerformed(e);
                     }
				}
				else {
					lblNewLabel.setVisible(true);
				}
				 
			}
		});
		btnLogin.setBounds(122, 187, 204, 23);
		contentPane.add(btnLogin);
		
		btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register")); 
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI register= new RegisterGUI();
				register.setVisible(true);
				btnRegister_actionPerformed(e);
			}
		});
		btnRegister.setBounds(10, 227, 113, 23);
		contentPane.add(btnRegister);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IncorrectUserOrPassword"));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(132, 221, 282, 14);
		lblNewLabel.setVisible(false);
		contentPane.add(lblNewLabel);
		
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack")); //$NON-NLS-1$ //$NON-NLS-2$
		btnGoBack.setBounds(302, 227, 122, 23);
		contentPane.add(btnGoBack);
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGoBack_actionPerformed(e);
			}
		});

	}
	private void btnRegister_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
