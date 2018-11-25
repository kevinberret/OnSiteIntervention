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
	public CommandLineRunner demo(CustomerRepository customerRepo, EmployeeRepository employeeRepo, InterventionRepository interventionRepo) {
		return args->{
			/*interventionRepo.deleteAll();
			employeeRepo.deleteAll();
			customerRepo.deleteAll();*/
			
			// insert data only if database is empty
			if(customerRepo.count() == 0
					&& employeeRepo.count() == 0
					&& interventionRepo.count() == 0) {
				Address address1 = new Address("Baker Street", "221b", "London", "NW1 6XE", 51.523770, -0.158556);
				
				Customer customer = new Customer("Sherlock", "Holmes", address1);
				System.out.println(customerRepo.save(customer).getId());
				Employee employee = new Employee("John", "Watson", 100, "user", "$2a$10$uljXojmzMK8acsx5LDxWluYL9AS7BBTH/bItlKFdTwLx5vT94doFi", "USER");
				System.out.println(employeeRepo.save(employee).getId());
				Employee employee2 = new Employee("John", "Doe", 100, "admin", "$2a$10$9BxOY8KPMisep7c7/6y1Xe6kTLRDES72BjweTaY72qlYHezFzMhqW", "ADMIN");
				System.out.println(employeeRepo.save(employee2));
				Intervention intervention = new Intervention(customer, employee, LocalDate.of(2018, 12, 25), LocalTime.of(15, 0), Duration.ofHours(1));
				System.out.println(interventionRepo.save(intervention));
			}
		};
	}
}