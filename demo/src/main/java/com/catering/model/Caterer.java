package com.catering.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@Document(collection = "caterer")
//public class Caterer extends RepresentationModel<Caterer> {
public class Caterer{	
	@Id
	private String id;
	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	private CatererLocation location;
	
	private CatererCapacity capacity;
	
	private CatererContactInfo contactInfo;

}
