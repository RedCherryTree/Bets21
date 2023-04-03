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
	private Question betQuestion;
	private RegisteredUser user;
	
	@XmlIDREF
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Transaction transaction;
	
	private double money;
	private int selectedResult;
	
	public Bet() {
		super();
	}
	
	public Bet(double money, int selectedResult, RegisteredUser user, Question betQuestion, Transaction transaction) {
		super();
		this.money=money;
		this.selectedResult=selectedResult;
		this.user=user;
		this.betQuestion=betQuestion;
	}

	public int getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}

	public Question getBetQuestion() {
		return betQuestion;
	}

	public void setBetQuestion(Question betQuestion) {
		this.betQuestion = betQuestion;
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

	public int getSelectedResult() {
		return selectedResult;
	}

	public void setSelectedResult(int selectedResult) {
		this.selectedResult = selectedResult;
	}
	
}
