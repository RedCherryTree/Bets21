package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class MultipleQuoteBetWinner extends Transaction implements Serializable{
	@XmlIDREF
	@OneToOne//(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private MultipleQuoteBet myBet;
	
	public MultipleQuoteBetWinner() {
		super();
	}
	
	public MultipleQuoteBetWinner(MultipleQuoteBet myBet) {
		super(myBet.getMoney(), myBet.getUser());
		this.myBet=myBet;
	}
	
	public double betReward() {
		return myBet.calculatePrize();
	}
	
	public MultipleQuoteBet getMyBet() {
		return myBet;
	}

	public void setMyBet(MultipleQuoteBet myBet) {
		this.myBet = myBet;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney")+":"+String.format("%.2f", betReward())+"€";
	}
}
