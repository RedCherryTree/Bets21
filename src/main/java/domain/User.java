package domain;

import java.io.*;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlSeeAlso ({RegisteredUser.class, Admin.class})
public abstract class User {
	@Id
	private String username;
	private String password;
	
	private Vector<Message> sentMessages=new Vector<Message>();
	private Vector<Message> receivedMessages=new Vector<Message>();
	
	public User(String username,String password) {
		this.username=username;
		this.password=password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	public boolean isAdmin() {
		return this.getClass().getSimpleName().equals("Admin");
	}

	public String getUserType() {
		return this.getClass().getSimpleName();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Vector<Message> getSentMessages() {
		return sentMessages;
	}

	public Vector<Message> getReceivedMessages() {
		return receivedMessages;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password ;
	}

	public Message sendMessage(User receiver, String subject, String text) {
		Message message= new Message(this, receiver, subject, text);
		sentMessages.add(0,message);
		return message;
	}
	
	public void receiveMessage(Message message) {
		this.receivedMessages.add(0,message);
	}
	
}
