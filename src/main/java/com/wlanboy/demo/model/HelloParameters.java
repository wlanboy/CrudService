package com.wlanboy.demo.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloParameters extends ResourceSupport {

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
