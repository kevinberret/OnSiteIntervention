package fi.haagahelia.serverprogramming.OnSiteIntervention;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import fi.haagahelia.serverprogramming.OnSiteIntervention.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class AuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest)request);
		    
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		    chain.doFilter(request, response);
		}catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | UsernameNotFoundException e) {
		    // if the token is malformed => error 401  
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}