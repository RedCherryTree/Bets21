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
	
	public RefundMoney() {
		super();
	}
	
	public RefundMoney(double money, RegisteredUser user, String reasonToRefund) {
		super(money, user);
		this.reasonToRefund=reasonToRefund;
	}

	public String getReasonToRefund() {
		return reasonToRefund;
	}

	public void setReasonToRefund(String reasonToRefund) {
		this.reasonToRefund = reasonToRefund;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("RefundedMoney")+":"+String.format("%.2f", super.getMoney())
		+"€"+" Your money has been refund. The reason is: "+reasonToRefund;
	}
}

