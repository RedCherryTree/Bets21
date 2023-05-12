package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.RegisteredUser;

import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyFollowsGUI extends JFrame {

	private JScrollPane accountsIFollow = new JScrollPane();
	private JPanel contentPane;
	private JTable followsTable= new JTable();
	
	private String[] columnNamesFollows = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Username"), 
			ResourceBundle.getBundle("Etiquetas").getString("Mail"), 

	};
	private DefaultTableModel tableModelFollows;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFollowsGUI frame = new MyFollowsGUI("user");
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
	public MyFollowsGUI(String user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		accountsIFollow = new JScrollPane();
		accountsIFollow.setBounds(new Rectangle(292, 50, 346, 150));
		accountsIFollow.setBounds(132, 58, 292, 84);
		contentPane.add(accountsIFollow);
		
		followsTable = new JTable();
		accountsIFollow.setViewportView(followsTable);
		tableModelFollows = new DefaultTableModel(null, columnNamesFollows);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		followsTable.setModel(tableModelFollows);
		
		table = new JTable();
		accountsIFollow.setColumnHeaderView(table);
		
		refreshTable(myfollows(user, facade));
		
		JLabel Username = new JLabel("Username");
		Username.setBounds(23, 28, 117, 14);
		contentPane.add(Username);
		
		textField = new JTextField();
		textField.setBounds(148, 27, 109, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setForeground(new Color(50, 205, 50));
		lblNewLabel_1.setBounds(270, 28, 23, 19);
		contentPane.add(lblNewLabel_1);
		
		JButton Unfollow = new JButton("Unfollow");
		Unfollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				System.out.println(i);
				facade.unfollowUser(user, i);
				refreshTable(myfollows(user, facade));
			}
		});
		
		Unfollow.setBounds(321, 153, 103, 41);
		contentPane.add(Unfollow);
		
		JButton followUser = new JButton("Follow User");
		followUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String follow = textField.getText();
				facade.followUser(user, follow);
				refreshTable(myfollows(user, facade));
				
			}
		});
		followUser.setBounds(278, 11, 89, 36);
		contentPane.add(followUser);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(63, 178, 172, 72);
		contentPane.add(textPane);
		
		JButton btnGoBack = new JButton("back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI userGUI= new UserGUI(user);
				userGUI.setVisible(true);
				btnGoBack_actionPerformed(e);
			}
		});
		btnGoBack.setBounds(321, 225, 103, 25);
		contentPane.add(btnGoBack);
	}
	private void btnGoBack_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	private void refreshTable(Vector<RegisteredUser> myfollows) {
		
		if(!myfollows.isEmpty()) {
			System.out.println("mylist lleno"+myfollows.firstElement().getUsername());
		for(RegisteredUser fl:myfollows) {
			Vector<Object> row = new Vector<Object>();
			row.add(fl.getUsername());
			row.add(fl.getMail());
			tableModelFollows.addRow(row);
		}	
		}
		else {
			System.out.println("mylist empty");
		}
		}

	private Vector<RegisteredUser> myfollows(String user, BLFacade facade) {
		return facade.getFollows(user);
	}
}
