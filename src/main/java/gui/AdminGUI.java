package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnMails;
	private JButton btnCustomerService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(null);
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
	public AdminGUI(String user) {
		try {
			jbInit(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit(String user) throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton QuestionButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		QuestionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuestionGUI(null);

				a.setVisible(true);
			}
		});
		QuestionButton.setBounds(10, 11, 414, 50);
		contentPane.add(QuestionButton);
		
		JButton QuoteButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));
		QuoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuoteGUI();

				a.setVisible(true);
			}
		});
		QuoteButton.setBounds(10, 72, 200, 50);
		contentPane.add(QuoteButton);
		
		JButton EventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		EventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateEventGUI createEvent = new CreateEventGUI(null);
				createEvent .setVisible(true);
			}
		});
		EventButton.setBounds(10, 133, 200, 50);
		contentPane.add(EventButton);
		
		JButton SetResultButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SetResult")); //$NON-NLS-1$ //$NON-NLS-2$
		SetResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SetResultGUI();

				a.setVisible(true);
			}
		});
		SetResultButton.setBounds(220, 72, 204, 50);
		contentPane.add(SetResultButton);
		
		JButton DeleteEventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		DeleteEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteEventGUI deleteEventGUI= new DeleteEventGUI();
				deleteEventGUI.setVisible(true);
				
			}
		});
		DeleteEventButton.setBounds(220, 133, 204, 50);
		contentPane.add(DeleteEventButton);
		
		btnMails = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MailSystem"));
		btnMails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyMessagesGUI myMessagesGUI=new MyMessagesGUI(user); 
				myMessagesGUI.setVisible(true);
			}
		});
		btnMails.setBounds(10, 194, 200, 42);
		contentPane.add(btnMails);
		
		btnCustomerService = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CustomerService"));
		btnCustomerService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientServiceGUI clientServiceGUI= new ClientServiceGUI(user);
				clientServiceGUI.setVisible(true);
			}
		});
		btnCustomerService.setBounds(220, 194, 204, 42);
		contentPane.add(btnCustomerService);
		

		
	}
}
