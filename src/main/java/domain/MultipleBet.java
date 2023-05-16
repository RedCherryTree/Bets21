package domain;

import java.util.ArrayList;
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
public class MultipleBet extends Transaction{

	private ArrayList<Bet> mulBet;
	
	public MultipleBet() {
		super();
	}
	
	public MultipleBet(Bet b) {
		mulBet.add(b);
	}
	
	public Double getTotalQuote(){
		Double q=0.0;
		for (Bet b:mulBet) {
			q = q * b.getBetQuote().getQuoteMultiplier();
		}
		return q;
	}
	
	public Double getTotalMoney(){
		Double q=0.0;
		for (Bet b:mulBet) {
			q = q + b.getMoney();
		}
		return q;
	}
	
	@Override
	public String toString() {
		String em="";
		for (Bet b:mulBet) {
			em += ResourceBundle.getBundle("Etiquetas").getString("MoneyBet")+String.format("%.2f",  super.getMoney())+"€";
		}
	return em;
	}	
}


