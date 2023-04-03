package businessLogic;
//hola
import java.util.Date;
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
import domain.Transaction;
import domain.Event;
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
    @WebMethod public void createQuote(Event ev, Question question, String quoteName, Float multiplier) {
    	dbManager.open(false);
    	dbManager.createQuote(ev, question, quoteName, multiplier);
    	dbManager.close();
    }
    @WebMethod public void depositMoney(String user, double money, String paymentOpton, String paymentMethod) {
    	dbManager.open(false);
    	dbManager.depositMoney(user, money, paymentOpton, paymentMethod);
    	dbManager.close();
    }
    
    @WebMethod public double getUserMoney(String user) {
    	dbManager.open(false);
    	double money=dbManager.getUserMoney(user);
    	dbManager.close();
    	return money;
    }
    @WebMethod public Vector<Transaction> getUserTransactions(String user){
    	dbManager.open(false);
    	Vector<Transaction> myTransactions=dbManager.getUserTransactions(user);
    	dbManager.close();
    	return myTransactions;
    }
    @WebMethod public void bet(String user, double money,Question question, int selectedResult){
    	dbManager.open(false);
    	dbManager.bet(user, money, question, selectedResult);
    	dbManager.close();
    }
}

