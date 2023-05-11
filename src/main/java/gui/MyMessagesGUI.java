package gui;

import java.awt.Color;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMessagesGUI extends JFrame {

	private JPanel contentPane;
	
	private JScrollPane receivedMessagesSPanel = new JScrollPane();
	private JTable receivedMessagesTable= new JTable();
	private DefaultTableModel tableModelMessages;
	
	private String[] columnNamesReceivedMessages = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Receiver"), 
			ResourceBundle.getBundle("Etiquetas").getString("Sender"), 
			ResourceBundle.getBundle("Etiquetas").getString("Subject"), 

	};
	
	private JTextField textField;

	private JLabel lblReceivedMessages;

	private JLabel lblNotReadMessages;

	private JButton btnWriteMessage;

	private JButton btnSentMessages;

	private JButton btnSearch;

	private JLabel lblSearchConversations;

	private JButton btnRefresh;

	private JLabel lblEror;


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
	public MyMessagesGUI(String us) {

		BLFacade facade = MainGUI.getBusinessLogic();
		User user=facade.getUser(us);
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
		
		Vector<Message> myMessages= facade.getUserReceivedMessages(us);
		for(Message m: myMessages) {
			Vector<Object> row = new Vector<Object>();
			row.add(m.getReceiver());
			row.add(m.getSender());
			row.add(m.getSubject());
			row.add(m);
			tableModelMessages.addRow(row);
		}
		
		receivedMessagesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=receivedMessagesTable.getSelectedRow();
				domain.Message m=(domain.Message)tableModelMessages.getValueAt(i,3); 
				System.out.println(m.toString());
				ReadMessageGUI readMessageGUI= new ReadMessageGUI(m);
				readMessageGUI.setVisible(true);
			}
		});
		
		btnSentMessages = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SentMessages"));
		btnSentMessages.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSentMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblReceivedMessages.setText(ResourceBundle.getBundle("Etiquetas").getString("SentMessages"));
				tableModelMessages.getDataVector().clear();
				receivedMessagesTable.updateUI();
				Vector<Message> myMessages= facade.getUserSentMessages(us);
				for(Message m: myMessages) {
					Vector<Object> row = new Vector<Object>();
					row.add(m.getReceiver());
					row.add(m.getSender());
					row.add(m.getSubject());
					row.add(m);
					tableModelMessages.addRow(row);
				}
			}
		});
		btnSentMessages.setBounds(385, 186, 130, 29);
		contentPane.add(btnSentMessages);
		
		lblReceivedMessages = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Received"));
		lblReceivedMessages.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReceivedMessages.setBounds(10, 28, 148, 23);
		contentPane.add(lblReceivedMessages);
		
		btnWriteMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WriteMessage"));
		btnWriteMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewMessageGUI messageGUI= new NewMessageGUI(user.getUsername(), "");
				messageGUI.setVisible(true);
			}
		});
		btnWriteMessage.setFont(btnWriteMessage.getFont().deriveFont(btnWriteMessage.getFont().getStyle() | Font.BOLD));

		btnWriteMessage.setBounds(245, 186, 130, 69);
		contentPane.add(btnWriteMessage);
		
		lblNotReadMessages = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NotReadQuantity"));
		lblNotReadMessages.setFont(lblNotReadMessages.getFont().deriveFont(lblNotReadMessages.getFont().getStyle() | Font.BOLD));
		lblNotReadMessages.setBounds(268, 11, 247, 14);
		contentPane.add(lblNotReadMessages);
		
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
				tableModelMessages.getDataVector().clear();
				receivedMessagesTable.updateUI();
				String receiver= textField.getText();
				if(facade.isRegistered(receiver)) {
					lblEror.setVisible(false);
					for(Message m: facade.getConversation(us, textField.getText())) {
						Vector<Object> row = new Vector<Object>();
						row.add(m.getReceiver());
						row.add(m.getSender());
						row.add(m.getSubject());
						row.add(m);
						tableModelMessages.addRow(row);
					}
				}
				else {
					lblEror.setVisible(true);
				}
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
					tableModelMessages.addRow(row);
				}
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnRefresh.setBounds(385, 226, 130, 29);
		contentPane.add(btnRefresh);
		
		lblEror = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));
		lblEror.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEror.setForeground(Color.RED);
		lblEror.setVisible(false);
		lblEror.setBounds(10, 233, 122, 14);
		contentPane.add(lblEror);
	}
//	private static void printNotRead(JScrollPane receivedMessagesSPanel, JTable receivedMessagesTable, DefaultTableModel tableModelMessages,
//			Vector<Message> myMessages) {
//		for(int i=0; i<myMessages.size();i++) {
//			if(!myMessages.get(i).isHasBeenRead()) {
//				receivedMessagesSPanel.setBackground(Color.CYAN);
//			}
//		}
//	}
}
