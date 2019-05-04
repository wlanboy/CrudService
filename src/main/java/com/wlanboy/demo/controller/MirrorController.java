package com.wlanboy.demo.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wlanboy.demo.clients.MirrorClient;
import com.wlanboy.demo.model.HelloParameters;

@RestController
public class MirrorController {

	private static final Logger logger = Logger.getLogger(MirrorController.class.getCanonicalName());

	@Autowired
	MirrorClient mirrorClient;

	@RequestMapping(value = "/mirror", method = RequestMethod.POST)
	public HttpEntity<HelloParameters> hello(@RequestBody HelloParameters parameters) {

		parameters = mirrorClient.postMirrorRequest(parameters);
		logger.info("Vorgang created.");

		return new ResponseEntity<HelloParameters>(parameters, HttpStatus.CREATED);
	}
}
