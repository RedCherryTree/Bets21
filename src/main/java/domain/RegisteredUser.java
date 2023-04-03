package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class RegisteredUser extends User implements Serializable{
	private double money;
	private String mail;
	private String DNI;
	private String birthday;
	private	int phoneNumber;
	private String firstName;
	private String lastName;
	private String country;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Transaction> myTransactions=new Vector<Transaction>();
	
	public RegisteredUser(String username,String password) {
		super(username, password);
	}
	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	
	public Vector<Transaction> getMyTransactions() {
		return myTransactions;
	}


	public Transaction addMoney(double money, String paymentOpton, String paymentMethod) {
		this.money+= money;
		Transaction transaction= new Transaction("Deposit", paymentOpton, paymentMethod,  money, this);
		this.myTransactions.add(transaction);
		return transaction;
	}
	
	public Transaction bet(double money, int selectedResult, Question question ) {
		this.money-= money;
		Transaction transaction= new Transaction("Bet", money, this);
		Bet bet= new Bet(money, selectedResult,this, question, transaction);
		transaction.setMyBet(bet);
		this.myTransactions.add(transaction);
		return transaction;
	}
}
