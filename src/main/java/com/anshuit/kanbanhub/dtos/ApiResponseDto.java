package com.anshuit.kanbanhub.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseDto {
	private LocalDateTime timestamp;
	private int statusCode;
	private String message;
	private HttpStatus status;
	private String path;
	
}