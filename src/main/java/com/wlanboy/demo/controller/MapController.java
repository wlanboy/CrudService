package com.wlanboy.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wlanboy.demo.model.HelloParameterMap;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.service.MapsService;

@RestController
public class MapController {

    private static final Logger logger = Logger
            .getLogger(MapController.class.getCanonicalName());

    @Autowired
    MapsService mapsdatenbank;
    
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public HttpEntity<HelloParameterMap> hello(@RequestBody HelloParameterMap map) {
    	
		SimpleObject object = new SimpleObject();
		object.setSIMPLE_NUMBER(map.getValue());
		
		HttpStatus status = HttpStatus.CREATED;
		
    	SimpleObject suche = mapsdatenbank.searchSimpleObjectByNameOrValue(object);
    	if (suche != null) {
    		status = HttpStatus.CONFLICT;
    	}
    	else {
    		SimpleObject data = mapsdatenbank.SaveObject(object);
        	
        	map = new HelloParameterMap(data);
            logger.info("Map created.");
    	}
    	
    	return new ResponseEntity<HelloParameterMap>(map, status);
    }   
    
    @RequestMapping(value = "/map/{identifier}", method = RequestMethod.GET)
    public HttpEntity<HelloParameterMap> objectgetbyid(@PathVariable(value="identifier") Long identifier) {
    	
    	logger.info("ID: ("+identifier+").");
    	
    	SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
    	if (suche != null)
    	{
    		logger.info("Map found ("+identifier+").");
    		
    		HelloParameterMap parameters = new HelloParameterMap(suche);
    		return new ResponseEntity<HelloParameterMap>(parameters, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }  
    
    @RequestMapping(value = "/map/{identifier}", method = RequestMethod.PUT)
    public HttpEntity<HelloParameterMap> helloupdateid(@PathVariable(value="identifier") Long identifier, @RequestBody HelloParameterMap parameters) {
    	
    	logger.info("ID: ("+identifier+").");
    	
    	SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
    	if (suche != null)
    	{
    		logger.info("Map found ("+identifier+").");
    		suche.setSIMPLE_NUMBER(parameters.getValue());
    		suche = mapsdatenbank.SaveObject(suche);
    		
    		HelloParameterMap updatedparameters = new HelloParameterMap(suche);
    		return new ResponseEntity<HelloParameterMap>(updatedparameters, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }
    
    @RequestMapping(value = "/map/{identifier}", method = RequestMethod.DELETE)
    public HttpEntity<HelloParameterMap> hellodeleteid(@PathVariable(value="identifier") Long identifier) {
    	
    	logger.info("ID: ("+identifier+").");
    	
    	SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
    	if (suche != null)
    	{
    		mapsdatenbank.deleteSimpleObject(identifier);
    		logger.info("Map deleted with ("+identifier+").");

    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }     
    
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public HttpEntity<List<HelloParameterMap>> helloall() {
    	
    	Iterable<SimpleObject> iterable = mapsdatenbank.findAllObjects();
        List<HelloParameterMap> list = new ArrayList<HelloParameterMap>();

        iterable.forEach((v)->  {
        	HelloParameterMap foundparameters = new HelloParameterMap(v); 
        	list.add(foundparameters);
        });
        
        logger.info("Map loaded ("+list.size()+").");
        
		return new ResponseEntity<List<HelloParameterMap>>(list, HttpStatus.OK);
    }   

}
