package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Bet {
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int betNumber;
	private Quote betQuote;
	private RegisteredUser user;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Transaction transaction;
	
	private double money;
	
	public Bet() {
		super();
	}
	
	public Bet(double money, RegisteredUser user,Quote betQuote, Transaction transaction) {
		super();
		this.money=money;
		this.user=user;
		this.betQuote=betQuote;
		this.transaction=transaction;
	}

	public int getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}



	public Quote getBetQuote() {
		return betQuote;
	}

	public void setBetQuote(Quote betQuote) {
		this.betQuote = betQuote;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Bet [betNumber=" + betNumber + ", betQuote=" + betQuote + ", user=" + user + ", transaction="
				+ transaction + ", money=" + money + "]";
	}


	
}
