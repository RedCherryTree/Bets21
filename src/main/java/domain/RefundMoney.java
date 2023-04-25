package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class RefundMoney extends ReturnMoney implements Serializable{

	private String reasonToRefund;
	public RefundMoney() {
		super();
	}
	
	public RefundMoney(Bet bet, String reasonToRefund) {
		super(bet);
		this.reasonToRefund=reasonToRefund;
	}

	public String getReasonToRefund() {
		return reasonToRefund;
	}

	public void setReasonToRefund(String reasonToRefund) {
		this.reasonToRefund = reasonToRefund;
	}
	
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("RefundedMoney")+":"+String.format("%.2f", super.getMoney())
		+"€"+" Your money has been refund. The reason is: "+reasonToRefund;
	}
}
