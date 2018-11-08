package fi.haagahelia.serverprogramming.OnSiteIntervention.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.CustomerService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.EmployeeService;

/**
 * This is the controller for customers.
 * @author kb
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	// get the service layer injected
	@Autowired
	private CustomerService customerService;
	
	// -----------
	// Admin pages
	// -----------
	@GetMapping("/showlist")
	public String showList(Model model) {
		// get all customers
		model.addAttribute("customers", customerService.getAllCustomers());
		
		// render data in template
		return "customer/list";
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addCustomer(Model model) {
		// create a new customer and set edit flag to false (rendering purposes)
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("edit", false);
		
		// render data in template
		return "customer/save";
	}
	
	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String editCustomer(@PathVariable("id") Long id, Model model) {
		// get the desired customer and set edit flag to true (rendering purposes)
		model.addAttribute("customer", customerService.getCustomer(id));
		model.addAttribute("edit", true);
		
		// render data in template
		return "customer/save";
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveCustomer(Customer customer) {
		// save the entity via the service layer
		customerService.addCustomer(customer);
		
		// redirect to list of customers
		return "redirect:/customer/showlist";
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCustomer(@PathVariable("id") Long id, Model model) {
		// delete the desired customer via the service layer
		customerService.deleteCustomerById(id);
		
		// render data in template
		return "redirect:/customer/showlist";
	}
}