package com.anshuit.kanbanhub.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.dtos.ApiResponseDto;
import com.anshuit.kanbanhub.dtos.TokenDto;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.security.MyJwtUtil;
import com.anshuit.kanbanhub.services.impls.RefreshTokenServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TokenAndRefreshTokenController {

	@Autowired
	private MyJwtUtil myJwtUtil;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@PostMapping(GlobalConstants.CHECK_TOKEN_VALIDITY_URL)
	public ResponseEntity<Boolean> checkTokenValidity(@RequestBody TokenDto tokenDto) {
		Boolean isTokenExpired = true;
		try {
			log.info("Validating Token...");
			isTokenExpired = myJwtUtil.isTokenExpired(tokenDto.getToken());
		} catch (SignatureException e) {
			log.info(GlobalConstants.JWT_SIGNATURE_EXCEPTION_MESSAGE);
		} catch (MalformedJwtException e) {
			log.info(GlobalConstants.JWT_MALFORMED_EXCEPTION_MESSAGE);
		} catch (ExpiredJwtException e) {
			log.info(GlobalConstants.JWT_EXPIRED_EXCEPTION_MESSAGE);
		}
		Boolean isTokenValid = !isTokenExpired;
		log.info("Is Token Valid : " + isTokenValid);
		return new ResponseEntity<>(isTokenValid, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL)
	public ResponseEntity<Boolean> checkRefreshTokenValidity(@RequestBody TokenDto tokenDto) {
		boolean isRefreshTokenValid = refreshTokenService
				.checkIfRefreshTokenValidFinderPlusOperation(tokenDto.getRefreshToken());
		return new ResponseEntity<>(isRefreshTokenValid, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.REFRESH_TOKEN_URL)
	public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenDto tokenDto) {
		TokenDto tokenDtoResponse = refreshTokenService.performRefresh(tokenDto.getRefreshToken());
		if (tokenDtoResponse == null) {
			throw new CustomException(GlobalConstants.REFRESH_TOKEN_EXPIRED_WITH_PERFORM_RE_LOGIN_MSG,
					HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(tokenDtoResponse, HttpStatus.OK);
	}

	@DeleteMapping(GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL)
	public ResponseEntity<ApiResponseDto> deleteRefreshTokenByRefreshTokenString(@RequestBody TokenDto tokenDto,
			HttpServletRequest request) {
		refreshTokenService.deleteRefreshTokenByRefreshTokenStringFinderPlusOperation(tokenDto.getRefreshToken());
		HttpStatus responseStatus = HttpStatus.OK;
		ApiResponseDto apiResponseDto = ApiResponseDto.builder()
				.message(GlobalConstants.REFRESH_TOKEN_SUCCESSFULLY_DELETED).timestamp(LocalDateTime.now())
				.status(responseStatus).statusCode(responseStatus.value()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(apiResponseDto, responseStatus);
	}

	@DeleteMapping("/tokenAndRefreshToken/deleteRefreshTokenByRefreshTokenId/{refreshTokenId}")
	public ResponseEntity<ApiResponseDto> deleteRefreshTokenById(@PathVariable Integer refreshTokenId,
			HttpServletRequest request) {
		refreshTokenService.deleteRefreshTokenByIdFinderPlusOperation(refreshTokenId);
		HttpStatus responseStatus = HttpStatus.OK;
		ApiResponseDto apiResponseDto = ApiResponseDto.builder()
				.message(GlobalConstants.REFRESH_TOKEN_SUCCESSFULLY_DELETED).timestamp(LocalDateTime.now())
				.status(responseStatus).statusCode(responseStatus.value()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(apiResponseDto, responseStatus);
	}
}
