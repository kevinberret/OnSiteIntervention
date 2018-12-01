package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.EmployeeRepository;

/**
 * This class is used for all business code for Employee objects.
 * @author kb
 *
 */
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepo;
	
	public long count() {
		return employeeRepo.count();
	}
	
	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepo.findAll();
	}
	
	public Optional<Employee> getEmployee(Long id) {
		return employeeRepo.findById(id);
	}
	
	public Employee getEmployeeByUsername(String username) {
		return employeeRepo.findByUsername(username);
	} 
	
	public Employee addEmployee(Employee employee) {
		Optional<Employee> tmpEmployee = employeeRepo.findById(employee.getId());
		if(tmpEmployee.isPresent()) {
			// if employee already exists but user didn't want to change the password, get the one stored in db
			if(employee.getPasswordHash().isEmpty()) {
				employee.setPasswordHash(tmpEmployee.get().getPasswordHash());
			}else {
				// modify existing password
				employee.setPasswordHash(getHashedPassword(employee.getPasswordHash()));
			}
		}else {
			// if new employee, has his password to ensure some kind of security
			employee.setPasswordHash(getHashedPassword(employee.getPasswordHash()));
		}
		
		return employeeRepo.save(employee);
	}
	
	private String getHashedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	public Employee updateEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}
	
	public boolean deleteEmployee(Employee employee) {
		// Try to find the desired employee
		Optional<Employee> element = employeeRepo.findById(employee.getId());
		
		if(element.isPresent()) {		
			employeeRepo.delete(employee);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteEmployeeById(Long id) {
		// Try to find the desired employee
		Optional<Employee> element = employeeRepo.findById(id);
		
		if(element.isPresent()) {
			employeeRepo.delete(element.get());
			return true;
		}
		
		return false;
	}
	
	public void deleteAll() {
		employeeRepo.deleteAll();
	}
}