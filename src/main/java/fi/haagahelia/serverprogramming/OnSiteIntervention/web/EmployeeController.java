package fi.haagahelia.serverprogramming.OnSiteIntervention.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.EmployeeService;

/**
 * This is the controller for employees.
 * @author kb
 *
 */
@Controller
@RequestMapping("/app/employee")
public class EmployeeController {
	// get the service layer injected
	@Autowired
	private EmployeeService employeeService;
	
	// -----------
	// Admin pages
	// -----------
	@GetMapping("/showlist")
	public String showList(Model model) {
		// get all employees
		model.addAttribute("employees", employeeService.getAllEmployees());
		
		// render data in template
		return "employee/list";
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addEmployee(Model model) {
		// create a new employee and set edit flag to false (rendering purposes)
		model.addAttribute("employee", new Employee());
		model.addAttribute("edit", false);
		
		// render data in template
		return "employee/save";
	}
	
	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editEmployee(@PathVariable("id") Long id, Model model) {
		// get the desired employee and set edit flag to true (rendering purposes)
		model.addAttribute("employee", employeeService.getEmployee(id));
		model.addAttribute("edit", true);
		
		// render data in template
		return "employee/save";
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveEmployee(Employee employee) {		
		// save the entity via the service layer
		employeeService.addEmployee(employee);
		
		// redirect to list of employees
		return "redirect:/app/employee/showlist";
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteEmployee(@PathVariable("id") Long id, Model model) {
		// delete the desired employee via the service layer
		employeeService.deleteEmployeeById(id);
		
		// render data in template
		return "redirect:/app/employee/showlist";
	}
}