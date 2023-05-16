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

public class BetMakeChoiceGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					BetMakeChoiceGUI frame = new BetMakeChoiceGUI("XX");
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
	public BetMakeChoiceGUI(String user) {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		setBounds(100, 100, 573, 417);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel jContentPane = new JPanel();
		contentPane.add(jContentPane);
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
		JLabel jLabelUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+": "+user);
		jLabelUsername.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelUsername.setForeground(Color.BLACK);
		jLabelUsername.setFont(new Font("Dialog", Font.BOLD, 13));
		
		BLFacade facade = MainGUI.getBusinessLogic();
		JLabel jLabelMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money")+": "+(double)Math.round(facade.getUserMoney(user))+" €");
		jLabelMoney.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMoney.setForeground(Color.BLACK);
		jLabelMoney.setFont(new Font("Dialog", Font.BOLD, 13));
		JSplitPane splitPaneUp = new JSplitPane();
		splitPaneUp.setResizeWeight(0.52);
		splitPaneUp.setLeftComponent(jLabelUsername);
		splitPaneUp.setRightComponent(jLabelMoney);
		jContentPane.add(splitPaneUp);
		
		
		JButton jButtonQueryQueries = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MultipleBet"));
		jButtonQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MultipleBetGUI mBetGUI= new MultipleBetGUI(user);
				mBetGUI.setVisible(true);
			}
		});
		jContentPane.add(jButtonQueryQueries);
		
		JButton jButtonLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetGUI betGUI= new BetGUI(user);
				betGUI.setVisible(true);
				close_actionPerformed(e); 
			}
		});
		jContentPane.add(jButtonLogin);
		
	}
	private void close_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
