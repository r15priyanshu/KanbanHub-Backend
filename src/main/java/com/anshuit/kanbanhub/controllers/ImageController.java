package com.anshuit.kanbanhub.controllers;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.kanbanhub.services.impls.EmployeeServiceImpl;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private EmployeeServiceImpl employeeService;

	@GetMapping(value = "serveProfilePicture/employee/{employeeId}")
	public ResponseEntity<byte[]> serveProfilePicture(@PathVariable("employeeId") Integer employeeId) {
		byte[] profilePicData = employeeService.getEmployeeProfilePicData(employeeId);
		String contentType = new Tika().detect(profilePicData);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		return new ResponseEntity<>(profilePicData, headers, HttpStatus.OK);
	}
}
