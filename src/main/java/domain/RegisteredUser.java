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
	private double moneyWon;
	private double totalBetMoney;
	private double moneyDeposit;
	private int numberOfBetWon;
	private int totalNumberOfBets;
	
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
	

	public double getMoneyWon() {
		return moneyWon;
	}


	public void setMoneyWon(double moneyWon) {
		this.moneyWon = moneyWon;
	}


	public double getTotalBetMoney() {
		return totalBetMoney;
	}


	public void setTotalBetMoney(double totalBetMoney) {
		this.totalBetMoney = totalBetMoney;
	}


	public double getMoneyDeposit() {
		return moneyDeposit;
	}


	public void setMoneyDeposit(double moneyDeposit) {
		this.moneyDeposit = moneyDeposit;
	}


	public int getNumberOfBetWon() {
		return numberOfBetWon;
	}


	public void setNumberOfBetWon(int numberOfBetWon) {
		this.numberOfBetWon = numberOfBetWon;
	}


	public int getTotalNumberOfBets() {
		return totalNumberOfBets;
	}


	public void setTotalNumberOfBets(int totalNumberOfBets) {
		this.totalNumberOfBets = totalNumberOfBets;
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


	public double calculateWinPercentage() {
		return numberOfBetWon/totalNumberOfBets;
	}
	
	public double calculateWonMoneyPercentage() {
		return moneyWon/totalBetMoney;
	}

	public Transaction depositMoney(double money, String paymentOpton, String paymentMethod) {
		this.money+= money;
		this.moneyDeposit+=money;
		DepositMoney transaction= new DepositMoney( paymentOpton, paymentMethod, money, this);
		this.myTransactions.add(transaction);
		return transaction;
	}
	
	public Transaction bet(double money, Quote quote ) {
		this.money-= money;
		this.totalBetMoney+=money;
		this.totalNumberOfBets+=1;
		Bet bet= new Bet(money,this, quote);
		this.myTransactions.add(bet);
		return bet;
	}
	public Transaction refundMoney(Bet bet, String reasonToRefund) {
		this.money+= bet.getMoney();
		this.totalBetMoney-=bet.getMoney();
		this.totalNumberOfBets-=1;
		RefundMoney refund= new RefundMoney(bet.getMoney(), this, reasonToRefund, bet.getBetQuote().getQuestion().getEvent().getDescription(),
				bet.getBetQuote().getQuestion().getQuestion(), bet.getBetQuote().getQuoteName());
		this.myTransactions.add(refund);
		return refund;		
	}
	
	public Transaction betWinner(Bet bet){
		BetWinner winner= new BetWinner(bet);
		this.money+=winner.betReward();
		this.moneyWon+=winner.betReward();
		this.numberOfBetWon+=1;
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
