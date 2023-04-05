package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PurchaseGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblThanksPurchase;
	private JButton btnAddMoney;
	private JLabel jLabelSelectOption;
	private JButton btnGoBack;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchaseGUI frame = new PurchaseGUI("XX");
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
	public PurchaseGUI(String user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblThanksPurchase = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ThanksPurchase")+": "+user+"!!!!");
		lblThanksPurchase.setFont(new Font("Source Serif Pro Semibold", Font.BOLD | Font.ITALIC, 21));
		lblThanksPurchase.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblThanksPurchase);
		
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(jLabelSelectOption);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetGUI betGUI= new BetGUI(user);
				betGUI.setVisible(true);
				close_actionPerformed(e);
			}
		});
		contentPane.add(btnNewButton);
		
		btnAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddMoney"));
		btnAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepositMoneyGUI depositMoneyGUI= new DepositMoneyGUI(user);
				depositMoneyGUI.setVisible(true);
				close_actionPerformed(e);
			}
		});
		contentPane.add(btnAddMoney);
		
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI userGUI= new UserGUI(user);
				userGUI.setVisible(true);
				close_actionPerformed(e);
			}
		});
		contentPane.add(btnGoBack);		
	}
	private void close_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
