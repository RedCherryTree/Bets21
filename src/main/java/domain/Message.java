package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Message implements Comparable<Message>{
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int messageNumber;
	
	private User sender;
	private User receiver;
	
	private String subject;
	private String text;
	
	private boolean hasBeenRead;
	
	public Message() {
		super();
	}
	
	public Message(User sender, User receiver, String subject, String text) {
		this();
		hasBeenRead=false;
		this.sender=sender;
		this.receiver=receiver;
		this.subject=subject;
		this.text=text;
	}

	public int getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHasBeenRead() {
		return hasBeenRead;
	}

	public void setHasBeenRead(boolean hasBeenRead) {
		this.hasBeenRead = hasBeenRead;
	}

	@Override
	public int compareTo(Message o) {
		return this.messageNumber-o.getMessageNumber();
	}
	
}
