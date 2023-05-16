package dataAccess;

//hello
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import domain.Event;
import domain.Message;
import domain.MultipleQuoteBet;
import domain.Question;
import domain.Quote;
import domain.RegisteredUser;
import domain.SuggestEvent;
import domain.SuggestRemoval;
import domain.Ticket;
import domain.Transaction;
import domain.User;
import exceptions.EventAlreadyExist;
import exceptions.EventDontExist;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		
	}

	public DataAccess()  {	
		 this(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				
			}
			Admin admin= new Admin("admin", "123");
			RegisteredUser user= new RegisteredUser("user","123");
			db.persist(admin);
			db.persist(user);
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 
	
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			//db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return q;
		
	}
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}
	

	/**
	 * Method to open the entity manager database
	 * 
	 * @param initializeMode boolean parameter
	 */
	public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	/**
	 * Method that returns whether the introduced questions is in the database or not
	 * 
	 * @param event, the event of the introduced question
	 * @param question, the question we are looking for
	 * @return true if the question exists in the database, false otherwise
	 */
    public boolean existQuestion(Event event, String question) {
    	System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
    	Event ev = db.find(Event.class, event.getEventNumber());
    	return ev.DoesQuestionExists(question);
    }
    
    /**
	 * Method to close the entity manager database
	 * 
	 */
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	/**
	 * This method retrieves whether the introduced is an administrator or not
	 * 
	 * @param username the name of the user we are searching for
	 * @return false if it does not exist an administrator with the introduced name
	 */
	public boolean isLogin(String username, String password) {
		boolean emaitza=this.isRegistered(username);
		if(emaitza) {
			User us = db.find(User.class, username);
			emaitza = us.getPassword().equals(password);
		}
				
		return emaitza;
		
	}
	
	/**
	 * This method retrieves whether the introduced is an administrator or not
	 * 
	 * @param username the name of the user we are searching for
	 * @return false if it does not exist an administrator with the introduced name
	 */
	public boolean isAdmin(String username) {
		User us=db.find(Admin.class, username);
		if(us==null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * This method retrieves whether the introduced user name is in the database or not
	 * 
	 * @param username of the user we are searching for
	 * @return true there is an administrator in the database with the received name, false otherwise
	 */
	public boolean isRegistered(String username) {
		User us=db.find(User.class, username);
		if(us == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method retrieves whether the introduced user name is in the database or not
	 * 
	 * @param mail of the user we are searching for
	 * @return true there is an administrator in the database with the received name, false otherwise
	 */
	public boolean emailRegistered(String mail) {
		User us=db.find(User.class, mail);
		if(us == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method registers the introduced user into the database, as a regular user
	 * 
	 * @param user, the user to be registered
	 */
	public void registerUser(RegisteredUser us) {
		db.getTransaction().begin();
//		us.setUserType("registeredUser");
		db.persist(us);
		db.getTransaction().commit();
	}

	/**
	 * Method to create and persist a new event into the database
	 * 
	 * @param description, information of the event
	 * @param eventDate, date of the event
	 * @return the instance of the created event
	 * @throws EventAlreadyExist if the event already has been introduced into the database
	 */
	public Event createEvent(String description, Date eventDate) throws EventAlreadyExist{
		db.getTransaction().begin();
		Event ev =new Event(description, eventDate);
		if(eventExist(eventDate, description)) {
			throw new EventAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAlreadyExist"));
		}else {
			db.persist(ev);
		}
		db.getTransaction().commit();
		return ev;
	}
	
	/**
	 * Method that returns whether the introduced description event already exists in the database
	 * 
	 * @param eventDate, the date of the event we are looking for
	 * @param description, the description of the event we are looking for
	 * @return true if there already is an event with the introduced description, false otherwise
	 */
	public boolean eventExist(Date eventDate, String description) {
		for (Event ev:this.getEvents(eventDate)){
			if (ev.getDescription().compareTo(description)==0)
				return true;
		}
		return false;
	}

	/**
	 * This method changes the quote of the specified question
	 * 
	 * @param ev, the event of the questions quote we want to change
	 * @param question, the question the quote we want to change
	 * @param quote, the new quote of the specified question
	 */
	public Quote createQuote(int question, String quoteName, Float multiplier) {
		db.getTransaction().begin();
		Question questionDb=db.find(Question.class, question);
		Quote q =questionDb.addQuote(quoteName, multiplier);
		db.persist(q);

		db.getTransaction().commit();
		return q;
	}
	public Quote changeQuote(Event ev, Question question, String quoteName, Float multiplier) {
		db.getTransaction().begin();
		Question questionDb=db.find(Question.class, question.getQuestionNumber());
		Quote q =questionDb.addQuote(quoteName, multiplier);
		db.persist(q);

		db.getTransaction().commit();
		return q;
	}
	
    public void depositMoney(String user, double money, String paymentOpton, String paymentMethod) {
   	db.getTransaction().begin();
   	RegisteredUser us= db.find(RegisteredUser.class, user);
   	Transaction transaction=us.depositMoney(money, paymentOpton, paymentMethod);
   	db.persist(transaction);
   	db.getTransaction().commit();
   }
     public double getUserMoney(String user) {
     	RegisteredUser us= db.find(RegisteredUser.class, user);
     	return us.getMoney();
     }
     public Vector<Transaction> getUserTransactions(String user){
    	RegisteredUser us= db.find(RegisteredUser.class, user);
    	for(Transaction t:us.getMyTransactions()) {
    		System.out.println(t.toString());
    	}
     	return us.getMyTransactions();
     }

 	public void bet(String user, double money, int quoteNumber) {
		db.getTransaction().begin();
		RegisteredUser us= db.find(RegisteredUser.class, user);
		Quote quote= db.find(Quote.class, quoteNumber);
		Transaction transaction= us.bet(money, quote);
		db.persist(transaction);
		db.getTransaction().commit();
		
	}
 	
 	public void multipleQuoteBet(String user, double money, Vector<Quote> quotes) {
		db.getTransaction().begin();
		RegisteredUser us= db.find(RegisteredUser.class, user);
		Transaction transaction= us.bet(money, quotes);
		db.persist(transaction);
		db.getTransaction().commit();	
	}
 	
 	public void deleteTransactions() {
 		db.getTransaction().begin();
 		Query query1 = db.createQuery("DELETE FROM Transaction t"); 
 		query1.executeUpdate();
 		TypedQuery<RegisteredUser> query = db.createQuery("SELECT u FROM RegisteredUser u",RegisteredUser.class);
 		List<RegisteredUser> users= query.getResultList();
 		for(RegisteredUser us: users) {
 			us.getMyTransactions().clear();
 			us.setMoney(0);
 		}
 		db.getTransaction().commit();
 	}
 	
	public Event deleteEvent(Integer eventNumber, Date eventDate, String reasonToRefund)throws EventDontExist{
		db.getTransaction().begin();
		Event ev = db.find(Event.class, eventNumber);
		if(ev==null) {
			throw new EventDontExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAlreadyExist"));
		}
		else {
			
			TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.betQuote.question.event.eventNumber=="+eventNumber+"",Bet.class);
			List<Bet> willBeRefunded= query.getResultList();
			System.out.println("This bets will be refunded: ");
			for(Bet bet: willBeRefunded) {
				System.out.println(bet.toString());
				RegisteredUser us= bet.getUser();
			   	Transaction transaction=us.refundMoney(bet, reasonToRefund);
			   	us.getMyTransactions().remove(bet);
			   	db.persist(transaction);
			}
			Query query1 = db.createQuery("DELETE FROM Quote quote WHERE quote.question.event.eventNumber=="+eventNumber+""); 
			Query query2 = db.createQuery("DELETE FROM Question question WHERE question.event.eventNumber=="+eventNumber+"");
			Query query3 = db.createQuery("DELETE FROM Event event WHERE event.eventNumber=="+eventNumber+""); 
			query1.executeUpdate();
			query2.executeUpdate();
			query3.executeUpdate();
		}
		
		db.getTransaction().commit();
		return ev;
	}
	
	public void selectWinner(Integer questionNumber, Integer quoteNumber) {
		db.getTransaction().begin();
		Question q = db.find(Question.class, questionNumber);
		Quote qt = db.find(Quote.class, quoteNumber);
		
		if( q.isHasFinished()==false) {
			for (Quote kuota:q.getQuotes()) {
				if (kuota!=qt) {
					qt.setWinner(false);	
				}else {
					qt.setWinner(true);	
				}
			}
			q.setHasFinished(true);
			q.setResult(qt.getQuoteName());
			TypedQuery<MultipleQuoteBet> query1 = db.createQuery("SELECT mqb FROM MultipleQuoteBet mqb WHERE mqb.hasEnded=="+false+"",MultipleQuoteBet.class);		
			List<MultipleQuoteBet> mqbWinners= query1.getResultList();
			for(MultipleQuoteBet mqb: mqbWinners) {
				if(mqb.hasBeenDecided() && mqb.hasWon()) {
					RegisteredUser us= mqb.getUser();
					Transaction transaction=us.mqBetWinner(mqb);
					db.persist(transaction);
				}
			}
			
			double mul = qt.getQuoteMultiplier();	
			TypedQuery<Bet> query2 = db.createQuery("SELECT b FROM Bet b WHERE b.betQuote.quoteNumber=="+quoteNumber+"",Bet.class);		
			List<Bet> bWinners= query2.getResultList();
			System.out.println("Winners of the regular bet: ");
			for(Bet bet: bWinners) {
				System.out.println(bet.toString());
				RegisteredUser us= bet.getUser();
				Transaction transaction=us.betWinner(bet);
				db.persist(transaction);
			}	
		

		}else {
			System.out.println("The result of this question has already been decided");
		}	
	
		db.getTransaction().commit();
	}
	public void deleteUsers() {
 		db.getTransaction().begin();
 		Query query1 = db.createQuery("DELETE FROM User u"); 
 		query1.executeUpdate();
 		db.getTransaction().commit();
	}
	
	public Message sendMessage(String sen, String rec, String subject, String text) {
	   	db.getTransaction().begin();
	   	User sender= db.find(User.class, sen);
	   	User receiver= db.find(User.class, rec);
	   	sender.getReceivedMessages();
	   	sender.getSentMessages();
	   	receiver.getReceivedMessages();
	   	receiver.getSentMessages();
	   	Message message=sender.sendMessage(receiver, subject, text);
	   	receiver.receiveMessage(message);
	   	db.persist(message);
	   	db.getTransaction().commit();
	   	return message;
	}
	
	public void readMessage(int mes) {
		db.getTransaction().begin();
		Message message=db.find(Message.class, mes);
		message.setHasBeenRead(true);
		db.getTransaction().commit();
		
	}

   
	public User getUser(String username) {
     	User us= db.find(User.class, username);
     	return us;
    }
	
    public Vector<Message> getUserReceivedMessages(String username){
    	User us= db.find(User.class, username);
    	for(Message m: us.getReceivedMessages()) {
    		System.out.println(m.toString());
    	}
     	return us.getReceivedMessages();
    }
    
    public Vector<Message> getUserSentMessages(String username){
    	User us= db.find(User.class, username);
    	for(Message m: us.getSentMessages()) {
    		System.out.println(m.toString());
    	}
     	return us.getSentMessages();
    }
    public Vector<Message> getConversation(String receiver, String sender){
    	User us= db.find(User.class, receiver);
    	Vector<Message> myConversation=us.getSentMessages();
    	myConversation.addAll(us.getReceivedMessages());
    	Collections.sort(myConversation);
    	Vector<Message> emaitza= new Vector<Message>();
    	for(Message m: myConversation) {
    		if(!emaitza.contains(m) && m.getSender().equals(sender)) {
    			System.out.println(m.toString());
    			emaitza.add(m);
    		}
    	}
     	return emaitza;
    }
    
	public boolean followUser(String username, String followus) {
		if(this.isRegistered(followus)) {
			db.getTransaction().begin();
		 	RegisteredUser user= db.find(RegisteredUser.class, username);
		 	RegisteredUser follow= db.find(RegisteredUser.class, followus);
		 	user.followUser(follow);
		 	System.out.println(user.getUsername());
			db.getTransaction().commit();
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean unfollowUser(String username, int follownum) {
		
			db.getTransaction().begin();
		 	RegisteredUser user= db.find(RegisteredUser.class, username);
		 	
		 	user.unfollowUser(follownum);
			db.getTransaction().commit();
			return true;
		
		
	}
	
	
	public Vector<RegisteredUser> getFollows(String username){
		RegisteredUser user= db.find(RegisteredUser.class, username);
		for(RegisteredUser u: user.getMyFollows())
			System.out.println(u.toString());
		return user.getMyFollows();
	}
	
	public void openTicket(String description, String username) {
		db.getTransaction().begin();
	 	RegisteredUser user= db.find(RegisteredUser.class, username);
	 	Ticket ticket=user.openTicket(description);
	 	db.persist(ticket);
		db.getTransaction().commit();
	}
	
	public void openTicket(String description, String username, String eventDescription, Date eventDate) {
		db.getTransaction().begin();
	 	RegisteredUser user= db.find(RegisteredUser.class, username);
	 	SuggestEvent ticket=user.openTicket(description, eventDescription, eventDate);
	 	db.persist(ticket);
		db.getTransaction().commit();
	}
	
	public void openTicket(String description, String username, Event event) {
		db.getTransaction().begin();
	 	RegisteredUser user= db.find(RegisteredUser.class, username);
	 	SuggestRemoval ticket=user.openTicket(description, event);
	 	db.persist(ticket);
		db.getTransaction().commit();
	}
	
	public Vector<Ticket> getNewTickets() {
		TypedQuery<Ticket> query = db.createQuery("SELECT ticket FROM Ticket ticket WHERE ticket.egoera='"+Ticket.getEGOERA_BERRIA()+"'",Ticket.class);
		Vector<Ticket> tickts2= new Vector<Ticket>();
		List<Ticket> tickets= query.getResultList();
		for(Ticket t: tickets) {
			tickts2.add(t);
		}
		return tickts2;
	}
	
	public void manageTicket(int ticketNumber,String adminName) {
		db.getTransaction().begin();
		Ticket ticket=db.find(Ticket.class, ticketNumber);
		Admin admin=db.find(Admin.class, adminName);
		admin.getAssignedTickets().add(ticket);
		ticket.setEgoera(Ticket.getEGOERA_TRATATZEN());
		db.getTransaction().commit();
	}
	
	public Vector<Ticket> getManagingTickets(String adminName){
		db.getTransaction().begin();
		Admin admin=db.find(Admin.class, adminName);
		db.getTransaction().commit();
		return admin.getAssignedTickets();
	}
	
	public void setTicketConcluded(int ticketNumber, String adminName) {
		db.getTransaction().begin();
		Ticket ticket=db.find(Ticket.class, ticketNumber);
		Admin admin=db.find(Admin.class, adminName);
		admin.getAssignedTickets().remove(ticket);
		ticket.setEgoera(Ticket.getEGOERA_ITXITA());
		db.getTransaction().commit();
	}
}
