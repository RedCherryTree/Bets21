package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

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
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFollowsGUI frame = new MyFollowsGUI();
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
	public MyFollowsGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		accountsIFollow = new JScrollPane();
		accountsIFollow.setBounds(new Rectangle(292, 50, 346, 150));
		accountsIFollow.setBounds(185, 58, 239, 84);
		contentPane.add(accountsIFollow);
		
		followsTable = new JTable();
		accountsIFollow.setViewportView(followsTable);
		tableModelFollows = new DefaultTableModel(null, columnNamesFollows);

		followsTable.setModel(tableModelFollows);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(23, 28, 117, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(148, 27, 109, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setForeground(new Color(50, 205, 50));
		lblNewLabel_1.setBounds(270, 28, 23, 19);
		contentPane.add(lblNewLabel_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(31, 72, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(31, 104, 109, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(321, 153, 103, 61);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(278, 11, 89, 36);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(321, 225, 103, 23);
		contentPane.add(btnNewButton_2);
	}
}
