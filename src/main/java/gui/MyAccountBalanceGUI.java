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
	private JLabel lblCurrentMoney;
	private JLabel lblNewLabel;
	private JLabel lblQuote;


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
		BalanceTable.getColumnModel().removeColumn(BalanceTable.getColumnModel().getColumn(2));
		Vector<Transaction> myTransactions= facade.getUserTransactions(user);
		for(Transaction t: myTransactions) {
			Vector<Object> row = new Vector<Object>();

			System.out.println("Transactions "+t);

			row.add(t.getTransactionNumber());
			if(t.getTransactionType().equals("Bet")) {
				row.add(ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+":"+String.format("%.2f",  t.getMoney())+"€");
				
			}
			else {
				if(t.getTransactionType().equals("Deposit")) {
					row.add(ResourceBundle.getBundle("Etiquetas").getString("DepositMoney")+":"+String.format("%.2f",  t.getMoney())+"€");
				}
				else {
					if(t.getTransactionType().equals("Refund")) {
						row.add(ResourceBundle.getBundle("Etiquetas").getString("RefundedMoney")+":"+String.format("%.2f", t.getMoney())+"€");
					}
					else {
						row.add(ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney")+":"+String.format("%.2f", t.getMoney())+"€");
					}
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
					lblPaymentOption.setVisible(true);
					lblPaymentOption.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+": "+t.getMyBet().getBetQuote().getQuestion().getEvent().getDescription());
					
					lblPaymentMethod.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": "+t.getMyBet().getBetQuote().getQuestion().getQuestion()); 
					lblPaymentMethod.setVisible(true);
					
					lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": "+t.getMyBet().getBetQuote().getQuoteName()); 
					lblQuote.setVisible(true);
				}
				else {
					if(t.getTransactionType().equals("Deposit")) {
						lblPaymentOption.setVisible(true);
						lblPaymentOption.setText(ResourceBundle.getBundle("Etiquetas").getString("PaymentOption")+": "+t.getPaymentOption());
						
						lblPaymentMethod.setText(ResourceBundle.getBundle("Etiquetas").getString("UsedPaymentCDOrMail")+": "+t.getPaymentMethod()); 
						lblPaymentMethod.setVisible(true);
						lblQuote.setVisible(false);
						
					}
					else {
						if(t.getTransactionType().equals("Refund")) {
	//						lblPaymentOption.setVisible(true);
	//						lblPaymentOption.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+": "+t.getMyBet().getBetQuote().getQuestion().getEvent().getDescription());
							
	//						lblPaymentMethod.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": "+t.getMyBet().getBetQuote().getQuestion().getQuestion()); 
	//						lblPaymentMethod.setVisible(true);
							
	//						lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": "+t.getMyBet().getBetQuote().getQuoteName()); 
	//						lblQuote.setVisible(true);
						}
						else {
	//						lblPaymentOption.setVisible(true);
	//						lblPaymentOption.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+": "+t.getMyBet().getBetQuote().getQuestion().getEvent().getDescription());
							
	//						lblPaymentMethod.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": "+t.getMyBet().getBetQuote().getQuestion().getQuestion()); 
	//						lblPaymentMethod.setVisible(true);
							
	//						lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": "+t.getMyBet().getBetQuote().getQuoteName()); 
	//						lblQuote.setVisible(true);
						}
						}
					}
				}
			
		});
		
		DetailsPanel = new JPanel();
		DetailsPanel.setBounds(10, 176, 414, 85);
		contentPane.add(DetailsPanel);
		DetailsPanel.setLayout(null);
		DetailsPanel.setVisible(true);
		
		lblPaymentOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PaymentOption")+":");
		lblPaymentOption.setBounds(10, 11, 394, 14);
		DetailsPanel.add(lblPaymentOption);
		lblPaymentOption.setVisible(false);
		
		lblPaymentMethod = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsedPaymentCDOrMail")+":");
		lblPaymentMethod.setBounds(10, 36, 394, 14);
		DetailsPanel.add(lblPaymentMethod);
		
		lblQuote = new JLabel();
		lblQuote.setBounds(10, 60, 394, 14);
		DetailsPanel.add(lblQuote);
		lblPaymentMethod.setVisible(false);
		
		
		lblCurrentMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentMoney")+": "+String.format("%.2f", facade.getUserMoney(user))+"€");
		lblCurrentMoney.setBounds(260, 11, 164, 14);
		contentPane.add(lblCurrentMoney);
		
		
		
	}
}
