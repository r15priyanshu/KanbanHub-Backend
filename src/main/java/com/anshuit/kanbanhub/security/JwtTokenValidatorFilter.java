package com.anshuit.kanbanhub.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anshuit.kanbanhub.constants.GlobalConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private MyJwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Inside JwtTokenValidatorFilter !! URL : " + request.getServletPath());
		String email = null;
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			log.info("Authorization Header With Bearer Token Present !!");
			String encodedTokenWithoutBearer = authorizationHeader.substring(7);
			log.info("Encoded Token Without Bearer : " + encodedTokenWithoutBearer);
			try {
				log.info("Extracting Username From Token...");
				email = jwtUtil.extractUsername(encodedTokenWithoutBearer);
			} catch (SignatureException e) {
				log.info("JWT Signature Does Not Match Locally Computed signature !! Token Might Have Been Tampered !!");
			} catch (MalformedJwtException e) {
				log.info("Token Malformed !! Token Might Have Been Tampered !!");
			}catch (ExpiredJwtException e) {
				log.info("Token Already Expired !!");
			}

			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtUtil.validateToken(encodedTokenWithoutBearer, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							email, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				} else {
					log.info("Token is not valid !!");
				}
			}
		} else {
			log.info(
					"Authorization Header is either empty or Does not starts With Bearer !! Invoking Next Filter in the Chain !!");
		}

		// Should Always Execute
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals(GlobalConstants.LOGIN_URL);
	}
}
