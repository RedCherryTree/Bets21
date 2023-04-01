package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@Entity
public class Quote {
	//Winner of the match
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int quoteNumber;
	private String quoteName;
	private Float quoteMultiplier;
//	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Question question;
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
	
	public void setQuoteMultiplier(Float quoteMultiplier) {
		this.quoteMultiplier = quoteMultiplier;
	}
	public Float getQuoteMultiplier() {
		return quoteMultiplier;
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
	
	public Quote(Question question,  String quoteName, Float quoteMultiplier) {
		super();
		this.question=question;
		this.quoteName=quoteName;
		this.quoteMultiplier=quoteMultiplier;
	}

	public String getQuotes() {
		return quoteName;
	}
	

	
}
