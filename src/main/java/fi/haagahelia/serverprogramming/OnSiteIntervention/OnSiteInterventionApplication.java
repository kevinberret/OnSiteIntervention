package fi.haagahelia.serverprogramming.OnSiteIntervention;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.AddressRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.CustomerRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.EmployeeRepository;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Intervention;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.InterventionRepository;

@SpringBootApplication
public class OnSiteInterventionApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnSiteInterventionApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepo, AddressRepository addressRepo, EmployeeRepository employeeRepo, InterventionRepository interventionRepo) {
		return args->{
			Address address1 = new Address("Baker Street", "221b", "London", "NW1 6XE", 51.523770, -0.158556);
			addressRepo.save(address1);
			
			Customer customer = new Customer("Sherlock", "Holmes", address1);
			customerRepo.save(customer);
			
			Employee employee = new Employee("John", "Watson", 100);
			employeeRepo.save(employee);
			
			Intervention intervention = new Intervention(customer, employee, LocalDate.of(2018, 12, 25), LocalTime.of(15, 0), Duration.ofHours(1));
			interventionRepo.save(intervention);
		};
	}
}