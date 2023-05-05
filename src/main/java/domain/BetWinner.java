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
public class BetWinner extends Transaction implements Serializable{
	@XmlIDREF
	@OneToOne//(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Bet myBet;
	
	public BetWinner() {
		super();
	}
	
	public BetWinner(Bet myBet) {
		super(myBet.getMoney(), myBet.getUser());
		this.myBet=myBet;
	}
	
	public double betReward() {
		return super.getMoney()*myBet.getBetQuote().getQuoteMultiplier();
	}
	
	public Bet getMyBet() {
		return myBet;
	}

	public void setMyBet(Bet myBet) {
		this.myBet = myBet;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney")+":"+String.format("%.2f", betReward())+"€";
	}
}
