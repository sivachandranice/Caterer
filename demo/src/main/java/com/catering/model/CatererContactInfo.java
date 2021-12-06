package com.catering.model;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CatererContactInfo {

	private String phoneNumber;
	
	private String mobileNumber;
	
	@Email(regexp=".*@.*\\..*", message = "Email should be valid")
	private String emailAddress;
}
