package domain;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class DepositMoney extends Transaction  implements Serializable{
	private String paymentOption;
	private String paymentMethod;
	
	public DepositMoney() {
		super();
	}
	/**
	 * Deposit money transaction builder
	 * 
	 * @param transactionType
	 * @param paymentOpton
	 * @param paymentMethod
	 * @param money
	 * @param user
	 */
	public DepositMoney(String paymentOpton, String paymentMethod,double money, RegisteredUser user) {
		super(money, user);
		this.paymentOption=paymentOption;
		this.paymentMethod=paymentMethod;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Override
	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("DepositMoney")+":"+String.format("%.2f",  super.getMoney())+"€";
	}
}
