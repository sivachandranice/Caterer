package com.catering.model;

import javax.validation.Constraint;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CatererCapacity {
	
	
	@Min(value = 1, message = "Minimum Guest Occupancy is negative")
	private int minGuestOccupancy;
	@Min(value = 1, message = "Minimum Guest Occupancy is negative")
	private int maxGuestOccupancy;
}
