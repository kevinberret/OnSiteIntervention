package fi.haagahelia.serverprogramming.OnSiteIntervention;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Intervention;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.CustomerService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.EmployeeService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.InterventionService;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class OnSiteInterventionRepoServiceTests {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private InterventionService interventionService;
	
	private static Address address;
	private static Customer customer;
	private static Employee employee;
	private static Intervention intervention;
	
	@BeforeClass
	public static void initTest(){
		// create an address used for the whole tests sequence
		address = new Address("Baker Street", "221b", "London", "NW1 6XE", 51.523770, -0.158556);
		
		// create a customer used for the whole tests sequence
		customer = new Customer("Benjamin", "Décaillet", address);
		
		// create an employee used for the whole tests sequence
		employee = new Employee("John", "Doe", 50, "user", "$2a$10$RUJ3TiHyiDV0/hZDINFex.OXuDFs4rEmjDTYQs4EiRZoLzCrMoj3m", "USER");
		
		// create an intervention used for the whole tests sequence
		intervention = new Intervention(customer, employee, LocalDate.of(2018, 12, 25), LocalTime.of(15, 0), Duration.ofHours(1));
	}
	
	//
	// Customer Tests
	//	
	@Test
	public void test_01_a_createACustomer(){
		// save the customer
		customerService.addCustomer(customer);
		
		// check that the returned customer corresponds to the created one
		assertThat(customer.getId())
			.isNotNull();
	}
	
	@Test
	public void test_01_b_getACustomer(){
		// check that a customer with a specific id can be get from the database
		assertThat(customerService.getCustomer(customer.getId()))
			.isNotEmpty();
		
		// check that the returned customer corresponds to the created one
		assertThat(customerService.getCustomer(customer.getId()).get().getId())
			.isEqualTo(customer.getId());
	}
	
	@Test
	public void test_01_c_getAllCustomers(){
		// get all the customers
		List<Customer> customers = customerService.getAllCustomers();
		
		// ensure we only have one customer
		assertThat(customers.size())
			.isEqualTo(1);
		
		// ensure the first customer is the same we added		
		assertThat(customers.get(0).getId())
			.isEqualTo(customer.getId());
	}	
	
	//
	// Employee Tests
	//	
	@Test
	public void test_02_a_createAnEmployee(){
		// save the employee
		employeeService.addEmployee(employee);
		
		// check that the returned employee corresponds to the created one
		assertThat(employee.getId())
			.isNotNull();
	}
	
	@Test
	public void test_02_b_getAnEmployee(){
		// check that a employee with a specific id can be get from the database
		assertThat(employeeService.getEmployee(employee.getId()))
			.isNotEmpty();
		
		// check that the returned customer corresponds to the created one
		assertThat(employeeService.getEmployee(employee.getId()).get().getId())
			.isEqualTo(employee.getId());
	}
	
	@Test
	public void test_02_c_getAllEmployees(){
		// get all the employees
		List<Employee> employees = employeeService.getAllEmployees();
		
		// ensure we only have one employee
		assertThat(employees.size())
			.isEqualTo(1);
		
		// ensure the first employee is the same we added		
		assertThat(employees.get(0).getId())
			.isEqualTo(employee.getId());
	}
	
	//
	// Intervention Tests
	//	
	@Test
	public void test_03_a_createAnIntervention(){
		// save the intervention
		interventionService.addIntervention(intervention);
		
		// check that the returned intervention corresponds to the created one
		assertThat(intervention.getId())
			.isNotNull();
	}
	
	@Test
	public void test_03_b_getAnIntervention(){
		// check that an intervention with a specific id can be get from the database
		assertThat(interventionService.getIntervention(intervention.getId()))
			.isNotEmpty();
		
		// check that the returned intervention corresponds to the created one
		assertThat(interventionService.getIntervention(intervention.getId()).get().getId())
			.isEqualTo(intervention.getId());
	}
	
	@Test
	public void test_03_c_getAllInterventions(){
		// get all the interventions
		List<Intervention> interventions = interventionService.getAllInterventions();
		
		// ensure we only have one intervention
		assertThat(interventions.size())
			.isEqualTo(1);
		
		// ensure the first intervention is the same we added		
		assertThat(interventions.get(0).getId())
			.isEqualTo(intervention.getId());
	}
	
	//
	// Deletion of objects
	//
	@Test
	public void test_04_a_deleteAnIntervention(){
		// try to find the intervention
		assertThat(interventionService.getIntervention(intervention.getId()))
			.isNotEmpty();		
		
		// delete the customer
		interventionService.deleteIntervention(intervention);
		
		// try to find the customer
		assertThat(interventionService.getIntervention(intervention.getId())).isEmpty();
	}
	
	@Test
	public void test_04_b_deleteACustomer(){
		// try to find the customer
		assertThat(customerService.getCustomer(customer.getId()))
			.isNotEmpty();
		
		// delete the customer
		customerService.deleteCustomer(customer);
		
		// try to find the customer
		assertThat(customerService.getCustomer(customer.getId())).isEmpty();
	}
	
	@Test
	public void test_04_c_deleteAnEmployee(){
		// try to find the employee
		assertThat(employeeService.getEmployee(employee.getId()))
			.isNotEmpty();
		
		// delete the employee
		employeeService.deleteEmployee(employee);
		
		// try to find the employee
		assertThat(employeeService.getEmployee(employee.getId()))
			.isEmpty();
	}
}