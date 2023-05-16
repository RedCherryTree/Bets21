package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Message;
import domain.SuggestEvent;
import domain.SuggestRemoval;
import domain.Ticket;
import exceptions.DateExpired;
import exceptions.EventAlreadyExist;
import exceptions.EventDontExist;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientServiceGUI extends JFrame {

	private JPanel contentPane;

	private JScrollPane ticketsSPanel = new JScrollPane();
	private JTable tableTickets= new JTable();
	private DefaultTableModel tableModelTicket;
	
	private String[] columnNamesTickets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("TicketN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Client"), 
			ResourceBundle.getBundle("Etiquetas").getString("Description")

	};

	private JButton btnReadMessage;

	private JButton btnManageTicket;
	private JButton btnManagingTickets;
	private JButton btnWriteToClient;

	private JLabel lblClient;
	private JButton btnAcceptProposal;
	private JButton btnDenyProposal;
	private JLabel lblInfo;
	private JLabel lblTicketType;
	private JLabel lblEvent;
	private JLabel lblDate;
	private JButton btnFinishTicket;
	private JButton btnRefresh;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientServiceGUI frame = new ClientServiceGUI("XX");
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
	public ClientServiceGUI(String admin) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setBounds(100, 100, 541, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ticketsSPanel = new JScrollPane();

		ticketsSPanel.setBounds(new Rectangle(292, 50, 346, 150));
		ticketsSPanel.setBounds(10, 39, 504, 125);
		contentPane.add(ticketsSPanel);
		
		tableTickets = new JTable();
		ticketsSPanel.setViewportView(tableTickets);
		tableModelTicket = new DefaultTableModel(null, columnNamesTickets);

		tableTickets.setModel(tableModelTicket);
		
		tableModelTicket.setColumnCount(4);
		tableTickets.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableTickets.getColumnModel().getColumn(1).setPreferredWidth(25);
		tableTickets.getColumnModel().getColumn(2).setPreferredWidth(268);
		tableTickets.getColumnModel().removeColumn(tableTickets.getColumnModel().getColumn(3));
		
		Vector<Ticket> tickets= facade.getNewTickets();
		for(Ticket t: tickets) {
			Vector<Object> row = new Vector<Object>();
			row.add(t.getTiquetNumber());
			row.add(t.getUser().getUsername());
			row.add(t.getDescription());
			row.add(t);
			tableModelTicket.addRow(row);
		} 
		

		tableTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				
				btnAcceptProposal.setEnabled(false);
				btnDenyProposal.setEnabled(false);
				btnManageTicket.setEnabled(true);
				lblClient.setText(ResourceBundle.getBundle("Etiquetas").getString("Client")+": "+ticket.getUser().getUsername());
				if(!ticket.getEgoera().equals(Ticket.getEGOERA_TRATATZEN())){
					btnReadMessage.setEnabled(true);
				}
				if(ticket instanceof Ticket) {
					lblTicketType.setText(ResourceBundle.getBundle("Etiquetas").getString("TicketType")+": "
				    +ResourceBundle.getBundle("Etiquetas").getString("SimpleTicket"));
					System.out.println("Paella");
				}
				else {
					System.out.println("Chorizo");
					String eventDesc="";
					String eventDate="";
					if(ticket.getEgoera().equals(Ticket.getEGOERA_TRATATZEN())) {
						btnAcceptProposal.setEnabled(true);
						btnDenyProposal.setEnabled(true);
					}
					if(ticket instanceof SuggestRemoval) {
						lblTicketType.setText(ResourceBundle.getBundle("Etiquetas").getString("TicketType")+": "
							    +ResourceBundle.getBundle("Etiquetas").getString("SuggestRemoval"));
						SuggestRemoval sr=(SuggestRemoval)ticket;
						eventDesc=sr.getEvent().getDescription();
						eventDate=sr.getEvent().getEventDate().toString();
					}
					else {//ticket instanceof SuggestEvent
						lblTicketType.setText(ResourceBundle.getBundle("Etiquetas").getString("TicketType")+": "
							    +ResourceBundle.getBundle("Etiquetas").getString("SuggestEvent"));
						SuggestEvent se=(SuggestEvent)ticket;
						eventDesc=se.getEventDescription();
						eventDate=se.getEventDate().toString();
					}
					lblEvent.setVisible(true);
					lblDate.setVisible(true);
					lblEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Event")+": "+eventDesc);
					lblDate.setText(ResourceBundle.getBundle("Etiquetas").getString("EventDate")+": "+eventDate);
				}
			}
		});
		
		
		btnReadMessage = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReadDescription"));
		btnReadMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				ReadTicketGUI readTicketGUI= new ReadTicketGUI(admin, ticket.getDescription());
				readTicketGUI.setVisible(true);
			}
		});
		btnReadMessage.setBounds(276, 192, 114, 51);
		contentPane.add(btnReadMessage);
		btnReadMessage.setEnabled(false);
		
		btnManageTicket = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ManageTicket"));
		btnManageTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				facade.manageTicket(ticket.getTiquetNumber(), admin);
				tableModelTicket.getDataVector().remove(i);
				tableTickets.updateUI();
			}
		});
		btnManageTicket.setBounds(400, 192, 114, 51);
		contentPane.add(btnManageTicket);
		btnManageTicket.setEnabled(false);
		
		btnManagingTickets = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ManagingTickets")); //$NON-NLS-1$ //$NON-NLS-2$
		btnManagingTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("ManagingTickets"));
				tableModelTicket.getDataVector().clear();
				tableTickets.updateUI();
				Vector<Ticket> tickets= facade.getManagingTickets(admin);
				for(Ticket t: tickets) {
					Vector<Object> row = new Vector<Object>();
					row.add(t.getTiquetNumber());
					row.add(t.getUser().getUsername());
					row.add(t.getDescription());
					row.add(t);
					tableModelTicket.addRow(row);
				} 
			}
		});
		btnManagingTickets.setBounds(332, 5, 182, 23);
		contentPane.add(btnManagingTickets);
		
		btnWriteToClient = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WriteToClient")); //$NON-NLS-1$ //$NON-NLS-2$
		btnWriteToClient.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnWriteToClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				NewMessageGUI newMessageGUI=new NewMessageGUI(admin, ticket.getUser().getUsername());
				newMessageGUI.setVisible(true);
			}
		});
		btnWriteToClient.setBounds(191, 5, 131, 23);
		contentPane.add(btnWriteToClient);
		btnWriteToClient.setEnabled(false);
		
		lblClient = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Client")+": ");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblClient.setBounds(20, 175, 217, 23);
		contentPane.add(lblClient);
		
		btnAcceptProposal = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AcceptProposal")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAcceptProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				if(ticket instanceof SuggestRemoval) {
					try {
						facade.deleteEvent(((SuggestRemoval) ticket).getEvent().getEventNumber(), ((SuggestRemoval) ticket).getEvent().getEventDate());
						facade.sendMessage(admin, ticket.getUser().getUsername(), "Your petition has been accepted", 
								ticket.getTiquetNumber()+". Ticket, suggesting us to remove an event has decided to be accepted.");
					} catch (DateExpired e1) {
						//  Auto-generated catch block
						e1.printStackTrace();
					} catch (EventDontExist e1) {
						//  Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {//ticket instanceof SuggestEvent
					try {
						facade.createEvent(((SuggestEvent) ticket).getEventDescription(), ((SuggestEvent) ticket).getEventDate());
						facade.sendMessage(admin, ticket.getUser().getUsername(), "Your petition has been accepted", 
								ticket.getTiquetNumber()+". Ticket, suggesting us to add an event has decided to be accepted.");
					} catch (DateExpired | EventAlreadyExist e1) {
						// Auto-generated catch block
						e1.printStackTrace();
					}
				}
				facade.setTicketConcluded(ticket.getTiquetNumber(), admin);
				tableModelTicket.getDataVector().remove(i);
				tableTickets.updateUI();
			}
		});
		btnAcceptProposal.setBounds(276, 254, 114, 29);
		contentPane.add(btnAcceptProposal);
		btnAcceptProposal.setEnabled(false);
		
		btnDenyProposal = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DenyProposal")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDenyProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				if(ticket instanceof SuggestRemoval) {
					facade.sendMessage(admin, ticket.getUser().getUsername(), "Your petition has been rejected", 
							ticket.getTiquetNumber()+". Ticket, suggesting us to remove an event has decided to be rejected.");
				}
				else {//ticket instanceof SuggestEvent
					facade.sendMessage(admin, ticket.getUser().getUsername(), "Your petition has been rejected", 
							ticket.getTiquetNumber()+". Ticket, suggesting us to add an event has decided to be rejected.");
				}
				facade.setTicketConcluded(ticket.getTiquetNumber(), admin);
				tableModelTicket.getDataVector().remove(i);
				tableTickets.updateUI();
			}
		});
		btnDenyProposal.setBounds(400, 254, 114, 29);
		contentPane.add(btnDenyProposal);
		btnDenyProposal.setEnabled(false);
		
		lblInfo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NewTickets")); //$NON-NLS-1$ //$NON-NLS-2$
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInfo.setBounds(10, 21, 164, 14);
		contentPane.add(lblInfo);
		
		lblTicketType = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TicketType")+": "); //$NON-NLS-1$ //$NON-NLS-2$
		lblTicketType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTicketType.setBounds(20, 209, 217, 25);
		contentPane.add(lblTicketType);
		
		lblEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event")+": "); //$NON-NLS-1$ //$NON-NLS-2$
		lblEvent.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEvent.setBounds(20, 245, 217, 23);
		lblEvent.setVisible(false);
		contentPane.add(lblEvent);
		lblEvent.setVisible(false);
		
		lblDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate")+": "); //$NON-NLS-1$ //$NON-NLS-2$
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(20, 286, 211, 14);
		lblDate.setVisible(false);
		contentPane.add(lblDate);
		
		btnFinishTicket = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FinishTicket")); //$NON-NLS-1$ //$NON-NLS-2$
		btnFinishTicket.setFont(btnFinishTicket.getFont().deriveFont(btnFinishTicket.getFont().getStyle() | Font.BOLD));
		btnFinishTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableTickets.getSelectedRow();
				domain.Ticket ticket=(domain.Ticket)tableModelTicket.getValueAt(i,3);
				facade.setTicketConcluded(ticket.getTiquetNumber(), admin);
				tableModelTicket.getDataVector().remove(i);
				tableTickets.updateUI();
			}
		});
		btnFinishTicket.setBounds(276, 294, 238, 48);
		contentPane.add(btnFinishTicket);
		lblDate.setVisible(false);
		btnFinishTicket.setEnabled(false);
		
		btnRefresh = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Refresh")); //$NON-NLS-1$ //$NON-NLS-2$
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInfo.setText(ResourceBundle.getBundle("Etiquetas").getString("NewTickets"));
				tableModelTicket.getDataVector().clear();
				tableTickets.updateUI();
				Vector<Ticket> tickets= facade.getNewTickets();
				for(Ticket t: tickets) {
					Vector<Object> row = new Vector<Object>();
					row.add(t.getTiquetNumber());
					row.add(t.getUser().getUsername());
					row.add(t.getDescription());
					row.add(t);
					tableModelTicket.addRow(row);
				} 
			}
		});
		btnRefresh.setBounds(20, 319, 89, 23);
		contentPane.add(btnRefresh);
		
	}
	
}
