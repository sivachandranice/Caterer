package com.catering.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.catering.model.Caterer;
import com.catering.service.CatererService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@RestController
@EnableSwagger2
@Validated
@CrossOrigin(origins = "http://localhost:8080")
public class CatererController {

	@Autowired
	CatererService catererService;
	
	

	@ApiOperation(notes = "Default page", value = "Welcome page of project")
	@GetMapping("/")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully landed on welcome page"),
	@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> welcomeHome() {
		return new ResponseEntity<>("Welcome Shiva!!!!!!!", HttpStatus.OK);
	}
	

	
	@CachePut("Caterers")
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Caterer>> getCatererByName(@PathVariable("name") String name) {
		try {
			log.info("Get Caterers with name::"+name);
			List<Caterer> catererList = catererService.getCatererByName(name);
//			catererList.forEach(caterer -> {
//				caterer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CatererController.class).getCatererByName(name)).withRel("getCatererByName"));
//			});
			log.info("Response Body::Get Catterers by Name::"+catererList);
			return new ResponseEntity<>(catererList, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
	//Implementation of HATEOAS
	
	@CachePut("Caterers")
	@GetMapping("/id/{id}")
	public ResponseEntity<EntityModel<Optional<Caterer>>> getCatererById(@PathVariable("id") String id) {
		try {
			log.info("Get Caterer by ID::"+id);
			Optional<Caterer> caterer = catererService.getCatererById(id);
			EntityModel<Optional<Caterer>> _caterer = EntityModel.of(caterer);
			_caterer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CatererController.class).getCatererById(id)).withRel("getCatererById"));
			_caterer.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CatererController.class).getCatererByName(id)).withRel("getCatererByName"));
			
			return new ResponseEntity<>(_caterer, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	
	@CachePut("Caterers")
	@GetMapping("/cityName/{cityName}")
	public ResponseEntity<Map<String,Object>> getCatererByCityNamePageable(@PathVariable("cityName") String cityName,@RequestParam(defaultValue = "0") int page,
		      @RequestParam(defaultValue = "3") int size){
		try {
			List<Caterer> caterers = new ArrayList<Caterer>();
			Pageable paging = PageRequest.of(page, size);
			Page<Caterer> pageCaterers = catererService.getCatererByCityNamePageable(cityName, paging);
			
			caterers=pageCaterers.getContent();
			
			Map<String, Object> response = new HashMap<>();
			response.put("caterers", caterers);
		    response.put("currentPage", pageCaterers.getNumber());
		    response.put("totalItems", pageCaterers.getTotalElements());
		    response.put("totalPages", pageCaterers.getTotalPages());			
		    return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error occured while fetching Caterers by CityName::"+e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@CachePut("Caterers")
	@GetMapping("/all")
	public ResponseEntity<List<Caterer>> getAllCaterer() {
		try {
			List<Caterer> caterers = catererService.getAllCaterer();
			return new ResponseEntity<>(caterers, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error occured while fetching all Caterer::"+e);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}


	@PostMapping("/save")
	public ResponseEntity<Caterer> createCaterer(@Valid @RequestBody Caterer caterer) {
		try {
			log.info("Request Body::Save Caterer:::"+caterer.toString());
			Caterer _caterer = catererService.createCaterer(caterer);
			log.info("Response Body::Save Caterer:::"+_caterer.toString());
			return new ResponseEntity<>(_caterer, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Error occured while saving Caterer::"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Caterer> updateCaterer(@PathVariable("id") String id, @RequestBody Caterer caterer) {
		try {
			log.info("Id::Update Caterer:::"+id);
			log.info("Request Body::Update Caterer:::"+caterer.toString());
			Caterer _caterer = catererService.updateCaterer(id,caterer);
			if(_caterer != null) {
				log.info("Response Body::Update Caterer:::"+_caterer.toString());
				return new ResponseEntity<>(_caterer, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error occured while updating Caterer::"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCaterer(@PathVariable("id") String id) {
		try {
			if(catererService.deleteCaterer(id))
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error occured while deleting Caterer with ID::"+id+"::"+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteall")
	public ResponseEntity<HttpStatus> deleteAllCaterers() {
		try {
			if(catererService.deleteAllCaterers())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Error occured while deleting Caterers::"+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}

}
