package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import domain.Quote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

 
public class CreateQuoteGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

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

	private JButton btnChangeQuoteButton;
	private final JTextField textFieldQuote = new JTextField();
	private final JLabel jLabelMultiplier = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JTextField textFieldMultiplier = new JTextField();
	private final JScrollPane scrollPaneQuotes = new JScrollPane();
	private JTable tableQuotes;

	private JLabel lblEmptyFields;

	public CreateQuoteGUI()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{

		BLFacade facade;
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(90, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

	    facade = MainGUI.getBusinessLogic();
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

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(93, 228, 444, 111));

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelQuotes.getDataVector().clear();
				tableQuotes.updateUI();
				
				scrollPaneQuotes.setEnabled(true);
				int i=tableQueries.getSelectedRow();
				Question q=(Question)tableModelQueries.getValueAt(i,2); 
				Vector<Quote> quotes=q.getQuotes();
				btnChangeQuoteButton.setEnabled(true);
				tableModelQuotes.setDataVector(null, columnNamesQuotes);
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
				tableModelQuotes.getDataVector().clear();
				tableQuotes.updateUI();
				
				scrollPaneQueries.setEnabled(true);
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();
				tableModelQueries.getDataVector().clear();
				tableQueries.updateUI();

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
		

		textFieldMultiplier.setBounds(123, 387, 86, 20);
		textFieldMultiplier.setColumns(10);
		textFieldQuote.setBounds(123, 354, 86, 20);
		textFieldQuote.setColumns(10);
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		
		btnChangeQuoteButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));
		btnChangeQuoteButton.setEnabled(false);
		btnChangeQuoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textFieldQuote.getText()!= null && textFieldMultiplier.getText() != null) {
					try {
						lblEmptyFields.setVisible(true);
						String quoteName= textFieldQuote.getText();
						textFieldQuote.setText("");
						Float multiplier=Float.parseFloat(textFieldMultiplier.getText());
						textFieldMultiplier.setText("");
						int i=tableEvents.getSelectedRow();
						int j=tableQueries.getSelectedRow();
						domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2);
						Question question=ev.getQuestions().get(j);
						Quote quote=facade.createQuote(question.getQuestionNumber(), quoteName, multiplier);
						Vector<Object> row = new Vector<Object>();
						row.add(quote.getQuoteNumber());
						row.add(quote.getQuoteName());
						row.add(quote.getQuoteMultiplier());
						row.add(quote);
						tableModelQuotes.addRow(row);	
					}
					catch(Exception ex){
//						lblErrorLabel.setVisible(true);
					}
				}
				else {
					lblEmptyFields.setVisible(true);
				}
			}
		});
		btnChangeQuoteButton.setBounds(506, 370, 113, 54);
		getContentPane().add(btnChangeQuoteButton);
		
		JLabel jLabelQuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quote"));
		jLabelQuote.setBounds(40, 357, 73, 14);
		getContentPane().add(jLabelQuote);
		
		getContentPane().add(textFieldQuote);
		jLabelMultiplier.setBounds(40, 390, 73, 14);
		
		getContentPane().add(jLabelMultiplier);
		
		getContentPane().add(textFieldMultiplier);
		scrollPaneQuotes.setBounds(237, 350, 259, 79);
		
		getContentPane().add(scrollPaneQuotes);
		
		tableQuotes = new JTable();
		tableQuotes = new JTable();
		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableQuotes.setModel(tableModelQuotes);
		
		lblEmptyFields = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyField"));
		lblEmptyFields.setForeground(new Color(255, 0, 0));
		lblEmptyFields.setBounds(40, 415, 177, 14);
		getContentPane().add(lblEmptyFields);
		lblEmptyFields.setVisible(false);
		
		tableModelQuotes.setDataVector(null, columnNamesQuotes);
		tableModelQuotes.setColumnCount(4);//Another row for the transactions
		
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(2).setPreferredWidth(175);
		tableQuotes.getColumnModel().removeColumn(tableQuotes.getColumnModel().getColumn(3));
		
		tableQuotes.addMouseListener(new MouseAdapter() {
			
			
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
}

