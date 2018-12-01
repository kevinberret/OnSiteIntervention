package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This entity represents an Intervention.
 * @author kb
 *
 */
@Entity
public class Intervention {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="intervention_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;
	
	private Duration duration;
	
	private String description;
	private String materialNeeded;
	private String feedback;
	private boolean done;
	private String signature;
	private String phoneNumber;
	
	public Intervention() {
		this.date = LocalDate.now();
		this.time = LocalTime.now();
	}
	
	public Intervention(Customer customer, Employee employee, LocalDate date, LocalTime time, Duration duration) {
		this.customer = customer;
		this.employee = employee;
		this.date = date;
		this.time = time;
		this.duration = duration;
	}

	// getters and setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterialNeeded() {
		return materialNeeded;
	}

	public void setMaterialNeeded(String materialNeeded) {
		this.materialNeeded = materialNeeded;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}