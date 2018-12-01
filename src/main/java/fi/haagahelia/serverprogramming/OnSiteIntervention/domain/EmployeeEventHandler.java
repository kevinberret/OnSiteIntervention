package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RepositoryEventHandler(Employee.class)
public class EmployeeEventHandler {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private EmployeeRepository employeeRepo;	

	@HandleBeforeSave
	@HandleBeforeCreate
	public void handleEmployeeSave(Employee newEmployee) {
		// detach the employee from the context so we can get the actual data from the database
		em.detach(newEmployee);	
		
		// get the current data from the database
		Optional<Employee> currentEmployee = employeeRepo.findById(newEmployee.getId());			
	      
		// if employee already exists in the database, modify the passed employee to match the data
		if(currentEmployee.isPresent()) {
			// if employee already exists but user didn't want to change the password, get the one stored in db
			if(newEmployee.getPasswordHash() == null) {
				newEmployee.setPasswordHash(currentEmployee.get().getPasswordHash());
			}else {
				// modify existing password
				newEmployee.setPasswordHash(getHashedPassword(newEmployee.getPasswordHash()));
			}
			
			// if employee already exists but no modifications were made to the firstname, get the one stored in db
			if(newEmployee.getFirstname() == null) {
				newEmployee.setFirstname(currentEmployee.get().getFirstname());
			}
			
			// if employee already exists but no modifications were made to the lastname, get the one stored in db
			if(newEmployee.getLastname() == null) {
				newEmployee.setLastname(currentEmployee.get().getLastname());
			}
			
			// if employee already exists but no modifications were made to the workrate, get the one stored in db
			if(newEmployee.getWorkRate() == 0) {
				newEmployee.setWorkRate(currentEmployee.get().getWorkRate());
			}
			
			// if employee already exists but no modifications were made to the role, get the one stored in db
			if(newEmployee.getRole() == null) {
				newEmployee.setRole(currentEmployee.get().getRole());
			}
			
			// if employee already exists but no modifications were made to the username, get the one stored in db
			if(newEmployee.getUsername() == null) {
				newEmployee.setUsername(currentEmployee.get().getUsername());
			}
		}else {
			// if new employee, has his password to ensure some kind of security
			newEmployee.setPasswordHash(getHashedPassword(newEmployee.getPasswordHash()));
		}  
	}
	
	// hash the clear password provided as parameter
	private String getHashedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
}