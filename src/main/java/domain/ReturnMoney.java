package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class ReturnMoney extends Transaction implements Serializable {
	@XmlIDREF
	@OneToOne//(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Bet myBet;
	
	
	public ReturnMoney() {
		super();
	}
	
	public ReturnMoney(Bet myBet) {
		super(myBet.getMoney(), myBet.getUser());
	}
	
}
