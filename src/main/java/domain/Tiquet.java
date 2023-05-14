package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity 
public class Tiquet {
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer tiquetNumber;
	
	private String egoera;
	private final String EGOERA_BERRIA="berria";
	private final String EGOERA_TRATATZEN="tratatzen";
	private final String EGOERA_ITXITA="itxita";
	
	private String description;
	@XmlIDREF
	private RegisteredUser user;
	
	public Tiquet() {
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
	public Tiquet(String description, RegisteredUser user) {
		this();
		this.egoera=EGOERA_BERRIA;
		this.user=user;
	}
	
	public Integer getTiquetNumber() {
		return tiquetNumber;
	}
	public void setTiquetNumber(Integer tiquetNumber) {
		this.tiquetNumber = tiquetNumber;
	}
	public String getEgoera() {
		return egoera;
	}
	public void setEgoera(String egoera) {
		this.egoera = egoera;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RegisteredUser getUser() {
		return user;
	}
	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	public String getEGOERA_BERRIA() {
		return EGOERA_BERRIA;
	}
	public String getEGOERA_TRATATZEN() {
		return EGOERA_TRATATZEN;
	}
	public String getEGOERA_ITXITA() {
		return EGOERA_ITXITA;
	}
	
	
}
