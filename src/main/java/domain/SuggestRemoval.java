package domain;

import java.util.Date;

public class SuggestRemoval extends Tiquet {
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
