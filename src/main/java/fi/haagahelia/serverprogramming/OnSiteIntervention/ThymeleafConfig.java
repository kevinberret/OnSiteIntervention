package fi.haagahelia.serverprogramming.OnSiteIntervention;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@Configuration
public class ThymeleafConfig {
	
	// use the new Java8 time classes for rendering purposes
	// code found here: https://gist.github.com/romach/337a788b5303454e08811b80767f55f1 
	@Bean
    public IDialect conditionalCommentDialect() {
        return new Java8TimeDialect();
    }
}