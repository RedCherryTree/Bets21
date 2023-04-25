package domain;

import java.util.ResourceBundle;

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

	private Quote betQuote;
	
	
	public Bet() {
		super();
	}
	
	public Bet(double money, RegisteredUser user, Quote betQuote) {
		super(money, user);
		this.betQuote=betQuote;
	}



	public Quote getBetQuote() {
		return betQuote;
	}

	public void setBetQuoten(Quote betQuote) {
		this.betQuote = betQuote;
	}


	
	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+String.format("%.2f",  super.getMoney())+"€";
	}
}
