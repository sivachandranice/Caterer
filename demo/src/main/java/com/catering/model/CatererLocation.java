package com.catering.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CatererLocation {
	
	private String cityName;
	
	private String address;
	
	private int postalCode;

}
