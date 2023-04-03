package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Question;
import domain.Transaction;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

public class MyAccountBalanceGUI extends JFrame {

	private JPanel contentPane;
	private JTable BalanceTable;
	private JScrollPane scrollPaneMyTransactions;
	private DefaultTableModel tableModelTransactions;
	
	private String[] columnNamesTransactions = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("TransactionN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Transaction"), 

	};
	private JLabel lblMyTransactions;
	private JLabel lblPaymentOption;
	private JPanel DetailsPanel;
	private JLabel lblPaymentMethod;
	private JLabel lblNewLabelPaymentOption;
	private JLabel lblNewLabelPaymentMethod;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyAccountBalanceGUI frame = new MyAccountBalanceGUI("XX");
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
	public MyAccountBalanceGUI(String user) {

		BLFacade facade = MainGUI.getBusinessLogic();
		setTitle("My Account Balance");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPaneMyTransactions = new JScrollPane();
		scrollPaneMyTransactions.setBounds(new Rectangle(20, 50, 379, 115));
		contentPane.add(scrollPaneMyTransactions);
		
		
		BalanceTable = new JTable();
		scrollPaneMyTransactions.setViewportView(BalanceTable);
		tableModelTransactions = new DefaultTableModel(null, columnNamesTransactions);
		BalanceTable.setModel(tableModelTransactions);
		
		tableModelTransactions.setDataVector(null, columnNamesTransactions);
		tableModelTransactions.setColumnCount(3);//Another row for the transactions
		
		BalanceTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		BalanceTable.getColumnModel().getColumn(1).setPreferredWidth(279);
		Vector<Transaction> myTransactions= facade.getUserTransactions(user);
		for(Transaction t: myTransactions) {
			Vector<Object> row = new Vector<Object>();

			System.out.println("Transactions "+t);

			row.add(t.getTransactionNumber());
			if(t.getTransactionType().equals("Bet")) {
				row.add(ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+":"+t.getMoney()+"€");
			}
			else {
				if(t.getTransactionType().equals("Deposit")) {
					row.add(ResourceBundle.getBundle("Etiquetas").getString("DepositMoney")+":"+t.getMoney()+"€");
				}
				else {
					row.add(ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney")+":"+t.getMoney()+"€");
				}
			}
			row.add(t); // t object added in order to obtain it with tableModelEvents.getValueAt(i,2)
			tableModelTransactions.addRow(row);		
		}
		
		lblMyTransactions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Transaction"));
		lblMyTransactions.setForeground(SystemColor.textText);
		lblMyTransactions.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMyTransactions.setBounds(20, 26, 148, 14);
		contentPane.add(lblMyTransactions);
		BalanceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=BalanceTable.getSelectedRow();
				domain.Transaction t=(domain.Transaction)tableModelTransactions.getValueAt(i,2); // obtain t object
				if(t.getTransactionType().equals("Bet")) {//Hay que acabar cuando se implemente Bet
					
				}
				else {
					if(t.getTransactionType().equals("Deposit")) {
						lblPaymentOption.setVisible(true);
						
						lblNewLabelPaymentOption.setText(t.getPaymentOption());
						lblNewLabelPaymentOption.setVisible(true);
						
						lblPaymentMethod.setVisible(true);
						lblNewLabelPaymentMethod.setText(t.getPaymentMethod());

					}
					else {//Hay que acabar cuando se implemente QuestionWinner
						
					}
				}
			}
		});
		
		DetailsPanel = new JPanel();
		DetailsPanel.setBounds(10, 176, 389, 71);
		contentPane.add(DetailsPanel);
		DetailsPanel.setLayout(null);
		DetailsPanel.setVisible(true);
		
		lblPaymentOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PaymentOption")+":");
		lblPaymentOption.setBounds(10, 11, 209, 14);
		DetailsPanel.add(lblPaymentOption);
		lblPaymentOption.setVisible(false);
		
		lblPaymentMethod = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsedPaymentCDOrMail")+":");
		lblPaymentMethod.setBounds(10, 46, 209, 14);
		DetailsPanel.add(lblPaymentMethod);
		lblPaymentMethod.setVisible(false);
		
		
		lblNewLabelPaymentOption = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelPaymentOption.setBounds(250, 11, 139, 14);
		DetailsPanel.add(lblNewLabelPaymentOption);
		
		lblNewLabelPaymentMethod = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelPaymentMethod.setBounds(250, 46, 139, 14);
		DetailsPanel.add(lblNewLabelPaymentMethod);
	}
}
