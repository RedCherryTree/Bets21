package domain;

import javax.persistence.Entity;
//A
//B
@Entity
public class Admin extends User {
	
	public Admin(String username,String password) {
		super(username, password);
	}
}
