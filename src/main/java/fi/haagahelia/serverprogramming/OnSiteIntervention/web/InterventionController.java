package fi.haagahelia.serverprogramming.OnSiteIntervention.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.serverprogramming.OnSiteIntervention.Service.CustomerService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.Service.EmployeeService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.Service.InterventionService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.CustomerRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.EmployeeRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Intervention;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.InterventionRepository;

/**
 * This is the controller for interventions.
 * @author kb
 *
 */
@Controller
@RequestMapping("/intervention")
public class InterventionController {
	// get the service layers injected
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private InterventionService interventionService;
	
	// -----------
	// Admin pages
	// -----------
	@GetMapping("/showlist")
	public String listInterventions(Model model) {
		// get all employees
		model.addAttribute("interventions", interventionService.getAllInterventions());
		
		// render data in template
		return "intervention/list";
	}
	
	@GetMapping("/add")
	public String addIntervention(Model model) {
		// create a new intervention and set edit flag to false (rendering purposes)
		// get the list of customers and employees for the creation of the intervention
		Intervention intervention = new Intervention();
		List<Customer> customers = customerService.getAllCustomers();
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("intervention", intervention);
		model.addAttribute("customers", customers);
		model.addAttribute("employees", employees);
		model.addAttribute("edit", false);
		
		// render data in template
		return "intervention/save";
	}
	
	@GetMapping("/edit/{id}")
	public String editIntervention(@PathVariable("id") Long id, Model model) {
		// get the desired intervention and set edit flag to true (rendering purposes)
		List<Customer> customers = customerService.getAllCustomers();
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("intervention", interventionService.getIntervention(id).get());
		model.addAttribute("customers", customers);
		model.addAttribute("employees", employees);
		model.addAttribute("edit", true);
		
		// render data in template
		return "intervention/save";
	}
	
	@PostMapping("/save")
	public String saveIntervention(Intervention intervention) {
		System.out.println("client " + intervention.getCustomer().getFirstname());
		System.out.println("save date " + intervention.getDate().toString());
		// save the entity via the service layer
		interventionService.addIntervention(intervention);
		
		// redirect to list of interventions
		return "redirect:/intervention/showlist";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteIntervention(@PathVariable("id") Long id, Model model) {
		// delete the desired customer via the service layer
		interventionService.deleteIntervention(id);
		
		// render data in template
		return "redirect:/intervention/showlist";
	}
}