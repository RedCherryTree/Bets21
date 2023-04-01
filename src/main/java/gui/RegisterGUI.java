package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import domain.RegisteredUser;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JButton btnLogin;
	private JLabel usernameError;
	private JLabel emptyFieldError;
	private JLabel passwordError;
	private JTextField mailField;
	private JButton btnGoBack;
	private JLabel mailError;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));
		usernameLabel.setBounds(41, 32, 132, 14);
		contentPane.add(usernameLabel);
		
		passwordLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		passwordLabel.setBounds(41, 89, 132, 14);
		contentPane.add(passwordLabel);

		JLabel repeatPasswordLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPassword")); //$NON-NLS-1$ //$NON-NLS-2$
		repeatPasswordLabel.setBounds(41, 114, 154, 14);
		contentPane.add(repeatPasswordLabel);
		
		JLabel mailLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Mail")); //$NON-NLS-1$ //$NON-NLS-2$
		mailLabel.setForeground(new Color(0, 0, 0));
		mailLabel.setBounds(41, 148, 82, 14);
		contentPane.add(mailLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(215, 29, 86, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(215, 86, 86, 20);
		contentPane.add(passwordField);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBounds(215, 111, 86, 20);
		contentPane.add(repeatPasswordField);

		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(215, 145, 86, 20);
		contentPane.add(mailField);

		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usernameError.setVisible(false);
				passwordError.setVisible(false);
				mailError.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				String user=usernameField.getText();
				String pass1=passwordField.getText();
				String pass2=repeatPasswordField.getText();
				String mail =mailField.getText();
				
				if(!(user.equals("")||pass1.equals("")||mail.equals(""))) {
					if(!facade.isRegistered(user)) {
						if(!facade.emailRegistered(mail)){
							if(pass1.equals(pass2)) {
								
								RegisteredUser us= new RegisteredUser(user, pass1);
								us.setMail(mail);
								facade.registUser(us);
								UserGUI userGUI=new UserGUI(user);
								userGUI.setVisible(true);
								btnRegister_actionPerformed(e);
								
							} else
								passwordError.setVisible(true);
						} else
							mailError.setVisible(true);
					} else 
						usernameError.setVisible(true);
			} else
				emptyFieldError.setVisible(true);
			}
		});
		btnRegister.setBounds(122, 187, 204, 23);
		contentPane.add(btnRegister);
		
		btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Login")); 
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login= new LoginGUI();
				login.setVisible(true);
				btnRegister_actionPerformed(e);
			}
		});
		btnLogin.setBounds(10, 227, 113, 23);
		contentPane.add(btnLogin);
		
		emptyFieldError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
		emptyFieldError.setForeground(Color.RED);
		emptyFieldError.setBounds(132, 221, 292, 14);
		contentPane.add(emptyFieldError);
		emptyFieldError.setVisible(false);
		
		usernameError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsernameAlredyExists"));
		usernameError.setForeground(Color.RED);
		usernameError.setBounds(132, 221, 292, 14);
		contentPane.add(usernameError);
		usernameError.setVisible(false);
		
		passwordError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DifferentPasswords")); //$NON-NLS-1$ //$NON-NLS-2$
		passwordError.setForeground(Color.RED);
		passwordError.setBounds(132, 221, 292, 14);
		contentPane.add(passwordError);
		passwordError.setVisible(false);

		
		mailError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MailAlreadyRegistered"));
		mailError.setForeground(Color.RED);
		mailError.setBounds(132, 221, 292, 14);
		contentPane.add(mailError);
		mailError.setVisible(false);
		
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack")); //$NON-NLS-1$ //$NON-NLS-2$
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGoBack_actionPerformed(e);
			}
		});
		btnGoBack.setBounds(302, 227, 122, 23);
		contentPane.add(btnGoBack);


	}
	private void btnRegister_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}