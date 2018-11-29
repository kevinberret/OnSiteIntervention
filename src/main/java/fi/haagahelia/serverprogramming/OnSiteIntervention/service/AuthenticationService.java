package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class AuthenticationService {
	static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
	static final String SIGNINGKEY = "a0Ay)p>sx*ZJpe#@iv=KkHZ#KBcB]6%My`_m6W_xTc%,[KFu:K0.]^0B[4#7lgh"; // secret key used for generate/decrypt the JWT
	static final String PREFIX = "Bearer";
	
	// Add token to Authorization header
	static public void addToken(HttpServletResponse res, String username, String role) {
		// generate a token with the username, expirationtime and the key
		String JwtToken = Jwts.builder().claim("role", role).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
		        .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
		        .compact();
		
		res.addHeader("Authorization", PREFIX + " " + JwtToken);
		res.addHeader("Access-Control-Expose-Headers", "Authorization");
	}
	
	// Get token from Authorization header
	static public Authentication getAuthentication(HttpServletRequest request)  throws AuthenticationException, SignatureException {
		// get token and try to parse it
		String token = request.getHeader("Authorization");
		if (token != null) {
			Claims body = Jwts.parser()
				  .setSigningKey(SIGNINGKEY)
				  .parseClaimsJws(token.replace(PREFIX, ""))
				  .getBody();
			
			// get username and its roles
			String user = body.getSubject();
			String role = (String) body.get("role");
			
			if (user != null && role != null) {				
				List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
				return new UsernamePasswordAuthenticationToken(user, null, authorityList);
			}
		}
		return null;
	}
}