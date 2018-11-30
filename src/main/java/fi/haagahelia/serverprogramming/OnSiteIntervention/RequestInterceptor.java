package fi.haagahelia.serverprogramming.OnSiteIntervention;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.EmployeeService;

public class RequestInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// check if a user is logged-in (authentication not null, user authenticated and not anonymous user
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		if (modelAndView != null 
				&& loggedInUser != null 
				&& loggedInUser.isAuthenticated() 
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			// if so, add its informations to the modelandview so we can use them in the view
			String username = loggedInUser.getName();
		    
		    Employee employee = employeeService.getEmployeeByUsername(username);
	    	modelAndView.addObject("user", employee);
		}	    
	}
}