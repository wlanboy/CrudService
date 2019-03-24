package com.wlanboy.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloParameters extends ResourceSupport {

	private Long identifier;
	private String target;
	private String status;

	public HelloParameters(Vorgang vorgang) {
		this.identifier = vorgang.getId();
		this.target = vorgang.getName();
		this.status = vorgang.getStatus();
	}

	public HelloParameters(long identifier, String target, String status) {
		this.identifier = identifier;
		this.target = target;
		this.status = status;
	}

	public HelloParameters(String target, String status) {
		this.target = target;
		this.status = status;
	}
	
	public HelloParameters() {

	}

}
