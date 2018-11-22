package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This entity represents an Employee.
 * @author kb
 *
 */
@Entity
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "employee_id"))
})
public class Employee extends Person {
	// entity attributes
	private int workRate;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="employee")
	private List<Intervention> interventions;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	@JsonIgnore
	private String passwordHash;
	
	@Column(name="role", nullable=false)
	@JsonIgnore
	private String role;
	
	public Employee() {
	}

	public Employee(String firstname, String lastname, int workRate, String username, String passwordHash, String role) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.workRate = workRate;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}