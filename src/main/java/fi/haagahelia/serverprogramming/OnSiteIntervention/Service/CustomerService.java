package fi.haagahelia.serverprogramming.OnSiteIntervention.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.AddressRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.CustomerRepository;

/**
 * This class is used for all business code for Customer objects.
 * @author kb
 *
 */
@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	public List<Customer> getAllCustomers() {
		return (List<Customer>) customerRepo.findAll();
	}
	
	public Optional<Customer> getCustomer(Long id) {
		return customerRepo.findById(id);
	}
	
	public Customer addCustomer(Customer customer) {
		// save the address first and then save the customer
		addressRepo.save(customer.getAddress());
		return customerRepo.save(customer);
	}
	
	public Customer updateCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public boolean deleteCustomer(Long id) {
		// Try to find the desired customer
		Optional<Customer> element = customerRepo.findById(id);
		
		if(element.isPresent()) {		
			customerRepo.delete(element.get());
			return true;
		}
		
		return false;
	}
}