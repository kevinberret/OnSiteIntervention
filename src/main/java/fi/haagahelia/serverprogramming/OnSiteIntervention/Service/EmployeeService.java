package fi.haagahelia.serverprogramming.OnSiteIntervention.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepo.findAll();
	}
	
	public Optional<Employee> getEmployee(Long id) {
		return employeeRepo.findById(id);
	}
	
	public Employee addEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}
	
	public Employee updateEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}
	
	public boolean deleteEmployee(Long id) {
		// Try to find the desired employee
		Optional<Employee> element = employeeRepo.findById(id);
		
		if(element.isPresent()) {
			employeeRepo.delete(element.get());
			return true;
		}
		
		return false;
	}
}