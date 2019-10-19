package com.wlanboy.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wlanboy.demo.clients.MirrorClient;
import com.wlanboy.demo.model.HelloParameters;

@RestController
public class MirrorController {

	private static final Logger logger = LoggerFactory.getLogger(MirrorController.class);

	@Autowired
	MirrorClient mirrorClient;

	@PostMapping(value = "/mirror")
	public HttpEntity<HelloParameters> hello(@RequestBody HelloParameters parameters) {

		parameters = mirrorClient.postMirrorRequest(parameters);
		logger.info("Mirror Request {}", parameters);

		return new ResponseEntity<>(parameters, HttpStatus.CREATED);
	}
}
