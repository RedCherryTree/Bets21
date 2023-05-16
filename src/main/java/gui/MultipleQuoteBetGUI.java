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
import java.awt.SystemColor;

public class MultipleQuoteBetGUI extends JFrame {

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
	private JScrollPane scrollPaneMyQuotes = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableQuotes= new JTable();
	private JTable tableMyQuotes= new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelQuotes;
	private DefaultTableModel tableModelMyQuotes;
	private DefaultTableModel tableModelMultiple;

	
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
	


	private JButton btnAdd;

	private JLabel lblErrorLabel;

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
	private JLabel lblNewLabel_1;

	private JLabel lblHasFinished;
	private JLabel lblBetQuote;

	private JLabel lblQuotes2;

	private JButton btnRemoveQuote;

	private JButton btnBet;
	private JLabel lblError;
	

	public MultipleQuoteBetGUI(String user)
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

	private double mult=1.0;
	private double minBet=0;
	private JLabel lblTotalMultiplier;
	private JLabel lblFinalMinimumBet;
	
	private void jbInit(String user) throws Exception
	{

		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(758, 532));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Bet"));

		jLabelEventDate.setBounds(new Rectangle(67, 11, 140, 14));
		jLabelQueries.setForeground(SystemColor.menuText);
		jLabelQueries.setBounds(386, 121, 406, 14);
		jLabelEvents.setBounds(388, 10, 121, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);


		jCalendar1.setBounds(new Rectangle(65, 30, 225, 135));

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
		
		scrollPaneEvents.setBounds(new Rectangle(388, 30, 346, 80));
		scrollPaneQueries.setBounds(new Rectangle(388, 137, 346, 80));

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
				btnAdd.setEnabled(false);
				spinnerBet.setEnabled(false);
				
				scrollPaneQuotes.setEnabled(true);
				int i=tableQueries.getSelectedRow();
				Question q=(Question)tableModelQueries.getValueAt(i,2); 
				Vector<Quote> quotes=q.getQuotes();
				if(q.isHasFinished()) {
					tableQuotes.setEnabled(false);
					lblHasFinished.setVisible(true);	
				}
				else {
					tableQuotes.setEnabled(true);
					lblHasFinished.setVisible(false);
				}
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
				btnAdd.setEnabled(false);
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
		

		scrollPaneQuotes.setBounds(386, 241, 348, 88);
		
		getContentPane().add(scrollPaneQuotes);
		
		tableQuotes = new JTable();
		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableQuotes.setModel(tableModelQuotes);
		
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
				
				btnAdd.setEnabled(true);
			}
		});
