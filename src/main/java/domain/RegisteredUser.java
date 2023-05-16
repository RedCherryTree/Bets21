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
	
	private Vector<RegisteredUser> myFollows=new Vector<RegisteredUser>();
	private Vector<RegisteredUser> myFollowers=new Vector<RegisteredUser>();
	
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


	public Vector<RegisteredUser> getMyFollowers() {
		if(myFollowers==null)
			myFollowers= new Vector<RegisteredUser>();
		return myFollowers;
	}
	



	public Vector<RegisteredUser> getMyFollows() {
		if(myFollows==null)
			myFollows= new Vector<RegisteredUser>();
		return myFollows;
	}


	public void setMyFollows(Vector<RegisteredUser> myFollows) {
		this.myFollows = myFollows;
	}


	public void setMyTransactions(Vector<Transaction> myTransactions) {
		this.myTransactions = myTransactions;
	}


	public void setMyFollowers(Vector<RegisteredUser> myFollowers) {
		this.myFollowers = myFollowers;
	}



	public Transaction depositMoney(double money, String paymentOpton, String paymentMethod) {
		this.money+= money;
		DepositMoney transaction= new DepositMoney( paymentOpton, paymentMethod, money, this);
		this.myTransactions.add(transaction);
		return transaction;
	}
	
	public Transaction bet(double money, Quote quote ) {
		this.money-= money;
		Bet bet= new Bet(money,this, quote);
		this.myTransactions.add(bet);
		return bet;
	}
	
	public Transaction bet(double money, Vector<Quote> quotes ) {
		this.money-= money;
		MultipleQuoteBet bet= new MultipleQuoteBet(money,this, quotes);
		this.myTransactions.add(bet);
		return bet;
	}
	
	public Transaction refundMoney(Bet bet, String reasonToRefund) {
		this.money+= bet.getMoney();
		RefundMoney refund= new RefundMoney(bet.getMoney(), this, reasonToRefund, bet.getBetQuote().getQuestion().getEvent().getDescription(),
				bet.getBetQuote().getQuestion().getQuestion(), bet.getBetQuote().getQuoteName());
		this.myTransactions.add(refund);
		return refund;		
	}
	
	public Transaction betWinner(Bet bet){
		BetWinner winner= new BetWinner(bet);
		this.money+=winner.betReward();
		this.myTransactions.add(winner);
		return winner;	
	}
	
	public Transaction mqBetWinner(MultipleQuoteBet bet){
		MultipleQuoteBetWinner winner= new MultipleQuoteBetWinner(bet);
		this.money+=winner.betReward();
		this.myTransactions.add(winner);
		return winner;	
	}
	public boolean followUser(RegisteredUser user) {
		if (!this.myFollows.contains(user) ) {
			return this.myFollows.add(user);
		}
		else {
			return false;
		}
	}
	
	public boolean unfollowUser(int i) {
		return myFollows.remove(i) != null;
	}
	
	@Override
	public String toString() {
		return super.toString()+", Mail= "+this.mail+"]";
	}
}
