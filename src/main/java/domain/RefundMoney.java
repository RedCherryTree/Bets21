package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class RefundMoney extends Transaction implements Serializable{

	private String reasonToRefund;
	private String betDesc;
	private String betQuestion;
	private String quoteName;
	
	public RefundMoney() {
		super();
	}
	
	public RefundMoney(double money, RegisteredUser user, String reasonToRefund, String betDesc, String betQuestion, String quoteName) {
		super(money, user);
		this.reasonToRefund=reasonToRefund;
		this.betDesc=betDesc;
		this.betQuestion=betQuestion;
		this.quoteName=quoteName;
	}

	public String getReasonToRefund() {
		return reasonToRefund;
	}

	public void setReasonToRefund(String reasonToRefund) {
		this.reasonToRefund = reasonToRefund;
	}
	
	public String getBetDesc() {
		return betDesc;
	}

	public void setBetDesc(String betDesc) {
		this.betDesc = betDesc;
	}

	public String getBetQuestion() {
		return betQuestion;
	}

	public void setBetQuestion(String betQuestion) {
		this.betQuestion = betQuestion;
	}

	
	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("RefundedMoney")+":"+String.format("%.2f", super.getMoney())
		+"€"+" Your money has been refund. The reason is: "+reasonToRefund;
	}
}
