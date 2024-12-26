package com.anshuit.kanbanhub.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.security.JwtTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

	@Autowired
	private JwtTokenValidatorFilter jwtTokenValidatorFilter;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
		AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
		return authenticationManager;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/error", GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL,
						GlobalConstants.CHECK_TOKEN_VALIDITY_URL, GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL,
						GlobalConstants.REFRESH_TOKEN_BY_EMPLOYEE_DISPLAY_ID_URL,
						GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL)
				.permitAll().requestMatchers("/images/**").permitAll().anyRequest().authenticated());
		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new BasicAuthenticationEntryPoint()));
		http.addFilterBefore(jwtTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class);
		// http.httpBasic(withDefaults());
		// http.csrf(cc ->
		// cc.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
		http.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.cors(cc -> cc.configurationSource(this.createCorsConfigurationSource()));
		http.csrf(cc -> cc.disable());
		return http.build();
	}

	private CorsConfigurationSource createCorsConfigurationSource() {
		CorsConfigurationSource configurationSource = new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(GlobalConstants.ALLOWED_ORIGINS_LIST);
				config.setAllowedMethods(GlobalConstants.ALLOWED_METHODS_LIST);
				config.setAllowedHeaders(GlobalConstants.ALLOWED_HEADERS_LIST);
				config.setExposedHeaders(GlobalConstants.EXPOSED_HEADERS_LIST);
				config.setAllowCredentials(true);
				config.setMaxAge(3600L);
				return config;
			}
		};
		return configurationSource;
	}
}