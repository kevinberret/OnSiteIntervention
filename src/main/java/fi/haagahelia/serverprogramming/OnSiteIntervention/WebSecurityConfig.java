package fi.haagahelia.serverprogramming.OnSiteIntervention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.serverprogramming.OnSiteIntervention.service.UserDetailServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {	
	@Configuration
    @Order(1)                                                        
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
	    private UserDetailServiceImpl userDetailsService;	
		
		@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	    }
		
		@Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/api/**")                               
                .authorizeRequests()
                .anyRequest()
                	.authenticated()
                .and()
                    .httpBasic()
        		.and()
        			.csrf()
        			.disable();
        }
    }
	
    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    	@Autowired
        private UserDetailServiceImpl userDetailsService;	
    	
    	@Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http
    		.authorizeRequests()
    			.antMatchers("/css/**", "/js/**")
    			.permitAll()
    		.anyRequest()
    			.authenticated()
    		.and()
    			.formLogin()
    			.loginPage("/login")
    			.defaultSuccessUrl("/index")
    			.permitAll()
    		.and()
    			.logout()
    			.permitAll()
    		.and()
    	      	.exceptionHandling()
    	      	.accessDeniedPage("/403");
        }
    }
}