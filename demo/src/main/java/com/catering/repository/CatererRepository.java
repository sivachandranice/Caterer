package com.catering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.catering.model.Caterer;


public interface CatererRepository extends MongoRepository<Caterer, String> {
	
	List<Caterer> findByLocation(String location);
	List<Caterer> findByName(String name);
	Optional<Caterer> findById(String catererId);
	@Query(value ="{\"location.cityName\": '?0'}")
	Page<Caterer> findByCityNameIgnoringCase(String cityName,Pageable pageable);

}
