package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class BetWinner extends ReturnMoney implements Serializable{

	public BetWinner() {
		super();
	}
	
	public BetWinner(Bet myBet) {
		super(myBet);

	}
	
	public double betReward() {
		return super.getMoney()*super.getMyBet().getBetQuote().getQuoteMultiplier();
	}
	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("EarnedMoney")+":"+String.format("%.2f", super.getMoney())+"€";
	}
}
