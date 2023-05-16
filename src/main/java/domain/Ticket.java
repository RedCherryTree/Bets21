package domain;

import java.io.Serializable;

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
public class Ticket implements Serializable{
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer tiquetNumber;
	
	private String egoera;
	private final static String EGOERA_BERRIA="berria";
	private final static String EGOERA_TRATATZEN="tratatzen";
	private final static String EGOERA_ITXITA="itxita";
	
	private String description;
	@XmlIDREF
	private RegisteredUser user;
	
	public Ticket() {
		super();
	}

	
	public Ticket(String description, RegisteredUser user) {
		this();
		this.egoera=EGOERA_BERRIA;
		this.user=user;
		this.description=description;
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
	public static String getEGOERA_BERRIA() {
		return EGOERA_BERRIA;
	}
	public static String getEGOERA_TRATATZEN() {
		return EGOERA_TRATATZEN;
	}
	public static String getEGOERA_ITXITA() {
		return EGOERA_ITXITA;
	}


	@Override
	public String toString() {
		return "Ticket [tiquetNumber=" + tiquetNumber + ", egoera=" + egoera + ", description=" + description + "]";
	}
	
	
}
