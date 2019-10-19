package com.wlanboy.demo.controller;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wlanboy.demo.model.HelloParameters;
import com.wlanboy.demo.model.Vorgang;
import com.wlanboy.demo.service.VorgangsService;

@RestController
public class HelloController {

	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	VorgangsService vorgangsdatenbank;

	@PostMapping(value = "/hello")
	public HttpEntity<HelloParameters> hello(@RequestBody HelloParameters parameters) {

		Vorgang vorgang = new Vorgang();
		vorgang.setName(parameters.getTarget());
		vorgang.setStatus(parameters.getStatus());

		HttpStatus status = HttpStatus.CREATED;
		Vorgang suche = vorgangsdatenbank.searchVorgangByNameAndStatus(vorgang);
		if (suche != null) {
			status = HttpStatus.CONFLICT;
		} else {
			vorgang = vorgangsdatenbank.SaveVorgang(vorgang);

			parameters = new HelloParameters(vorgang);
			parameters.add(
					linkTo(methodOn(HelloController.class).hellogetbyid(parameters.getIdentifier())).withSelfRel());
			logger.info("Vorgang created.");
		}

		return new ResponseEntity<>(parameters, status);
	}

	@GetMapping(value = "/hello/{identifier}")
	public HttpEntity<HelloParameters> hellogetbyid(@PathVariable(value = "identifier") Long identifier) {

		Vorgang suche = vorgangsdatenbank.searchVorgangById(identifier);
		if (suche != null) {
			logger.info("Vorgang found ( {})",identifier);

			HelloParameters parameters = new HelloParameters(suche);
			parameters.add(
					linkTo(methodOn(HelloController.class).hellogetbyid(parameters.getIdentifier())).withSelfRel());
			return new ResponseEntity<>(parameters, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/hello/{identifier}", method = RequestMethod.PUT)
	public HttpEntity<HelloParameters> helloupdateid(@PathVariable(value = "identifier") Long identifier,
			@RequestBody HelloParameters parameters) {

		Vorgang suche = vorgangsdatenbank.searchVorgangById(identifier);
		if (suche != null) {
			logger.info("Vorgang found ( {})",identifier);
			
			suche.setName(parameters.getTarget());
			suche.setStatus(parameters.getStatus());
			suche = vorgangsdatenbank.SaveVorgang(suche);

			HelloParameters updatedparameters = new HelloParameters(suche);
			updatedparameters
					.add(linkTo(methodOn(HelloController.class).hellogetbyid(updatedparameters.getIdentifier()))
							.withSelfRel());
			return new ResponseEntity<>(updatedparameters, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(value = "/hello/{identifier}")
	public HttpEntity<HelloParameters> hellodeleteid(@PathVariable(value = "identifier") Long identifier) {

		Vorgang suche = vorgangsdatenbank.searchVorgangById(identifier);
		if (suche != null) {
			vorgangsdatenbank.deleteVorgang(identifier);
			logger.info("Vorgang found ( {})",identifier);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			logger.info("Vorgang not found ( {})",identifier);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/hello")
	public HttpEntity<List<HelloParameters>> helloall() {

		Iterable<Vorgang> iterable = vorgangsdatenbank.findAllVorgaenge();
		List<HelloParameters> list = new ArrayList<>();

		iterable.forEach((v) -> {
			HelloParameters foundparameters = new HelloParameters(v);
			foundparameters.add(linkTo(methodOn(HelloController.class).hellogetbyid(foundparameters.getIdentifier()))
					.withSelfRel());
			list.add(foundparameters);
		});

		logger.info("Vorgang loaded ( {} )",list.size());

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
