package com.anshuit.kanbanhub.services.impls;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anshuit.kanbanhub.constants.GlobalConstants;
import com.anshuit.kanbanhub.dtos.TokenDto;
import com.anshuit.kanbanhub.entities.Employee;
import com.anshuit.kanbanhub.entities.RefreshToken;
import com.anshuit.kanbanhub.enums.ExceptionDetailsEnum;
import com.anshuit.kanbanhub.exceptions.CustomException;
import com.anshuit.kanbanhub.repositories.RefreshTokenRepository;
import com.anshuit.kanbanhub.security.MyJwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefreshTokenServiceImpl {

	@Autowired
	private MyJwtUtil myJwtUtil;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private EmployeeServiceImpl employeeService;

	public TokenDto performRefresh(String refreshToken,String employeeDisplayId) {
		log.info("Performing Token Refresh For : " + refreshToken);
		RefreshToken foundRefreshToken = this.findRefreshTokenByRefreshTokenString(refreshToken);
		Employee employee = foundRefreshToken.getEmployee();
		
		if(!employee.getEmployeeDisplayId().equals(employeeDisplayId)) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.REFRESH_TOKEN_DOES_NOT_BELONG_TO_EMPLOYEE_WITH_DISPLAY_ID,employeeDisplayId);
		}
		
		TokenDto tokenDto = null;
		boolean isRefreshTokenValid = this.checkIfRefreshTokenValidOnlyOperation(foundRefreshToken);
		log.info("Refresh Token Valid : " + isRefreshTokenValid);
		if (isRefreshTokenValid) {
			UserDetails userDetails = User.builder().username(employee.getEmail()).password(employee.getPassword())
					.authorities(employee.getRole()).build();
			String token = myJwtUtil.generateToken(userDetails);
			tokenDto = new TokenDto();
			tokenDto.setToken(token);
			tokenDto.setRefreshToken(refreshToken);
		} else {
			log.info("Refresh Token Already Expired , Deleting The Token From DB !!");
			this.deleteRefreshTokenByRefreshTokenObjectOnlyOperation(foundRefreshToken);
		}
		return tokenDto;
	}

	public RefreshToken getRefreshToken(Employee employee) {
		RefreshToken refreshToken = employee.getRefreshToken();
		if (refreshToken == null) {
			log.info("Refresh Token Not Found !! Generating New Refresh Token !!");
			refreshToken = this.createRefreshTokenOnlyOperation(employee);
		} else if (this.checkIfRefreshTokenValidOnlyOperation(refreshToken)) {
			log.info("Refresh Token Already Present And Is Valid , Updating The Expiry !!");
			refreshToken.setExpiry(
					new Date(System.currentTimeMillis() + GlobalConstants.JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS));
		} else {
			log.info("Refresh Token Present But Not Valid !! Updating Old Refresh Token With New Refresh Token and Expiry !!");
			RefreshToken newRefreshTokenDetails = this.createRefreshTokenOnlyOperation(employee);
			refreshToken.setRefreshToken(newRefreshTokenDetails.getRefreshToken());
			refreshToken.setExpiry(newRefreshTokenDetails.getExpiry());
		}
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken createRefreshTokenOnlyOperation(Employee employee) {
		return RefreshToken.builder().refreshToken(UUID.randomUUID().toString()).employee(employee)
				.expiry(new Date(
						System.currentTimeMillis() + GlobalConstants.JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS))
				.build();
	}

	public RefreshToken findRefreshTokenByRefreshTokenString(String refreshToken) {
		RefreshToken foundRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
						ExceptionDetailsEnum.REFRESH_TOKEN_NOT_FOUND_WITH_TOKEN, refreshToken));
		return foundRefreshToken;
	}

	public RefreshToken findRefreshTokenById(Integer refreshTokenId) {
		RefreshToken foundRefreshToken = refreshTokenRepository.findById(refreshTokenId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
						ExceptionDetailsEnum.REFRESH_TOKEN_NOT_FOUND_WITH_ID, refreshTokenId));
		return foundRefreshToken;
	}

	public boolean checkIfRefreshTokenValidFinderPlusOperation(String refreshToken) {
		RefreshToken foundRefreshToken = findRefreshTokenByRefreshTokenString(refreshToken);
		return this.checkIfRefreshTokenValidOnlyOperation(foundRefreshToken);
	}

	public boolean checkIfRefreshTokenValidOnlyOperation(RefreshToken refreshToken) {
		if (refreshToken == null)
			return false;

		Date current = new Date(System.currentTimeMillis());
		if (current.before(refreshToken.getExpiry())) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteRefreshTokenByIdFinderPlusOperation(Integer refreshTokenId) {
		RefreshToken foundRefreshToken = this.findRefreshTokenById(refreshTokenId);
		this.deleteRefreshTokenByRefreshTokenObjectOnlyOperation(foundRefreshToken);
	}

	public void deleteRefreshTokenByRefreshTokenStringFinderPlusOperation(String refreshToken) {
		RefreshToken foundRefreshToken = this.findRefreshTokenByRefreshTokenString(refreshToken);
		this.deleteRefreshTokenByRefreshTokenObjectOnlyOperation(foundRefreshToken);
	}

	public void deleteRefreshTokenByRefreshTokenObjectOnlyOperation(RefreshToken refreshToken) {
		// We will first have to break the relationship and save changes on employee
		Employee employee = refreshToken.getEmployee();
		employee.setRefreshToken(null);
		employeeService.saveOrUpdateEmployee(employee);
		refreshTokenRepository.delete(refreshToken);
	}
}
