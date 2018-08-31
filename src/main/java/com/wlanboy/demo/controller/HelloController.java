package com.wlanboy.demo.controller;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

import com.wlanboy.demo.model.HelloParameters;
import com.wlanboy.demo.model.Vorgang;
import com.wlanboy.demo.repository.VorgangRepository;

@RestController
public class HelloController {

    private static final Logger logger = Logger
            .getLogger(HelloController.class.getCanonicalName());

    @Autowired
    VorgangRepository vorgangsdatenbank;
    
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public HttpEntity<HelloParameters> hello(@RequestBody HelloParameters parameters) {
    	
		Vorgang vorgang = new Vorgang();
		vorgang.setName(parameters.getTarget());
		vorgang.setStatus(parameters.getStatus());
		
		vorgang = vorgangsdatenbank.save(vorgang);
    	
    	parameters = new HelloParameters(vorgang);
    	parameters.add(linkTo(methodOn(HelloController.class).hellogetbyid(parameters.getIdentifier())).withSelfRel());
        logger.info("Vorgang created.");
        return new ResponseEntity<HelloParameters>(parameters, HttpStatus.OK);
    }   
    
    @RequestMapping(value = "/hello/{identifier}", method = RequestMethod.GET)
    public HttpEntity<HelloParameters> hellogetbyid(@PathVariable(value="identifier") Long identifier) {
    	
    	logger.info("ID: ("+identifier+").");
    	
    	Vorgang suche = vorgangsdatenbank.findById(identifier).orElse(null);
    	if (suche != null)
    	{
    		logger.info("Vorgang found ("+identifier+").");
    		
    		HelloParameters parameters = new HelloParameters(suche);
    		parameters.add(linkTo(methodOn(HelloController.class).hellogetbyid(parameters.getIdentifier())).withSelfRel());
    		return new ResponseEntity<HelloParameters>(parameters, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }  
    
    @RequestMapping(value = "/hello/{identifier}", method = RequestMethod.POST)
    public HttpEntity<HelloParameters> helloupdateid(@PathVariable(value="identifier") Long identifier, @RequestBody HelloParameters parameters) {
    	
    	logger.info("ID: ("+identifier+").");
    	
    	Vorgang suche = vorgangsdatenbank.findById(identifier).orElse(null);
    	if (suche != null)
    	{
    		logger.info("Vorgang found ("+identifier+").");
    		suche.setName(parameters.getTarget());
    		suche.setStatus(parameters.getStatus());
    		suche = vorgangsdatenbank.save(suche);
    		
    		HelloParameters updatedparameters = new HelloParameters(suche);
    		updatedparameters.add(linkTo(methodOn(HelloController.class).hellogetbyid(updatedparameters.getIdentifier())).withSelfRel());
    		return new ResponseEntity<HelloParameters>(updatedparameters, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }     
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public HttpEntity<List<HelloParameters>> helloall() {
    	
    	Iterable<Vorgang> iterable = vorgangsdatenbank.findAll();
        List<HelloParameters> list = new ArrayList<HelloParameters>();

        iterable.forEach((v)->  {
        	HelloParameters foundparameters = new HelloParameters(v); 
        	foundparameters.add(linkTo(methodOn(HelloController.class).hellogetbyid(foundparameters.getIdentifier())).withSelfRel()); 
        	list.add(foundparameters);
        });
        
        logger.info("Vorgang loaded ("+list.size()+").");
        
		return new ResponseEntity<List<HelloParameters>>(list, HttpStatus.OK);
    }   

}
