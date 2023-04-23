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
public class Bet extends Transaction{
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int betNumber;
	private Question betQuestion;
	
	private int selectedResult;
	
	public Bet() {
		super();
	}
	
	public Bet(double money, int selectedResult, RegisteredUser user, Question betQuestion) {
		super(money, user);
		this.selectedResult=selectedResult;
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

	public int getSelectedResult() {
		return selectedResult;
	}

	public void setSelectedResult(int selectedResult) {
		this.selectedResult = selectedResult;
	}
	
	@Override
	public String toString() {
		return "Money bet= "+super.getMoney();
	}
}
