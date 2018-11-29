package fi.haagahelia.serverprogramming.OnSiteIntervention;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.AccountCredentials;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.AuthenticationService;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.EmployeeService;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired
	private EmployeeService employeeService;
	
	public LoginFilter(String url, AuthenticationManager authManager, ApplicationContext ctx) {
		super(new AntPathRequestMatcher(url));
		// as generic filters have no link with the context, we have to specify this
		// source: https://stackoverflow.com/a/48107721
		this.employeeService = ctx.getBean(EmployeeService.class);
		
	    setAuthenticationManager(authManager);	    
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		// try to get a user representation from the token 
		AccountCredentials creds = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
		
		// authenticate the user
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword(),
						Collections.emptyList()
		        )
		);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String role = authResult.getAuthorities().iterator().next().toString();
		AuthenticationService.addToken(response, authResult.getName(), role);
		
		// add employee informations in the body
		Employee employee = employeeService.getEmployeeByUsername(authResult.getName());
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    JSONObject employeeJSON = new JSONObject();
	    employeeJSON.put("id", employee.getId());
	    employeeJSON.put("firstname", employee.getFirstname());
	    employeeJSON.put("lastname", employee.getLastname());
	    employeeJSON.put("username", employee.getUsername());
	    employeeJSON.put("role", employee.getRole());
	    
	    response.getWriter().write(employeeJSON.toJSONString());
	}
}