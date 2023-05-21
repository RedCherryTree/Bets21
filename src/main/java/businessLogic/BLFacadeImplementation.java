package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Quote;
import domain.User;
import domain.RegisteredUser;
import domain.Ticket;
import domain.Transaction;
import domain.Event;
import domain.Message;
import exceptions.*;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		    dbManager.initializeDB();
		    } else
		     dbManager=new DataAccess();
		dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   };
   
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event text of the event
	 * @param date to when event is added
	 * @return the created event, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
 @WebMethod
 public Event createEvent(String description, Date eventDate) throws DateExpired, EventAlreadyExist{
	   
		dbManager.open(false);
		Event ev=null; 
			    
		if(new Date().compareTo(eventDate)>0) 
			throw new DateExpired(ResourceBundle.getBundle("Etiquetas").getString("DateExpired"));
				
		
		 ev=dbManager.createEvent(description, eventDate);		

		dbManager.close();
		
		return ev;
 };
  
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);
		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
    /**
	 * This method retrieves whether the introduced user name exists in the database and if it is
	 * whether if the received password is correct
	 * 
	 * @param username of the user we are searching for
	 * @param password of the user we are searching for
	 * @return true if the introduced user is in the database and has the same password, false otherwise
	 */
    public boolean isLogin(String username, String password) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.isLogin(username, password);
    	dbManager.close();
    	return emaitza;
    }
    
    /**
	 * This method retrieves whether the introduced is an administrator or not
	 * 
	 * @param username the name of the user we are searching for
	 * @return false if it does not exist an administrator with the introduced name
	 */
    public boolean isAdmin(String username) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.isAdmin(username);
    	dbManager.close();
    	return emaitza;
    }
    
    /**
	 * This method retrieves whether the introduced user name is in the database or not
	 * 
	 * @param username of the user we are searching for
	 * @return true there is an administrator in the database with the received name, false otherwise
	 */
    public boolean isRegistered(String username) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.isRegistered(username);
    	dbManager.close();
    	return emaitza;
    }
    public boolean emailRegistered(String mail) {
    	dbManager.open(false);
    	boolean emaitza = dbManager.emailRegistered(mail);
    	dbManager.close();
    	return emaitza;
    }
    
    /**
	 * This method registers the introduced user into the database, as a regular user
	 * 
	 * @param user, the user to be registered
	 */
    public void registUser(RegisteredUser us) {
    	dbManager.open(false);
    	dbManager.registerUser(us);
    	dbManager.close();
    }
    
    /**
	 * This method changes the quote of the specified question
	 * 
	 * @param ev, the event of the questions quote we want to change
	 * @param question, the question the quote we want to change
	 * @param quote, the new quote of the specified question
	 */
    @WebMethod public Quote createQuote(int question, String quoteName, Float multiplier) {
    	dbManager.open(false);
    	Quote quote=dbManager.createQuote(question, quoteName, multiplier);
    	dbManager.close();
    	return quote;
    }
    
	/**
	 * This method deposits money on an user account
	 * 
	 * @param user, user that the money will be added
	 * @param money, quantity of money
	 * @param paymentOpton, payment condition
	 * @param paymentMethod, method of the payment
	 */
    @WebMethod public void depositMoney(String user, double money, String paymentOpton, String paymentMethod) {
    	dbManager.open(false);
    	dbManager.depositMoney(user, money, paymentOpton, paymentMethod);
    	dbManager.close();
    }
    
	/**
	 * This method returns the quantity of money of an user account
	 * 
	 * @param user, username of the user we are searching for
	 * @return float of the money the user have
	 */
    @WebMethod public double getUserMoney(String user) {
    	dbManager.open(false);
    	double money=dbManager.getUserMoney(user);
    	dbManager.close();
    	return money;
    }
    
    /**
  	 * This method retrieves the transactios that a user did
  	 * 
  	 * @param username of the user we are searching for
  	 * @return Vector of "Transaction"-s that did the user
  	 */
    @WebMethod public Vector<Transaction> getUserTransactions(String user){
    	dbManager.open(false);
    	Vector<Transaction> myTransactions=dbManager.getUserTransactions(user);
    	dbManager.close();
    	return myTransactions;
    }
    
    /**
  	 * This method will bet in a quote for an user with a quantity of money
  	 * 
  	 * @param user, username of the user that will bet
  	 * @param money, quantity of money that will be bet
 	 * @param quoteNumber, number of the quote
  	 */
    @WebMethod public void bet(String user, double money,int quoteNumber){
    	dbManager.open(false);
    	dbManager.bet(user, money, quoteNumber);
    	dbManager.close();
    }
    
    /**
     * Places a bet on multiple quotes for a user.
     * 
     * @param user   the username of the user placing the bet
     * @param money  the amount of money being bet
     * @param quotes the vector of quotes to bet on
     */
    @WebMethod 
    public void multipleQuoteBet(String user, double money, Vector<Quote> quotes) {
    	dbManager.open(false);
    	dbManager.multipleQuoteBet(user, money, quotes);
    	dbManager.close();
    }
    
    /**
     * Deletes an event with the given event number and event date.
     * 
     * @param eventnumber the number of the event to be deleted
     * @param eventDate   the date of the event to be deleted
     * @return the deleted event
     * @throws DateExpired    if the event date has already passed
     * @throws EventDontExist if the event does not exist
     */
    @WebMethod
    public Event deleteEvent(Integer eventnumber, Date eventDate) throws DateExpired, EventDontExist{
   	   
   		dbManager.open(false);
   		Event ev=null; 
   			    
   		if(new Date().compareTo(eventDate)>0) 
   			throw new DateExpired(ResourceBundle.getBundle("Etiquetas").getString("DateExpired"));
   				
   		
   		 ev=dbManager.deleteEvent( eventnumber, eventDate,"Event has been canceled.");		

   		dbManager.close();
   		
   		return ev;
    };
    
    /**
     * Selects a winner for a given question number and quote number.
     * 
     * @param questionNumber the number of the question
     * @param quoteNumber    the number of the quote
     */
    @WebMethod
    public void selectWinner(Integer questionNumber, Integer quoteNumber) {
   	   
   		dbManager.open(false);

   		dbManager.selectWinner( questionNumber, quoteNumber);		
	
   		dbManager.close();
    };

    
    /**
     * Sends a message from the sender to the receiver with the specified subject and text.
     * 
     * @param sen     the username of the sender
     * @param rec     the username of the receiver
     * @param subject the subject of the message
     * @param text    the content of the message
     * @return the sent message
     */
    @WebMethod 
    public Message sendMessage(String sen, String rec, String subject, String text) {
   		dbManager.open(false);

   		Message message=dbManager.sendMessage(sen, rec, subject, text);	
	
   		dbManager.close();
   		return message;
    }
    
    /**
     * Retrieves a user with the given username.
     * 
     * @param username the username of the user to retrieve
     * @return the retrieved user
     */
    @WebMethod
	public User getUser(String username) {
    	dbManager.open(false);
    	User user = dbManager.getUser(username);
    	dbManager.close();
    	return user;
    }
    
    /**
     * Retrieves the sent messages for a user with the given username.
     * 
     * @param username the username of the user
     * @return the vector of sent messages
     */
    @WebMethod 
    public Vector<Message> getUserSentMessages(String username){
    	dbManager.open(false);
    	Vector<Message> messages = dbManager.getUserSentMessages(username);
    	dbManager.close();
    	return messages;
    }
    
    /**
     * Retrieves the received messages for a user with the given username.
     * 
     * @param username the username of the user
     * @return the vector of received messages
     */
    @WebMethod 
    public Vector<Message> getUserReceivedMessages(String username){
    	dbManager.open(false);
    	Vector<Message> messages = dbManager.getUserReceivedMessages(username);
    	dbManager.close();
    	return messages;
    }
    

    /**
     * Follows a user with the given username.
     * 
     * @param username the username of the user following
     * @param followus the username of the user being followed
     * @return true if the follow operation is successful, false otherwise
     */
	@Override
	@WebMethod
	public boolean followUser(String username, String followus) {
		dbManager.open(false);
		boolean emaitza =dbManager.followUser(username,followus);
		dbManager.close();
		return emaitza;
	}
	
	/**
	 * Unfollows a user with the given username.
	 * 
	 * @param username  the username of the user unfollowing
	 * @param followus  the username of the user being unfollowed
	 * @return true if the unfollow operation is successful, false otherwise
	 */
	@Override
	public boolean unfollowUser(String username, int followus) {
		dbManager.open(false);
		boolean emaitza =dbManager.unfollowUser(username,followus);
		dbManager.close();
		return emaitza;
	}

	/**
	 * Retrieves the users followed by a user with the given username.
	 * 
	 * @param username the username of the user
	 * @return the vector of followed users
	 */
	@Override
	public Vector<RegisteredUser> getFollows(String username) {
		dbManager.open(false);
		Vector<RegisteredUser> user = dbManager.getFollows(username);
		dbManager.close();
		return user;
	}
	
	/**
	 * Opens a ticket with the given description and username.
	 * 
	 * @param description the description of the ticket
	 * @param username    the username of the user opening the ticket
	 */
	@WebMethod
	public void openTicket(String description, String username) {
		dbManager.open(false);
		dbManager.openTicket(description, username);
		dbManager.close();
	}
	
	/**
	 * Opens a ticket with the given description, username, event description, and event date.
	 * 
	 * @param description      the description of the ticket
	 * @param username         the username of the user opening the ticket
	 * @param eventDescription the description of the associated event
	 * @param eventDate        the date of the associated event
	 */
	@WebMethod
	public void openTicket(String description, String username, String eventDescription, Date eventDate) {
		dbManager.open(false);
		dbManager.openTicket(description, username, eventDescription, eventDate);
		dbManager.close();
	}
	
	/**
	 * Opens a ticket with the given description, username, and event.
	 * 
	 * @param description the description of the ticket
	 * @param username    the username of the user opening the ticket
	 * @param event       the associated event
	 */
	@WebMethod
	public void openTicket(String description, String username, Event event) {
		dbManager.open(false);
		dbManager.openTicket(description, username, event);
		dbManager.close();
	}
	
	/**
	 * Retrieves new tickets.
	 * 
	 * @return the vector of new tickets
	 */
	@WebMethod
	public Vector<Ticket> getNewTickets() {
		dbManager.open(false);
		Vector<Ticket> tickets=(Vector<Ticket>) dbManager.getNewTickets();
		dbManager.close();
		return tickets;
	}
	
	/**
	 * Manages a ticket from the new tickets with the given ticket number and admin name.
	 * 
	 * @param ticketNumber the number of the ticket to manage
	 * @param adminName    the name of the admin managing the ticket
	 */
	@WebMethod
	public void manageTicket(int ticketNumber,String adminName) {
		dbManager.open(false);
		dbManager.manageTicket(ticketNumber, adminName);
		dbManager.close();
	}
	
	/**
	 * Retrieves the tickets being managed by an admin with the given name.
	 * 
	 * @param adminName the name of the admin
	 * @return the vector of managed tickets
	 */
	@WebMethod
	public Vector<Ticket> getManagingTickets(String adminName){
		dbManager.open(false);
		Vector<Ticket> tickets=dbManager.getManagingTickets(adminName);
		dbManager.close();
		return tickets;
	}
	
	/**
	 * Sets a ticket with the given ticket number as concluded by an admin with the given name.
	 * 
	 * @param ticketNumber the number of the ticket to set as concluded
	 * @param adminName    the name of the admin setting the ticket as concluded
	 */
	@WebMethod public void setTicketConcluded(int ticketNumber, String adminName) {
		dbManager.open(false);
		dbManager.setTicketConcluded(ticketNumber, adminName);
		dbManager.close();
	}
}

