package fi.haagahelia.serverprogramming.OnSiteIntervention.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.AddressRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.CustomerRepository;

/**
 * This class is used for all business code for Address objects.
 * @author kb
 *
 */
@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	public List<Address> getAllAddresses() {
		return (List<Address>) addressRepository.findAll();
	}
	
	public Optional<Address> getAddress(Long id) {
		return addressRepository.findById(id);
	}
	
	public Address addCustomer(Address address) {
		return addressRepository.save(address);
	}
	
	public Address updateCustomer(Address address) {
		return addressRepository.save(address);
	}
	
	public boolean deleteAddress(Long id) {
		// Try to find the desired customer
		Optional<Address> element = addressRepository.findById(id);
		
		if(element.isPresent()) {
			addressRepository.delete(element.get());
			return true;
		}
		
		return false;
	}
}