//		lblQuote.setVisible(false);
		
		lblCurrentMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CurrentMoney")+": "+(double)Math.round(facade.getUserMoney(user))+"€");
		lblCurrentMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCurrentMoney.setBounds(576, 5, 158, 25);
		getContentPane().add(lblCurrentMoney);
		
		lblErrors = new JLabel();
		lblErrors.setBounds(453, 331, 185, 14);
		getContentPane().add(lblErrors);
		lblErrors.setForeground(Color.RED);
		
		lblHasFinished = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("HasFinished"));
		lblHasFinished.setForeground(Color.RED);
		lblHasFinished.setBounds(473, 121, 259, 14);
		getContentPane().add(lblHasFinished);
		lblHasFinished.setVisible(false);	
		lblErrors.setVisible(false);
		
		
		tableModelQuotes.setDataVector(null, columnNamesQuotes);
		tableModelQuotes.setColumnCount(4);//Another row for the transactions
		
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(175);
		tableQuotes.getColumnModel().getColumn(2).setPreferredWidth(175);
		tableQuotes.getColumnModel().removeColumn(tableQuotes.getColumnModel().getColumn(3));
		
		scrollPaneMyQuotes.setBounds(29, 241, 297, 88);
		
		getContentPane().add(scrollPaneMyQuotes);
		tableMyQuotes = new JTable();
		tableMyQuotes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int quot=tableQuotes.getSelectedRow();
				domain.Quote quote=(domain.Quote)tableModelQuotes.getValueAt(quot,3);
				
				lblEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Event")+": "+ quote.getQuestion().getEvent().getDescription());
				int iques=tableQueries.getSelectedRow();
				
				lblQuestion.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": "+quote.getQuestion().getQuestion());
				lblMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": "+quote.getQuestion().getBetMinimum());
				
				lblQuote.setText(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": "+quote.getQuoteName());
				lblMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("Multiplier")+":        x"+quote.getQuoteMultiplier());
				
				btnRemoveQuote.setEnabled(true);
			}
		});
		scrollPaneMyQuotes.setViewportView(tableMyQuotes);
		tableModelMyQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableModelMyQuotes.setColumnCount(4);
		tableMyQuotes.setModel(tableModelMyQuotes);
		tableMyQuotes.getColumnModel().getColumn(0).setPreferredWidth(175);
		tableMyQuotes.getColumnModel().getColumn(1).setPreferredWidth(175);
		tableMyQuotes.getColumnModel().getColumn(2).setPreferredWidth(175);
		tableMyQuotes.getColumnModel().removeColumn(tableMyQuotes.getColumnModel().getColumn(3));
		
		btnGoBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnGoBack.setBounds(576, 444, 140, 25);
		getContentPane().add(btnGoBack);
		btnGoBack.setFont(new Font("Tahoma", Font.BOLD, 13));
		
	    btnAdd = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddQuote"));
	    btnAdd.setBounds(371, 367, 138, 63);
	    getContentPane().add(btnAdd);
	    btnAdd.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
				int quot=tableQuotes.getSelectedRow();
				domain.Quote quote=(domain.Quote)tableModelQuotes.getValueAt(quot,3);
				Vector<Vector> vector= tableModelMyQuotes.getDataVector();
				boolean alreadyIs=alreadyExistQuote(vector, quote);
				if(!alreadyIs) {
					lblError.setVisible(false);
					Vector<Object> row = new Vector<Object>();
					
					row.add(quote.getQuoteNumber());
					row.add(quote.getQuoteName());
					row.add(quote.getQuoteMultiplier());
					row.add(quote);
					tableModelMyQuotes.addRow(row);	 
					
					minBet+=quote.getQuestion().getBetMinimum();
					mult=mult*quote.getQuoteMultiplier();
					spinnerBet.setValue(minBet);
					lblTotalMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("TotalMultiplier")+": x"+mult);
					lblFinalMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("FinalMinimumBet")+": "+minBet+"€");
					
					if(vector.size()>1) {
						btnBet.setEnabled(true);
						spinnerBet.setEnabled(true);
					}
				}
				else {
					lblError.setVisible(true);
				}
	    	}
	    });
	    btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
	    btnAdd.setEnabled(false);
	    
	    lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")+": ");
	    lblQuestion.setBounds(29, 382, 306, 14);
	    getContentPane().add(lblQuestion);
	    lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    
	    spinnerBet = new JSpinner();
	    spinnerBet.setBounds(273, 407, 53, 20);
	    getContentPane().add(spinnerBet);
	    spinnerBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    spinnerBet.setEnabled(false);
	    lblNewLabel.setBounds(340, 407, 21, 14);
	    getContentPane().add(lblNewLabel);
	    
	    lblMoneyBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+":");
	    lblMoneyBet.setBounds(184, 407, 79, 14);
	    getContentPane().add(lblMoneyBet);
	    lblMoneyBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    
	    lblMultiplier = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuoteMultiplier")+": "+"x"+mult);
	    lblMultiplier.setBounds(29, 432, 142, 14);
	    getContentPane().add(lblMultiplier);
	    lblMultiplier.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    //		lblQuestion.setVisible(false);
	    		
	    		lblQuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quote")+": ");
	    		lblQuote.setBounds(29, 407, 217, 14);
	    		getContentPane().add(lblQuote);
	    		lblQuote.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    		
	    		lblMinimumBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+": "+minBet+"€");
	    		lblMinimumBet.setBounds(29, 455, 140, 14);
	    		getContentPane().add(lblMinimumBet);
	    		lblMinimumBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    		
	    		lblEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event")+": ");
	    		lblEvent.setBounds(29, 359, 306, 12);
	    		getContentPane().add(lblEvent);
	    		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 14));
	    		
	    		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
	    		btnBet.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				Vector<Vector> vector= tableModelMyQuotes.getDataVector();
	    				Vector<Quote> myQuotes= new Vector<Quote>();
	    				for(Vector v: vector) {
	    					myQuotes.add((Quote)v.get(3));
	    				}
	    	    		double betMon= (int)spinnerBet.getValue();
	    	    		if(betMon>facade.getUserMoney(user)) {
	    	    			lblErrors.setVisible(true);
	    	    			lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("NotEnouhgMoney")+"!");
	    	    		}
	    	    		else {
	    	    			if(betMon<minBet) {
	    	    				lblErrors.setVisible(true);
	    	    				lblErrors.setText(ResourceBundle.getBundle("Etiquetas").getString("HigherBet")+"!");
	    	    			}
	    	    			else {
	    	    				facade.multipleQuoteBet(user, betMon, myQuotes);
	    	    				PurchaseGUI purchaseGUI= new PurchaseGUI(user);
	    	    				purchaseGUI.setVisible(true);
	    	    				close_actionPerformed(e);
	    	    			}
	    	    		}
	    			}
	    			
	    		});
	    		btnBet.setFont(new Font("Tahoma", Font.BOLD, 16));
	    		btnBet.setBounds(576, 363, 140, 70);
	    		btnBet.setEnabled(false);
	    		getContentPane().add(btnBet);
	    		
	    		btnRemoveQuote = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveQuote"));
	    		btnRemoveQuote.setFont(new Font("Tahoma", Font.BOLD, 11));
	    		btnRemoveQuote.setBounds(371, 441, 138, 23);
	    		btnRemoveQuote.setEnabled(false);
	    		getContentPane().add(btnRemoveQuote);
	    		btnRemoveQuote = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveQuote"));
	    		btnRemoveQuote.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				int kuota=tableQuotes.getSelectedRow();
	    				domain.Quote quote=(domain.Quote)tableModelQuotes.getValueAt(kuota,3);
	    				Vector<Vector> vector= tableModelMyQuotes.getDataVector();
	    				lblError.setVisible(false);
	    				tableModelMyQuotes.removeRow(kuota);	 			
	    				minBet-=quote.getQuestion().getBetMinimum();
	    				mult=mult/quote.getQuoteMultiplier();
	    				spinnerBet.setValue(minBet);
	    				lblTotalMultiplier.setText(ResourceBundle.getBundle("Etiquetas").getString("TotalMultiplier")+": x"+mult);
	    				lblFinalMinimumBet.setText(ResourceBundle.getBundle("Etiquetas").getString("FinalMinimumBet")+": "+minBet+"€");	    				
	    				if(vector.size()>1) {
	    						btnBet.setEnabled(true);
	    						spinnerBet.setEnabled(true);
	    				} else {
	    					btnBet.setEnabled(false);
    						spinnerBet.setEnabled(false);
	    				}
	    			}
	    		});
	    		
	    		
	    		lblQuotes2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quote"));
	    		lblQuotes2.setBounds(386, 228, 46, 14);
	    		getContentPane().add(lblQuotes2);
	    		
	    		lblBetQuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetQuote")); //$NON-NLS-1$ //$NON-NLS-2$
	    		lblBetQuote.setBounds(29, 228, 122, 14);
	    		getContentPane().add(lblBetQuote);
	    		
	    		lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlreadyContainsQuote")); //$NON-NLS-1$ //$NON-NLS-2$
	    		lblError.setForeground(Color.RED);
	    		lblError.setBounds(29, 331, 286, 14);
	    		lblError.setVisible(false);
	    		getContentPane().add(lblError);
	    		
	    		lblTotalMultiplier = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TotalMultiplier")+": x"+mult); //$NON-NLS-1$ //$NON-NLS-2$
	    		lblTotalMultiplier.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    		lblTotalMultiplier.setBounds(184, 432, 142, 14);
	    		getContentPane().add(lblTotalMultiplier);
	    		
	    		lblFinalMinimumBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FinalMinimumBet")+": "+minBet+"€"); //$NON-NLS-1$ //$NON-NLS-2$
	    		lblFinalMinimumBet.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    		lblFinalMinimumBet.setBounds(184, 455, 142, 14);
	    		getContentPane().add(lblFinalMinimumBet);
		
	    		btnGoBack.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				UserGUI userGUI= new UserGUI(user);
	    				userGUI.setVisible(true);
	    				close_actionPerformed(e);
			}
		});
		
	}
	public static boolean alreadyExistQuote(Vector<Vector> vector, Quote q) {
		for(Vector v: vector) {
			if(((Quote)v.get(3)).getQuestion().getQuestionNumber()==q.getQuestion().getQuestionNumber()) {
				return true;
			}
		}
		return false;
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