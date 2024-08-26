
package com.anshuit.kanbanhub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a custom AuthenticationProvider class which is responsible for
 * authenticating user by using CustomUserDetailsService. AuthenticationManager
 * will call this provider's authenticate method.
 * 
 * @returns returns an authentication token with authenticated set to true if
 *          successful.( Please check the constructor of the token being
 *          returned )
 * @author Priyanshu Anand
 */

@Slf4j
@Component
public class JwtCustomAuthenticationProvider implements AuthenticationProvider {

	public JwtCustomAuthenticationProvider() {
		log.info("JwtCustomAuthenticationProvider Successfully Instantiated !!");
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			throw e;
		}

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
			return authenticationToken;
		} else {
			throw new BadCredentialsException("Invalid Password !! Password did not match !!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
