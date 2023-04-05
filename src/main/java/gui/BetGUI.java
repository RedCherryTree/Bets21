package gui;

import java.awt.Color;
import java.awt.Component;
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
import java.awt.Font;
import javax.swing.JSpinner;

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
	private JScrollPane scrollPaneQuotes = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableQuotes= new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelQuotes;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),

	};
	
	private String[] columnNamesQuotes = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QuoteNumber"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteName"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteMultiplier"),

	};
	


	private JButton btnBet;

	private JLabel lblErrorLabel;
	private final JPanel panel = new JPanel();

	private JLabel lblMinimumBet;

	private JLabel lblCurrentMoney;


	private JLabel lblQuestion;

	private JLabel lblQuote;

	private JLabel lblEvent;

	private JLabel lblMoneyBet;

	private JSpinner spinnerBet;

	private JLabel lblMultiplier;

	private JLabel lblErrors;
	private final JLabel lblNewLabel = new JLabel("€"); //$NON-NLS-1$ //$NON-NLS-2$

	private JButton btnGoBack;
	

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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Bet"));

		jLabelEventDate.setBounds(new Rectangle(40, 35, 140, 14));
		jLabelQueries.setBounds(20, 211, 406, 14);
		jLabelEvents.setBounds(294, 34, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);


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



					CreateQuoteGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													


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
					tableModelQuotes.getDataVector().clear();
					tableQuotes.updateUI();
					tableModelQueries.getDataVector().clear();
					tableQueries.updateUI();
				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(20, 230, 321, 90));

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelQuotes.getDataVector().clear();
				tableQuotes.updateUI();
				
				lblEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Event")+": ");
				lblQuestion.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": ");
				lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": ");
				lblMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": ");
				lblMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")+": ");
				btnBet.setEnabled(false);
				spinnerBet.setEnabled(false);
				
				scrollPaneQuotes.setEnabled(true);
				int i=tableQueries.getSelectedRow();
				Question q=(Question)tableModelQueries.getValueAt(i,2); 
				Vector<Quote> quotes=q.getQuotes();

				if (quotes==null) {
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("StillNoQuote")+": "+q.getQuestion());
				}else {
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuestion")+" "+q.getQuestion());
					for (domain.Quote qt:quotes){
						Vector<Object> row = new Vector<Object>();
						row.add(qt.getQuoteNumber());
						row.add(qt.getQuoteName());
						row.add(qt.getQuoteMultiplier());
						row.add(qt);
						tableModelQuotes.addRow(row);	
				}
					
				}

			}
		});
		
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelQueries.getDataVector().clear();
				tableQueries.updateUI();
				tableModelQuotes.getDataVector().clear();
				tableQuotes.updateUI();
				
				lblEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Event")+": ");
				lblQuestion.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": ");
				lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": ");
				lblMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": ");
				lblMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")+": ");
				btnBet.setEnabled(false);
				spinnerBet.setEnabled(false);
				
				scrollPaneQueries.setEnabled(true);
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();


				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(75);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableModelQueries.setDataVector(null, columnNamesQueries);
		tableModelQueries.setColumnCount(3);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(75);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
		

		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		

		scrollPaneQuotes.setBounds(358, 230, 280, 90);
		
		getContentPane().add(scrollPaneQuotes);
		
		tableQuotes = new JTable();
		tableQuotes = new JTable();
		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableQuotes.setModel(tableModelQuotes);
		panel.setBounds(40, 331, 598, 130);
		
		tableQuotes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object 
				lblEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Event")+": "+ ev.getDescription());
				int iques=tableQueries.getSelectedRow();
				
				domain.Question question=(domain.Question)tableModelQueries.getValueAt(iques,2);
				lblQuestion.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": "+question.getQuestion());
				lblMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": "+question.getBetMinimum());
				
				int quot=tableQuotes.getSelectedRow();
				domain.Quote quote=(domain.Quote)tableModelQuotes.getValueAt(quot,3);
				lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": "+quote.getQuoteName());
				lblMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")+":        x"+quote.getQuoteMultiplier());
				
				btnBet.setEnabled(true);
				spinnerBet.setEnabled(true);
				spinnerBet.setValue(question.getBetMinimum());
			}
		});
		
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblMinimumBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": ");
		lblMinimumBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMinimumBet.setBounds(30, 94, 122, 14);
		panel.add(lblMinimumBet);
		
		lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": ");
		lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuestion.setBounds(30, 44, 306, 14);
		panel.add(lblQuestion);
