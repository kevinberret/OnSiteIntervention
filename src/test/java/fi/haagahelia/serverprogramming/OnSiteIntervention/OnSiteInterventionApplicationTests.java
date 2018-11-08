package fi.haagahelia.serverprogramming.OnSiteIntervention;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.serverprogramming.OnSiteIntervention.web.CustomerController;
import fi.haagahelia.serverprogramming.OnSiteIntervention.web.EmployeeController;
import fi.haagahelia.serverprogramming.OnSiteIntervention.web.IndexController;
import fi.haagahelia.serverprogramming.OnSiteIntervention.web.InterventionController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnSiteInterventionApplicationTests {
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private EmployeeController employeeController;
	
	@Autowired
	private IndexController indexController;
	
	@Autowired
	private InterventionController interventionController;

	@Test
	public void contextLoads() {
		// ensure that the controllers are created
		assertThat(customerController).isNotNull();
		assertThat(employeeController).isNotNull();
		assertThat(indexController).isNotNull();
		assertThat(interventionController).isNotNull();
	}
}