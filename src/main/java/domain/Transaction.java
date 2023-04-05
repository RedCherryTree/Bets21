package domain;

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
public class Transaction {

	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer transactionNumber;
	private String transactionType;//Bet, Deposit or betResult money
	private double money;
	
	//Si se inserta dinero
	private String paymentOption;
	private String paymentMethod;
	
	//Si apuestas
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Bet myBet;
	
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
	public Transaction(String transactionType,String paymentOpton, String paymentMethod, double money, RegisteredUser user) {
		super();
		this.transactionType=transactionType;
		this.money=money;
		this.user=user;
		this.paymentOption=paymentOpton;
		this.paymentMethod=paymentMethod;
	}
	public Transaction(String transactionType, double money, RegisteredUser user) {
		super();
		this.transactionType=transactionType;
		this.money=money;
		this.user=user;
	}

	public Transaction(double money, RegisteredUser user, Quote betQuote) {
		super();
		this.transactionType="Bet";
		this.money=money;
		this.user=user;
		this.myBet= new Bet(money, user, betQuote, this);
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
	
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Bet getMyBet() {
		return myBet;
	}
	public void setMyBet(Bet myBet) {
		this.myBet = myBet;
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
