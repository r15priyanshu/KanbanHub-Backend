package com.anshuit.kanbanhub.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
