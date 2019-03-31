package com.wlanboy.demo.controller;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wlanboy.demo.model.HelloParameterMap;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.service.MapsService;

@RestController
public class MapController {

	private static final Logger logger = Logger.getLogger(MapController.class.getCanonicalName());

	@Autowired
	MapsService mapsdatenbank;

	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<HelloParameterMap> createMap(@RequestBody HelloParameterMap map) {

		HelloParameterMap dbMap = null;
		SimpleObject object = new SimpleObject();

		try {
			object.setSIMPLE_NUMBER(map.getValue());
			object.calculateHash();

			object = mapsdatenbank.SaveObject(object);

		} catch (NoSuchAlgorithmException e) {
			logger.warning(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		if (object != null) {
			return ResponseEntity.notFound().build();
		} else {
			dbMap = new HelloParameterMap(object);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dbMap.getId())
					.toUri();
			logger.info("Map created. " + dbMap);
			return ResponseEntity.created(uri).body(map);
		}

	}

	@RequestMapping(value = "/map/{identifier}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<HelloParameterMap> mapById(@PathVariable(value = "identifier") Long identifier) {

		logger.info("ID: (" + identifier + ").");

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			logger.info("Map found (" + identifier + ").");

			HelloParameterMap parameters = new HelloParameterMap(suche);
			return ResponseEntity.ok(parameters);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/map/{identifier}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('MODOBJECT')")
	public ResponseEntity<HelloParameterMap> mapUpdate(@PathVariable(value = "identifier") Long identifier,
			@RequestBody HelloParameterMap parameters) {

		logger.info("ID: (" + identifier + ").");

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			logger.info("Map found (" + identifier + ").");
			suche.setSIMPLE_NUMBER(parameters.getValue());
			suche = mapsdatenbank.SaveObject(suche);

			HelloParameterMap updatedparameters = new HelloParameterMap(suche);
			return ResponseEntity.ok(updatedparameters);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/map/{identifier}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('MODOBJECT')")
	public ResponseEntity<HelloParameterMap> mapDelete(@PathVariable(value = "identifier") Long identifier) {

		logger.info("ID: (" + identifier + ").");

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			mapsdatenbank.deleteSimpleObject(identifier);
			logger.info("Map deleted with (" + identifier + ").");

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<List<HelloParameterMap>> mapAll() {

		Iterable<SimpleObject> iterable = mapsdatenbank.findAllObjects();
		List<HelloParameterMap> list = new ArrayList<HelloParameterMap>();

		iterable.forEach((v) -> {
			HelloParameterMap foundparameters = new HelloParameterMap(v);
			list.add(foundparameters);
		});

		logger.info("Map loaded (" + list.size() + ").");

		return ResponseEntity.ok(list);
	}

}
