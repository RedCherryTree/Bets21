package exceptions;

public class EventDontExist extends Exception{
	 private static final long serialVersionUID = 1L;
	 
	 public EventDontExist()
	  { 
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public EventDontExist(String s)
	  {
	    super(s);
	  }
}