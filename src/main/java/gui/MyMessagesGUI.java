package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Message;
import domain.Transaction;
import domain.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSeparator;

public class MyMessagesGUI extends JFrame {

	private JPanel contentPane;
	
	private JScrollPane receivedMessagesSPanel = new JScrollPane();
	private JTable receivedMessagesTable= new JTable();
	
	private String[] columnNamesReceivedMessages = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Receiver"), 
			ResourceBundle.getBundle("Etiquetas").getString("Sender"), 
			ResourceBundle.getBundle("Etiquetas").getString("Subject"), 

	};
	private DefaultTableModel tableModelMessages;
	private JTextField textField;

	private JLabel lblReceivedMessages;

	private JLabel lblNotReadMessages;

	private JButton btnWriteMessage;

	private JButton btnSentMessages;

	private JButton btnReply;

	private JButton btnSearch;

	private JLabel lblSearchConversations;

	private JButton btnRefresh;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyMessagesGUI frame = new MyMessagesGUI(null);
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
	public MyMessagesGUI(User user) {
		BLFacade facade = MainGUI.getBusinessLogic();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		receivedMessagesSPanel = new JScrollPane();
		receivedMessagesSPanel.setBounds(new Rectangle(292, 50, 346, 150));
		receivedMessagesSPanel.setBounds(10, 52, 505, 123);
		contentPane.add(receivedMessagesSPanel);
		
		receivedMessagesTable = new JTable();
		receivedMessagesSPanel.setViewportView(receivedMessagesTable);
		tableModelMessages = new DefaultTableModel(null, columnNamesReceivedMessages);

		receivedMessagesTable.setModel(tableModelMessages);
		tableModelMessages.setColumnCount(4);
		
		receivedMessagesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		receivedMessagesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		receivedMessagesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
		receivedMessagesTable.getColumnModel().removeColumn(receivedMessagesTable.getColumnModel().getColumn(3));
		
		Vector<Message> myMessages= user.getReceivedMessages();
		for(Message m: myMessages) {
			Vector<Object> row = new Vector<Object>();
			row.add(m.getReceiver());
			row.add(m.getSender());
			row.add(m.getSubject());
			row.add(m);
		}
		
		btnSentMessages = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SentMessages"));
		btnSentMessages.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSentMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReceivedMessages.setText(ResourceBundle.getBundle("Etiquetas").getString("SentMessages"));
				tableModelMessages.getDataVector().clear();
				receivedMessagesTable.updateUI();
				Vector<Message> myMessages= user.getSentMessages();
				for(Message m: myMessages) {
					Vector<Object> row = new Vector<Object>();
					row.add(m.getReceiver());
					row.add(m.getSender());
					row.add(m.getSubject());
					row.add(m);
				}
			}
		});
		btnSentMessages.setBounds(245, 186, 130, 29);
		contentPane.add(btnSentMessages);
		
		lblReceivedMessages = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Received"));
		lblReceivedMessages.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReceivedMessages.setBounds(10, 28, 148, 23);
		contentPane.add(lblReceivedMessages);
		
		btnWriteMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WriteMessage"));
		btnWriteMessage.setFont(btnWriteMessage.getFont().deriveFont(btnWriteMessage.getFont().getStyle() | Font.BOLD));
		btnWriteMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnWriteMessage.setBounds(385, 186, 130, 64);
		contentPane.add(btnWriteMessage);
		
		lblNotReadMessages = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NotReadQuantity"));
		lblNotReadMessages.setFont(lblNotReadMessages.getFont().deriveFont(lblNotReadMessages.getFont().getStyle() | Font.BOLD));
		lblNotReadMessages.setBounds(234, 11, 190, 14);
		contentPane.add(lblNotReadMessages);
		
		btnReply = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReplyMessage"));
		btnReply.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnReply.setBounds(245, 221, 130, 29);
		contentPane.add(btnReply);
		
		lblSearchConversations = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LookForConversation"));
		lblSearchConversations.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblSearchConversations.setBounds(10, 186, 200, 14);
		contentPane.add(lblSearchConversations);
		
		textField = new JTextField();
		textField.setBounds(10, 208, 122, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Search"));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(142, 208, 74, 20);
		contentPane.add(btnSearch);
		
		btnRefresh = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Refresh"));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReceivedMessages.setText(ResourceBundle.getBundle("Etiquetas").getString("Received"));
				tableModelMessages.getDataVector().clear();
				receivedMessagesTable.updateUI();
				Vector<Message> myMessages= user.getReceivedMessages();
				for(Message m: myMessages) {
					Vector<Object> row = new Vector<Object>();
					row.add(m.getReceiver());
					row.add(m.getSender());
					row.add(m.getSubject());
					row.add(m);
				}
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnRefresh.setBounds(142, 230, 74, 20);
		contentPane.add(btnRefresh);
	}
}
