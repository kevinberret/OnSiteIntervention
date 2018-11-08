package fi.haagahelia.serverprogramming.OnSiteIntervention;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.aspectj.weaver.Iterators;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

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
	@Profile("!test")
	public CommandLineRunner demo(CustomerRepository customerRepo, AddressRepository addressRepo, EmployeeRepository employeeRepo, InterventionRepository interventionRepo) {
		return args->{
			// insert data only if database is empty
			if(addressRepo.count() == 0 
					&& customerRepo.count() == 0
					&& employeeRepo.count() == 0
					&& interventionRepo.count() == 0) {
				Address address1 = new Address("Baker Street", "221b", "London", "NW1 6XE", 51.523770, -0.158556);
				addressRepo.save(address1);
				Customer customer = new Customer("Sherlock", "Holmes", address1);
				customerRepo.save(customer);
				Employee employee = new Employee("John", "Watson", 100, "user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
				employeeRepo.save(employee);
				Employee employee2 = new Employee("John", "Doe", 100, "admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
				employeeRepo.save(employee2);
				Intervention intervention = new Intervention(customer, employee, LocalDate.of(2018, 12, 25), LocalTime.of(15, 0), Duration.ofHours(1));
				interventionRepo.save(intervention);
			}
		};
	}
}