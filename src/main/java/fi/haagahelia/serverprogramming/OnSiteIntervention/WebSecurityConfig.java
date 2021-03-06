package fi.haagahelia.serverprogramming.OnSiteIntervention;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fi.haagahelia.serverprogramming.OnSiteIntervention.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {	
	@Autowired
    private UserDetailServiceImpl userDetailsService;	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Configuration
    @Order(1)                                                        
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
        protected void configure(HttpSecurity http) throws Exception {		
			// security for API endpoint
			// disable csrf support and cors
			http
				.csrf()
				.disable()
				.cors()
			// authorize requests on /api only to authenticated users
			.and()
				.antMatcher("/api/**")                               
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            // allow to authentication on /api/login
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/login")
				.permitAll()
				.anyRequest()
				.authenticated()
		    .and()
		    // Filter for the login requests to add the token in the header and the user informations in the body if correct token
		    .addFilterBefore(new LoginFilter("/api/login", authenticationManager(), getApplicationContext()),
		       UsernamePasswordAuthenticationFilter.class)
		    // Filter for other requests to check JWT in header if its correct and allow/disallow access to resource
		    .addFilterBefore(new AuthenticationFilter(),
		       UsernamePasswordAuthenticationFilter.class);
        }
		
		// configure cors configuration
		@Bean
	    protected CorsConfigurationSource corsConfigurationSource() {
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Arrays.asList("*"));
			config.setAllowedMethods(Arrays.asList("*"));
			config.setAllowedHeaders(Arrays.asList("*"));
			config.setAllowCredentials(true);
			config.applyPermitDefaultValues();
	        
			source.registerCorsConfiguration("/api/**", config);
			return source;
		}
    }
	
    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	// allow all resources in css and js folders to be accessed by anyone
        	http
    		.authorizeRequests()
    			.antMatchers("/css/**", "/js/**")
    			.permitAll()
    		// allow access to /app/** only to authenticated people
			.and()
				.authorizeRequests()
				.antMatchers("/app/**")
				.authenticated()
			// specify the login form url (access to everyone) and the redirect url after login
    		.and()
    			.formLogin()
    			.loginPage("/app/login")
    			.defaultSuccessUrl("/app/index")
    			.permitAll()
    		// logout can be accessed by everyone
    		.and()
    			.logout()
    			.permitAll()
			// custom error page
    		.and()
    	      	.exceptionHandling()
    	      	.accessDeniedPage("/app/403");
        }
    }
}