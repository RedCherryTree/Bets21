package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Transaction {

	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer transactionNumber;
	private String transactionType;//Bet, deposit, or bet result money
	private double money;
	@XmlIDREF
	private RegisteredUser user; 
	
	public Transaction() {
		super();
	}
	public Transaction(String transactionType, double money, RegisteredUser user) {
		super();
		this.transactionType=transactionType;
		this.money=money;
		this.user=user;
	}

	public Integer getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	public String toString(){
		String transaction=transactionNumber+";"+transactionType+";";
		if(transactionType.equals("Bet")) {
			transaction=transaction+"-"+money;
		}
		else {//transactionType.equals("Deposit") or transactionType.equals("BetWinner")
			transaction=transaction+"+"+money;
		}
		return transaction;
	}
}
