package com.cogent.fooddeliveryapp.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cogent.fooddeliveryapp.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class); 
	
	
	@Value("${com.cogent.fooddeliveryapp.jwtSecret}") // import springframework not lombok
	private String jwtSecret;
	
	
	@Value("${com.cogent.fooddeliveryapp.jwtExpirationMs}") // import springframework not lombok
	private long jwtExpirationMs;
	
	// to generate the token
	public String generateToken(Authentication authentication) {
		
		// upcasting ?
//		Authentication authentication = userPrincipal;
		// downcasting ; principal is getPrincipal
		UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
		
		// it is using builer DP.
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername()) // name
				.setIssuedAt(new Date())  // iat
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))  // expiring date
				.signWith(SignatureAlgorithm.HS512, jwtSecret)  // SIGNATURE data details
				.compact();  // compact inquiry
		
	}
	
	// validation of token;     String authToken mean ; token mixed with number and character
	public boolean validateJwtToken(String authToken) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired signature: {}", e.getMessage());
			
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
			
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
			
		} catch (SignatureException e) {
			logger.error("Invalid JWT token signature: {}", e.getMessage());
			
		} catch (IllegalArgumentException e) {
			logger.error("Illegal JWT token: {}", e.getMessage());
			
		}
		return false;
	}
	
	// retrieve token
	// get name from the token ==> 
	public String getUsernameFromJwtToken(String authToken) {
		return Jwts.parser()  // compact ---> javaobject
				.setSigningKey(jwtSecret)  // ---> secret key ---> encoding is done.
				.parseClaimsJws(authToken)  // provided actual token
				.getBody()  // extracting the body content
				.getSubject();  // extracting the subject from JWT Payload(data)
	}
	
}
