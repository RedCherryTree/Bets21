package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Question;
import domain.Quote;
import domain.User;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class BetGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;


	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Quote")

	};
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	private JButton btnBet;

	private JLabel lblErrorLabel;
	private final JRadioButton rdbtnLocal = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("BetGUI.rdbtnNewRadioButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JRadioButton rdbtnTies = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("BetGUI.rdbtnNewRadioButton_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JRadioButton rdbtnVisiting = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("BetGUI.rdbtnNewRadioButton_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public BetGUI(String user)
	{
		try
		{
			jbInit(user);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit(String user) throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(93, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(251, 420, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(93, 228, 444, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneQueries.setEnabled(true);
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					try {
//						row.add(q.getQuestionQuote().getQuotes());
					}
					catch(NullPointerException ex) {
						row.add(ResourceBundle.getBundle("Etiquetas").getString("StillNoQuote"));
					}
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(75);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);;

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(75);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableQueries.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int i=tableQueries.getSelectedRow();
			if(tableQueries.getValueAt(i, 2) != null) {
			String s=(String)tableQueries.getValueAt(i, 2);
			rdbtnLocal.setEnabled(true);
			rdbtnTies.setEnabled(true);
			rdbtnVisiting.setEnabled(true);
			
			if(buttonGroup.isSelected(null)) {
			btnBet.setEnabled(true);
			}
			
			if(s.equals(ResourceBundle.getBundle("Etiquetas").getString("StillNoQuote"))){
				textField.setEnabled(true);
				textField_1.setEnabled(true);
				textField_2.setEnabled(true);
				btnBet.setEnabled(true);
			}
			}
			else {

			//	textField.setEnabled(true);
			//	textField_1.setEnabled(true);
			//	textField_2.setEnabled(true);
				
				
			}
			}
			});


		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		
		
		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));
		btnBet.setEnabled(false);
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableEvents.getSelectedRow();
				int j=tableQueries.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2);
				Question question=ev.getQuestions().get(j);
				
				Quote quote = new Quote(question,(String)tableQueries.getValueAt(i, 2),(Float)tableQueries.getValueAt(i, 2));
				int who = 0;
				if(rdbtnLocal.isSelected()) who = 1;
				if(rdbtnTies.isSelected()) who = 2;
				if(rdbtnVisiting.isSelected()) who = 3;
				// TODO: aldatu hay, dirua jaso apostu bat egiterakoan
				double money=0.0;
				facade.bet(user, money, question, who);
				
			}
		});
		btnBet.setBounds(474, 373, 129, 30);
		getContentPane().add(btnBet);
		
		lblErrorLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		lblErrorLabel.setForeground(Color.RED);
		lblErrorLabel.setBounds(10, 423, 231, 25);
		getContentPane().add(lblErrorLabel);
		buttonGroup.add(rdbtnLocal);
		rdbtnLocal.setBounds(93, 351, 109, 23);
		
		getContentPane().add(rdbtnLocal);
		buttonGroup.add(rdbtnTies);
		rdbtnTies.setBounds(214, 351, 109, 23);
		
		getContentPane().add(rdbtnTies);
		buttonGroup.add(rdbtnVisiting);
		rdbtnVisiting.setBounds(340, 351, 109, 23);
		
		getContentPane().add(rdbtnVisiting);
		lblErrorLabel.setVisible(false);
		rdbtnLocal.setEnabled(false);
		rdbtnTies.setEnabled(false);
		rdbtnVisiting.setEnabled(false);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
