package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * This entity represents an Employee.
 * @author kb
 *
 */
@Entity
public class Employee extends Person {
	// entity attributes
	private int workRate;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="employee")
	private List<Intervention> interventions;
	
	public Employee() {
	}

	public Employee(String firstname, String lastname, int workRate) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.workRate = workRate;
	}

	// getters and setters
	public int getWorkRate() {
		return workRate;
	}

	public void setWorkRate(int workRate) {
		this.workRate = workRate;
	}

	public List<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}
}