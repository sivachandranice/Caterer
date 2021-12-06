package com.catering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.catering.model.Caterer;
import com.catering.msgsender.MessageSender;
import com.catering.repository.CatererRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CatererService {
	
	@Autowired
	CatererRepository catererRepository;
	
	
	
	public List<Caterer> getCatererByName(String name) throws Exception{

		try {
			return catererRepository.findByName(name);
		} catch (Exception e) {
			log.error("Exception occured:: Getting Caterer by Name::"+e);
			throw new Exception(e);
		}	
	}
	
	public Optional<Caterer> getCatererById(String id) throws Exception{

		try {
			return catererRepository.findById(id);
		} catch (Exception e) {
			log.error("Exception occured:: Getting Caterer by ID::"+e);
			throw new Exception(e);
		}	
	}
	
	public List<Caterer> getAllCaterer() throws Exception{

		try {
			return catererRepository.findAll();
		} catch (Exception e) {
			log.error("Exception occured:: Getting All Caterer::"+e);
			throw new Exception(e);
		}	
	}
	
	public Caterer createCaterer(Caterer caterer) throws Exception{

		try {
			Caterer _caterer = catererRepository.save(caterer);
			MessageSender msgSender = new MessageSender();
			try {
				msgSender.sendMessageToDestination(caterer.toString(), "", false);
			}
			catch(Exception ex) {
				log.error("Topic send error::::"+ex);
			}
			return _caterer;
		} catch (Exception e) {
			log.error("Exception occured:: Saving Caterer::"+caterer.getName()+"::"+e);
			throw new Exception(e);
		}	
	}
	
	public Caterer updateCaterer(String id, Caterer caterer) throws Exception{

		try {
			Optional<Caterer> catererData = catererRepository.findById(id);
			if (catererData.isPresent()) {
				Caterer _caterer = catererData.get();
				_caterer.setName(caterer.getName());
				_caterer.setLocation(caterer.getLocation());
				_caterer.setCapacity(caterer.getCapacity());
				_caterer.setContactInfo(caterer.getContactInfo());
				return catererRepository.save(_caterer);
			}
			else {
				log.info("No record to update for Caterer ID::"+id);
				return null;
			}
			
		} catch (Exception e) {
			log.error("Exception occured:: Saving Caterer::"+caterer.getName()+"::"+e);
			throw new Exception(e);
		}	
	}
	
	public boolean deleteCaterer(String id) throws Exception{

		try {
			catererRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("Exception occured:: Getting Caterer by ID::"+e);
			throw new Exception(e);
		}	
	}
	
	public boolean deleteAllCaterers()throws Exception{

		try {
			catererRepository.deleteAll();
			return true;
		} catch (Exception e) {
			log.error("Exception occured:: Getting Caterer by ID::"+e);
			throw new Exception(e);
		}	
	}
	
	public Page<Caterer> getCatererByCityNamePageable(@PathVariable("cityName") String cityName, Pageable pageable) throws Exception{
		try {
			log.info("Caterers by City name::"+catererRepository.findByCityNameIgnoringCase(cityName,pageable));
			return catererRepository.findByCityNameIgnoringCase(cityName,pageable);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occured:: Getting Caterer by CityName::"+e);
			throw new Exception(e);
		}
	}
	



}
