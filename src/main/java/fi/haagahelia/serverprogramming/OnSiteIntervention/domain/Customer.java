package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * This entity represents a Customer.
 * @author kb
 *
 */
@Entity
public class Customer extends Person{
	@OneToOne(cascade=CascadeType.REMOVE)
	protected Address address;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
	private List<Intervention> interventions;

	public Customer() {
	}

	public Customer(String firstname, String lastname, Address address) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.address=address;
	}

	// getters and setters
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}
}