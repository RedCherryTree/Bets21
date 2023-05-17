package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.RegisteredUser;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;

public class UserGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnCustomerService;
	private JButton jButtonHistory;
	private JButton jButtonMoney;
	private JSplitPane splitPaneDown;
	private JButton btnMailSystem;
	private JButton btnMultipleCuoteBet;
	private JButton jBet;
	private JSplitPane splitPane;
	private JButton jButtonQueryQueries;
	private JLabel jLabelUsername;
	private JLabel jLabelMoney;
	private JSplitPane splitPaneUp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UserGUI frame = new UserGUI("XX");
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
	public UserGUI(String user) {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		setBounds(100, 100, 573, 417);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel jContentPane = new JPanel();
		contentPane.add(jContentPane);
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
		jLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+": "+user);
		jLabelUsername.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelUsername.setForeground(Color.BLACK);
		jLabelUsername.setFont(new Font("Dialog", Font.BOLD, 13));
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		jLabelMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money")+": "+(double)Math.round(facade.getUserMoney(user))+" €");
		jLabelMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMoney.setForeground(Color.BLACK);
		jLabelMoney.setFont(new Font("Dialog", Font.BOLD, 13));
		
		splitPaneUp = new JSplitPane();
		splitPaneUp.setResizeWeight(0.52);
		splitPaneUp.setLeftComponent(jLabelUsername);
		splitPaneUp.setRightComponent(jLabelMoney);
		jContentPane.add(splitPaneUp);
		
		
		jButtonQueryQueries = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindQuestionsGUI findQGUI= new FindQuestionsGUI();
				findQGUI.setVisible(true);
			}
		});
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.52);
		jContentPane.add(splitPane);
		
		jBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		splitPane.setLeftComponent(jBet);
		
		btnMultipleCuoteBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MultipleQuoteBet"));
		btnMultipleCuoteBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleQuoteBetGUI multipleQuoteBetGUI= new MultipleQuoteBetGUI(user);
				multipleQuoteBetGUI.setVisible(true);
			}
		});
		splitPane.setRightComponent(btnMultipleCuoteBet);
		jBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetGUI betGUI= new BetGUI(user);
				betGUI.setVisible(true);
				close_actionPerformed(e); 
			}
		});
		
		JSplitPane splitPane_1 = new JSplitPane();
		jContentPane.add(splitPane_1);
		
		JButton btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyFollows")); //$NON-NLS-1$ //$NON-NLS-2$
		splitPane_1.setLeftComponent(btnNewButton_1);
		
		btnMailSystem = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MailSystem"));
		splitPane_1.setRightComponent(btnMailSystem);
		btnMailSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyMessagesGUI messagesGUI= new MyMessagesGUI(user);
				messagesGUI.setVisible(true);
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyFollowsGUI followsGUI= new MyFollowsGUI(user);
				followsGUI.setVisible(true);
			}
		});
		
		btnCustomerService = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CustomerService"));
		btnCustomerService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenTiquetGUI clientService=new OpenTiquetGUI(user);
				clientService.setVisible(true);
			}
		});
		jContentPane.add(btnCustomerService);
		jContentPane.add(jButtonQueryQueries);
		
		jButtonHistory = new JButton(ResourceBundle.getBundle("Etiquetas").getString("History"));
		jButtonHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyAccountBalanceGUI myBalanceGUI= new MyAccountBalanceGUI(user);
				myBalanceGUI.setVisible(true);
			}
		});
		jButtonMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddMoney"));
		jButtonMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepositMoneyGUI depositGUI= new DepositMoneyGUI(user);
				depositGUI.setVisible(true);
				close_actionPerformed(e);
			}
		});
		splitPaneDown = new JSplitPane();
		splitPaneDown.setLeftComponent(jButtonHistory);
		splitPaneDown.setRightComponent(jButtonMoney);
		splitPaneDown.setResizeWeight(0.52);
		jContentPane.add(splitPaneDown);
		
	}
	private void close_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
