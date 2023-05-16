package domain;

import java.util.Date;

public class SuggestEvent extends Tiquet {
	private String eventDescription;
	private Date eventDate;
	
	public SuggestEvent() {
		super();
	}

	public SuggestEvent(String description, RegisteredUser user, String eventDescription, Date eventDate) {
		super(description, user);
		this.eventDescription=eventDescription;
		this.eventDate=eventDate;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
}