//		lblQuestion.setVisible(false);
		
		lblQuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": ");
		lblQuote.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuote.setBounds(30, 69, 217, 14);
		panel.add(lblQuote);
		
		lblMoneyBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+":");
		lblMoneyBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMoneyBet.setBounds(257, 69, 79, 14);
		panel.add(lblMoneyBet);
		
	    btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet")+"!"); //$NON-NLS-1$ //$NON-NLS-2$
	    btnBet.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int iques=tableQueries.getSelectedRow();
				domain.Question question=(domain.Question)tableModelQueries.getValueAt(iques,2);
				int quot=tableQuotes.getSelectedRow();
				domain.Quote quote=(domain.Quote)tableModelQuotes.getValueAt(quot,3);
	    		double betMon= (int)spinnerBet.getValue();
	    		if(betMon>facade.getUserMoney(user)) {
	    			lblErrors.setVisible(true);
	    			lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnouhgMoney")+"!");
	    		}
	    		else {
	    			if(betMon<question.getBetMinimum()) {
	    				lblErrors.setVisible(true);
	    				lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("HigherBet")+"!");
	    			}
	    			else {
	    				facade.bet(user, betMon, quote.getQuoteNumber());
	    				lblErrors.setVisible(false);
	    				PurchaseGUI purchaseGUI= new PurchaseGUI(user);
	    				purchaseGUI.setVisible(true);
	    				close_actionPerformed(e);
	    			}
	    		}
	    	}
	    });
	    btnBet.setFont(new Font("Tahoma", Font.BOLD, 20));
	    btnBet.setBounds(426, 11, 162, 71);
		panel.add(btnBet);
		btnBet.setEnabled(false);
		
		lblEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event")+": ");
		lblEvent.setBounds(20, 11, 306, 12);
		panel.add(lblEvent);
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		spinnerBet = new JSpinner();
		spinnerBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		spinnerBet.setBounds(342, 66, 53, 20);
		panel.add(spinnerBet);
		spinnerBet.setEnabled(false);
		
		lblMultiplier = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")+": ");
		lblMultiplier.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMultiplier.setBounds(257, 94, 138, 14);
		panel.add(lblMultiplier);
		lblNewLabel.setBounds(395, 69, 21, 14);
		
		panel.add(lblNewLabel);
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnGoBack.setBounds(467, 94, 121, 25);
		panel.add(btnGoBack);
		btnGoBack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI userGUI= new UserGUI(user);
				userGUI.setVisible(true);
				close_actionPerformed(e);
			}
		});
//		lblQuote.setVisible(false);
		
		lblCurrentMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentMoney")+": "+facade.getUserMoney(user)+"€");
		lblCurrentMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCurrentMoney.setBounds(480, 11, 158, 12);
		getContentPane().add(lblCurrentMoney);
		
		lblErrors = new JLabel();
		lblErrors.setBounds(453, 331, 185, 14);
		getContentPane().add(lblErrors);
		lblErrors.setForeground(Color.RED);
		lblErrors.setVisible(false);
		
		
		tableModelQuotes.setDataVector(null, columnNamesQuotes);
		tableModelQuotes.setColumnCount(4);//Another row for the transactions
		
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(2).setPreferredWidth(175);
		tableQuotes.getColumnModel().removeColumn(tableQuotes.getColumnModel().getColumn(3));
		
		tableQuotes.addMouseListener(new MouseAdapter() {
			int i=tableQuotes.getSelectedRow();
//			Quote q=(Quote)tableModelQuotes.getValueAt(i,3); 
			
			
		});


	}

	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed to cyan.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		
			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}


	private void close_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}