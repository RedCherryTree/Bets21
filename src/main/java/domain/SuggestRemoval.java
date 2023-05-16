package domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class SuggestRemoval extends Ticket {
	private Event event;
	
	public SuggestRemoval() {
		super();
	}

	public SuggestRemoval(String description, RegisteredUser user, Event event) {
		super(description, user);
		this.event=event;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
