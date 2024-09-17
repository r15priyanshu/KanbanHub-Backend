package com.anshuit.kanbanhub.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.anshuit.kanbanhub.constants.GlobalConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyJwtUtil {

	private MyJwtUtil() {
		log.info("JwtUtil Successfully Instantiated !!");
	}

	private String SECRET_KEY = GlobalConstants.JWT_DEFAULT_SECRET;
	private long TOKEN_VALIDITY_IN_MILLISECONDS = GlobalConstants.JWT_TOKEN_VALIDITY_IN_MILLISECONDS;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(",")));
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims)
				.setIssuer(GlobalConstants.DEFAULT_APPLICATION_NAME).setSubject(subject)
				.setAudience(GlobalConstants.DEFAULT_FRONTEND_APPLICATION_NAME)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLISECONDS))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
