package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.JTree;
import javax.swing.JInternalFrame;
import java.awt.TextField;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RankingGUI extends JFrame {

	private JPanel contentPane;

	private JScrollPane rankingPanel = new JScrollPane();
	private JTable tableRanking= new JTable();
	private DefaultTableModel tableModelRanking;
	
	private String[] columnNamesRanking = new String[] {
			"Ranking", 
			ResourceBundle.getBundle("Etiquetas").getString("Username"), 
			ResourceBundle.getBundle("Etiquetas").getString("Mail"), 
			ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney"), 
			ResourceBundle.getBundle("Etiquetas").getString("WinPercentage"), 

	};

	private JButton btnMyRanking;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RankingGUI frame = new RankingGUI();
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
	public RankingGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rankingPanel = new JScrollPane();

		rankingPanel.setBounds(new Rectangle(292, 50, 346, 150));
		rankingPanel.setBounds(10, 52, 425, 183);
		contentPane.add(rankingPanel);
		
		tableRanking = new JTable();
		rankingPanel.setViewportView(tableRanking);
		tableModelRanking = new DefaultTableModel(null, columnNamesRanking);

		tableRanking.setModel(tableModelRanking);
		
		btnMyRanking = new JButton("New button");
		btnMyRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMyRanking.setBounds(268, 11, 167, 23);
		contentPane.add(btnMyRanking);
		tableModelRanking.setColumnCount(6);
		
		tableRanking.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableRanking.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableRanking.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableRanking.getColumnModel().getColumn(3).setPreferredWidth(60);
		tableRanking.getColumnModel().getColumn(4).setPreferredWidth(60);
		tableRanking.getColumnModel().removeColumn(tableRanking.getColumnModel().getColumn(5));
	}
}
