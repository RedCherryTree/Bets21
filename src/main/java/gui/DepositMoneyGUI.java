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
import java.awt.Color;

public class DepositMoneyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPaymentMethod;
	private JPasswordField passwordFieldP;
	private JLabel lblCreditCard;
	private JLabel lblSelectAmount;
	private JRadioButton rdbtnTermsConditions;
	private JLabel lblPassword;
	private JComboBox comboBoxPaymentOption;
	private JComboBox comboBoxAmountOfMoney;
	
    private DefaultComboBoxModel<String> amountOfMoney = new DefaultComboBoxModel();
    private DefaultComboBoxModel<String> paymentOptions = new DefaultComboBoxModel();
	private JLabel lblEuro;
	private JLabel lblEmptyField;
	private JLabel lblPayDone;

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
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DepositMoney")); //$NON-NLS-1$ //$NON-NLS-2$
		String us=user;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PaymentOption"));
		lblNewLabel.setBounds(34, 18, 193, 14);
		contentPane.add(lblNewLabel);
		
		lblCreditCard = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreditCardLbl"));
		lblCreditCard.setBounds(35, 58, 192, 14);
		contentPane.add(lblCreditCard);
		
		lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(34, 98, 193, 14);
		contentPane.add(lblPassword);
		
		String PayPal=ResourceBundle.getBundle("Etiquetas").getString("PayPal");
		String con=ResourceBundle.getBundle("Etiquetas").getString("Password");
		comboBoxPaymentOption = new JComboBox();
		comboBoxPaymentOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxPaymentOption.getSelectedItem().equals("PayPal")) {
					lblCreditCard.setText(PayPal);
					lblPassword.setText(con);
				}
				else if(comboBoxPaymentOption.getSelectedItem().equals(ResourceBundle.getBundle("Etiquetas").getString("CreditCard"))){
					lblCreditCard.setText(ResourceBundle.getBundle("Etiquetas").getString("CreditCardLbl"));
					lblPassword.setText(ResourceBundle.getBundle("Etiquetas").getString("SecretCode"));
				}
			}
		});
		comboBoxPaymentOption.setBounds(237, 11, 155, 29);
		contentPane.add(comboBoxPaymentOption);
		comboBoxPaymentOption.setModel(paymentOptions);
		paymentOptions.addElement(ResourceBundle.getBundle("Etiquetas").getString("CreditCard"));
		paymentOptions.addElement("PayPal");
		
		textFieldPaymentMethod = new JTextField();
		textFieldPaymentMethod.setBounds(237, 51, 155, 29);
		contentPane.add(textFieldPaymentMethod);
		textFieldPaymentMethod.setColumns(10);
		
		passwordFieldP = new JPasswordField();
		passwordFieldP.setBounds(237, 91, 155, 29);
		contentPane.add(passwordFieldP);
		
		comboBoxAmountOfMoney = new JComboBox();
		comboBoxAmountOfMoney.setBounds(135, 139, 42, 29);
		contentPane.add(comboBoxAmountOfMoney);
		comboBoxAmountOfMoney.setModel(amountOfMoney);
		comboBoxAmountOfMoney.setSelectedItem(null);
		amountOfMoney.addElement("5");
		amountOfMoney.addElement("10");
		amountOfMoney.addElement("20");
		amountOfMoney.addElement("50");

		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPayment"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnTermsConditions.isSelected()) {
					BLFacade facade = MainGUI.getBusinessLogic();
					//Add to history implementatu
					String option= (String) comboBoxPaymentOption.getSelectedItem();
					String method=textFieldPaymentMethod.getText();
					String pass=passwordFieldP.getText();
					if(method.equals("")||pass.equals("")) {
						lblEmptyField.setVisible(true);
						lblPayDone.setVisible(false);
					}
					else {
						facade.depositMoney(user, Integer.parseInt((String) comboBoxAmountOfMoney.getSelectedItem()), option, method);
						lblEmptyField.setVisible(false);
						rdbtnTermsConditions.setForeground(Color.BLACK);
						lblPayDone.setVisible(true);

						PurchaseGUI purchaseGUI= new PurchaseGUI(user);
						purchaseGUI.setVisible(true);
						btnGoBack_actionPerformed(e);
						
					}
				}
				else {
					rdbtnTermsConditions.setForeground(Color.RED);
					lblPayDone.setVisible(false);
				}
			}
		});
		btnNewButton.setBounds(34, 209, 358, 41);
		contentPane.add(btnNewButton);
		
		lblSelectAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectAmount"));
		lblSelectAmount.setBounds(34, 146, 104, 14);
		contentPane.add(lblSelectAmount);
		
		rdbtnTermsConditions = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("TermsAndConditions"));
		rdbtnTermsConditions.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnTermsConditions.setBounds(34, 179, 199, 23);
		contentPane.add(rdbtnTermsConditions);
		
		lblEuro = new JLabel("€");
		lblEuro.setFont(new Font("Source Serif Pro Black", Font.PLAIN, 18));
		lblEuro.setBounds(187, 139, 24, 29);
		lblEuro.setForeground(Color.BLACK);
		contentPane.add(lblEuro);
		
		lblEmptyField = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
		lblEmptyField.setForeground(Color.RED);
		lblEmptyField.setBounds(237, 120, 155, 14);
		contentPane.add(lblEmptyField);
		lblEmptyField.setVisible(false);
		
		lblPayDone = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PayDone"));
		lblPayDone.setForeground(Color.BLACK);
		lblPayDone.setBounds(237, 120, 155, 14);
		contentPane.add(lblPayDone);
		lblPayDone.setVisible(false);
	}
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}