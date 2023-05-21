package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@Entity
public class Quote implements Serializable {
	//Winner of the match
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int quoteNumber;
	private String quoteName;
	private double quoteMultiplier;
	@XmlIDREF
	private Question question;
	private boolean isWinner;

	public int getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(int quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuoteQuestion(Question question) {
		this.question = question;
	}
	
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public String getQuoteName() {
		return quoteName;
	}
	
	public void setQuoteMultiplier(double quoteMultiplier) {
		this.quoteMultiplier = quoteMultiplier;
	}
	public double getQuoteMultiplier() {
		return quoteMultiplier;
	}
	
	public boolean isWinner() {
		return isWinner;
	}
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}
	
	public Quote() {
		super();
	}
	public Quote(int quoteNumber, Question question, String quoteName, Float quoteMultiplier) {
		super();
		this.question=question;
		this.quoteName=quoteName;
		this.quoteMultiplier=quoteMultiplier;
	}
	
	public Quote(Question question,  String quoteName, double d) {
		super();
		this.question=question;
		this.quoteName=quoteName;
		this.quoteMultiplier=d;
	}

	public String getQuotes() {
		return quoteName;
	}
	@Override
	public String toString() {
		return "Quote [quoteNumber=" + quoteNumber + ", quoteName=" + quoteName + ", quoteMultiplier=" + quoteMultiplier
				+ ", question=" + question + "]";
	}
	

	
}
