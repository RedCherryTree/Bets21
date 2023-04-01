package exceptions;

public class DateExpired extends Exception {
 private static final long serialVersionUID = 1L;
	 public DateExpired()
	  {
	    super();
	  } 
	  /**This exception is triggered if the date has already expired
	  */
	  public DateExpired(String s)
	  {
	    super(s);
	  }
}
