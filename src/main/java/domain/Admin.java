package domain;

import java.util.Vector;

import javax.persistence.Entity;
@Entity
public class Admin extends User {
	private Vector<Ticket> assignedTickets= new Vector<Ticket>();
	
	public Admin(String username,String password) {
		super(username, password);
	}

	public Vector<Ticket> getAssignedTickets() {
		return assignedTickets;
	}

	public void setAssignedTickets(Vector<Ticket> assignedTickets) {
		this.assignedTickets = assignedTickets;
	}
	
	
}
