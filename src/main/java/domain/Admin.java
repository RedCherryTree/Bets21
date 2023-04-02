package domain;

import javax.persistence.Entity;
//A
@Entity
public class Admin extends User {
	
	public Admin(String username,String password) {
		super(username, password);
	}
}
