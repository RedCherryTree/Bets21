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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI();
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
	public AdminGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void jbInit() throws Exception{
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
		QuoteButton.setBounds(10, 72, 414, 50);
		contentPane.add(QuoteButton);
		
		JButton EventButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		EventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateEventGUI createEvent = new CreateEventGUI(null);
				createEvent .setVisible(true);
			}
		});
		EventButton.setBounds(10, 133, 414, 50);
		contentPane.add(EventButton);
		
		
		JButton CloseButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut")); //$NON-NLS-1$ //$NON-NLS-2$
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}

			
		});
		CloseButton.setBounds(142, 218, 147, 32);
		contentPane.add(CloseButton);
		

		
	}
	public void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
}
