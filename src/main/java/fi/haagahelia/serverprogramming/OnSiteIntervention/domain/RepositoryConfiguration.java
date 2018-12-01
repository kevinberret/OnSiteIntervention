package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
	public RepositoryConfiguration(){
        super();
    }
 
    @Bean
    EmployeeEventHandler authorEventHandler() {
        return new EmployeeEventHandler();
    }
}