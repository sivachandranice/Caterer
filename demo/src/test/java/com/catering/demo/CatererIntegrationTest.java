package com.catering.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.catering.model.Caterer;
import com.catering.model.CatererCapacity;
import com.catering.model.CatererContactInfo;
import com.catering.model.CatererLocation;
import com.catering.repository.CatererRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatererIntegrationTest {
	
	@Autowired
    private MockMvc mvc;
 
	@Mock
    private CatererRepository catererRepository;
    
    @BeforeAll
    public void setup(){
 
		Caterer caterer = new Caterer();
		CatererCapacity capacity = new CatererCapacity();
		CatererContactInfo contactInfo = new CatererContactInfo();
		CatererLocation location = new CatererLocation();
		
		
		String id = "012345";
		
		caterer.setId(id);
		caterer.setName("Testing");
		
		capacity.setMinGuestOccupancy(10);
		capacity.setMaxGuestOccupancy(100);
		
		contactInfo.setEmailAddress("siva@abc.com");
		contactInfo.setMobileNumber("123456");
		
		location.setAddress("Upper Changi Road East");
		location.setCityName("Singapore");
		location.setPostalCode(123456);		
		
		caterer.setCapacity(capacity);
		caterer.setLocation(location);
		caterer.setContactInfo(contactInfo);
		
		catererRepository.save(caterer);
		log.info("Input::::"+caterer.toString());
    }
    
    
    @Test
    public void test_getByFirstName_successfull() throws Exception {
    	
        mvc.perform(get("/name/Shiva")).andExpect(status().isOk());
 
    }

}
