package com.anshuit.kanbanhub.exceptions;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anshuit.kanbanhub.dtos.ApiResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseDto> handleCustomException(CustomException ex, HttpServletRequest request) {
		ApiResponseDto apiResponseDto = ApiResponseDto.builder().message(ex.getMessage()).timestamp(LocalDateTime.now())
				.status(ex.getStatus()).statusCode(ex.getStatus().value()).path(request.getRequestURI()).build();

		if (Objects.nonNull(ex.getExceptionDetailsEnum())) {
			apiResponseDto.setExceptionCode(ex.getExceptionDetailsEnum().getExceptionCode());
		}

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, ex.getStatus());
	}
}
