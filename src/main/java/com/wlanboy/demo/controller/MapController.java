package com.wlanboy.demo.controller;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wlanboy.demo.model.HelloParameterMap;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.service.MapsService;

@RestController
public class MapController {

	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@Autowired
	MapsService mapsdatenbank;

	@PostMapping(value = "/map")
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<HelloParameterMap> createMap(@RequestBody HelloParameterMap map) {

		HelloParameterMap dbMap = null;
		SimpleObject object = new SimpleObject();

		try {
			object.setSIMPLE_NUMBER(map.getValue());
			object.calculateHash();

			object = mapsdatenbank.saveObject(object);

		} catch (NoSuchAlgorithmException e) {
			logger.error("createMap Exception {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		if (object != null) {
			return ResponseEntity.notFound().build();
		} else {
			dbMap = new HelloParameterMap(object);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dbMap.getId())
					.toUri();
			logger.info("Map created {} ", dbMap);
			return ResponseEntity.created(uri).body(map);
		}

	}

	@GetMapping(value = "/map/{identifier}")
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<HelloParameterMap> mapById(@PathVariable(value = "identifier") Long identifier) {

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			logger.info("Map found ( {} )", identifier);

			HelloParameterMap parameters = new HelloParameterMap(suche);
			return ResponseEntity.ok(parameters);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping(value = "/map/{identifier}")
	@PreAuthorize("hasRole('MODOBJECT')")
	public ResponseEntity<HelloParameterMap> mapUpdate(@PathVariable(value = "identifier") Long identifier,
			@RequestBody HelloParameterMap parameters) {

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			logger.info("Map updated ( {} )", identifier);
			suche.setSIMPLE_NUMBER(parameters.getValue());
			suche = mapsdatenbank.saveObject(suche);

			HelloParameterMap updatedparameters = new HelloParameterMap(suche);
			return ResponseEntity.ok(updatedparameters);
		} else {
			logger.info("Map mot found ( {} )", identifier);
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping(value = "/map/{identifier}")
	@PreAuthorize("hasAnyRole('MODOBJECT')")
	public ResponseEntity<HelloParameterMap> mapDelete(@PathVariable(value = "identifier") Long identifier) {

		SimpleObject suche = mapsdatenbank.searchObjectById(identifier);
		if (suche != null) {
			mapsdatenbank.deleteSimpleObject(identifier);
			logger.info("Map deleted ( {} )", identifier);

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/map")
	@PreAuthorize("hasRole('SIMPLEOBJECT')")
	public ResponseEntity<List<HelloParameterMap>> mapAll() {

		Iterable<SimpleObject> iterable = mapsdatenbank.findAllObjects();
		List<HelloParameterMap> list = new ArrayList<>();

		iterable.forEach((v) -> {
			HelloParameterMap foundparameters = new HelloParameterMap(v);
			list.add(foundparameters);
		});

		logger.info("Maps loaded ( {} )",list.size());

		return ResponseEntity.ok(list);
	}

}
