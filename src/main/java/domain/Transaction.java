package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Transaction implements Serializable{

	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer transactionNumber;
//	private String transactionType;//Bet(Bet), betResult(returnMoney), refundMoney(returnMoney), (Deposit)Deposit
	private double money;
	
	
	@XmlIDREF
	private RegisteredUser user; 
	
	public Transaction() {
		super();
	}
	/**
	 * Deposit money transaction builder
	 * 
	 * @param transactionType
	 * @param paymentOpton
	 * @param paymentMethod
	 * @param money
	 * @param user
	 */
	public Transaction(double money, RegisteredUser user) {
		super();
		this.money=money;
		this.user=user;
	}

	public Integer getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
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
	@Override
	public String toString() {
		return "Transaction [transactionNumber=" + transactionNumber + ", money=" + money + ", user=" + user + ",";
	}
	
}
