package domain;

import java.io.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlSeeAlso ({RegisteredUser.class, Admin.class})
public abstract class User {
	@Id
	private String username;
	private String password;
	
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
//	public void setUserType(String userType) {
//		this.userType=userType;
//	}

	public String getUserType() {
		return this.getClass().getSimpleName();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
