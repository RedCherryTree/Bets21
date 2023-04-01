package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DepositMoneyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblCreditCard;
	private JLabel lblSelectAmount;
	private JButton btnGoBack;
	private JRadioButton rdbtnTermsConditions;
	private JLabel lblPassword;
	private JComboBox comboBoxPaymentOption;
	private JComboBox comboBoxAmountOfMoney;
	
    private DefaultComboBoxModel<String> amountOfMoney = new DefaultComboBoxModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepositMoneyGUI frame = new DepositMoneyGUI("XX");
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
	public DepositMoneyGUI(String user) {
		String us=user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PaymentOption"));
		lblNewLabel.setBounds(34, 18, 183, 14);
		contentPane.add(lblNewLabel);
		
		String PayPal=ResourceBundle.getBundle("Etiquetas").getString("PayPal");
		String con=ResourceBundle.getBundle("Etiquetas").getString("Password");
		comboBoxPaymentOption = new JComboBox();
		comboBoxPaymentOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxPaymentOption.getSelectedItem().equals("PayPal")) {
					lblCreditCard.setText(PayPal);
					lblPassword.setText(con);
				}
				else if(comboBoxPaymentOption.getSelectedItem().equals("Credit Card")){
					
				}
			}
		});
		comboBoxPaymentOption.setBounds(227, 11, 155, 29);
		contentPane.add(comboBoxPaymentOption);
		
		textField = new JTextField();
		textField.setBounds(227, 51, 155, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(227, 91, 155, 29);
		contentPane.add(passwordField);
		
		comboBoxAmountOfMoney = new JComboBox();
		comboBoxAmountOfMoney.setBounds(170, 131, 42, 29);
		contentPane.add(comboBoxAmountOfMoney);
		comboBoxAmountOfMoney.setModel(amountOfMoney);
		comboBoxAmountOfMoney.setSelectedItem(null);
		amountOfMoney.addElement("5");
		amountOfMoney.addElement("10");
		amountOfMoney.addElement("20");
		amountOfMoney.addElement("50");

		
		lblCreditCard = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreditCard"));
		lblCreditCard.setBounds(35, 58, 182, 14);
		contentPane.add(lblCreditCard);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPayment"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.depositMoney(user, Integer.parseInt((String) comboBoxAmountOfMoney.getSelectedItem()));
				//Add to history implementatu
			}
		});
		btnNewButton.setBounds(97, 171, 245, 41);
		contentPane.add(btnNewButton);
		
		lblSelectAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectAmount"));
		lblSelectAmount.setBounds(34, 138, 126, 14);
		contentPane.add(lblSelectAmount);
		
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGoBack_actionPerformed(e);
				UserGUI user= new UserGUI(us);
				user.setVisible(true);
			}
		});
		btnGoBack.setBounds(131, 220, 170, 30);
		contentPane.add(btnGoBack);
		
		rdbtnTermsConditions = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("TermsAndConditions"));
		rdbtnTermsConditions.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnTermsConditions.setBounds(229, 134, 199, 23);
		contentPane.add(rdbtnTermsConditions);
		
		lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(34, 98, 178, 14);
		contentPane.add(lblPassword);
	}
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
