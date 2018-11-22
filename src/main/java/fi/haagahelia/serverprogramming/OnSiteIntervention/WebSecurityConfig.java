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
			http
				.csrf()
				.disable()
				.cors()
			.and()
				.antMatcher("/api/**")                               
                .authorizeRequests()
                .anyRequest()
                .authenticated()
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/login")
				.permitAll()
				.anyRequest()
				.authenticated()
		    .and()
		    // Filter for the login requests
		    .addFilterBefore(new LoginFilter("/api/login", authenticationManager()),
		       UsernamePasswordAuthenticationFilter.class)
		    // Filter for other requests to check JWT in header
		    .addFilterBefore(new AuthenticationFilter(),
		       UsernamePasswordAuthenticationFilter.class);
        }
		
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
        	http
    		.authorizeRequests()
    			.antMatchers("/css/**", "/js/**")
    			.permitAll()
			.and()
				.authorizeRequests()
				.antMatchers("/app/**")
				.authenticated()		
    		.and()
    			.formLogin()
    			.loginPage("/app/login")
    			.defaultSuccessUrl("/app/index")
    			.permitAll()
    		.and()
    			.logout()
    			.permitAll()
    		.and()
    	      	.exceptionHandling()
    	      	.accessDeniedPage("/app/403");
        }
    }
}