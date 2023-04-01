package businessLogic;

import java.util.Vector;
import java.util.Date;





//import domain.Booking;
import domain.Question;
import domain.Quote;
import domain.User;
import domain.RegisteredUser;
import domain.Event;
import exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	/**
	 * This method creates an event on a date, with an event text and the minimum bet
	 * 
	 * @param event text of the event
	 * @param date to when event is added
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Event createEvent(String description, Date eventDate) throws DateExpired, EventAlreadyExist;
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	
	/**
	 * This method retrieves whether the introduced user name exists in the database and if it is
	 * whether if the received password is correct
	 * 
	 * @param username of the user we are searching for
	 * @param password of the user we are searching for
	 * @return true if the introduced user is in the database and has the same password, false otherwise
	 */
	@WebMethod public boolean isLogin(String username, String password);
	
	/**
	 * This method retrieves whether the introduced is an administrator or not
	 * 
	 * @param username the name of the user we are searching for
	 * @return false if it does not exist an administrator with the introduced name
	 */
	@WebMethod public boolean isAdmin(String username);
	
	/**
	 * This method retrieves whether the introduced user name is in the database or not
	 * 
	 * @param username of the user we are searching for
	 * @return true there is an administrator in the database with the received name, false otherwise
	 */
	@WebMethod public boolean isRegistered(String username);
	
	/**
	 * This method registers the introduced user into the database, as a regular user
	 * 
	 * @param user, the user to be registered
	 */
	@WebMethod public void registUser(RegisteredUser us);
	
	/**
	 * This method changes the quote of the specified question
	 * 
	 * @param ev, the event of the questions quote we want to change
	 * @param question, the question the quote we want to change
	 * @param quote, the new quote of the specified question
	 */
	@WebMethod public void createQuote(Event ev, Question question, String quoteName, Float multiplier);

	/**
	 * This method retrieves whether the introduced user name is in the database or not
	 * 
	 * @param mail of the user we are searching for
	 * @return true there is an administrator in the database with the received name, false otherwise
	 */
	@WebMethod boolean emailRegistered(String mail);
	@WebMethod public void depositMoney(String user, double money);
	 @WebMethod public double getUserMoney(String user);

	
}
