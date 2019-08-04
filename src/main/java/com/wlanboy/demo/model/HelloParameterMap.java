package com.wlanboy.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloParameterMap {

	private Long id;
	private String value;
	private String hash;
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public HelloParameterMap(SimpleObject object) {
		this.id = object.getSIMPLE_ID();
		this.value = object.getSIMPLE_NUMBER();
		this.hash = object.getSIMPLE_HASH();
	}

	public HelloParameterMap(long identifier, String value, String hash) {
		this.id = identifier;
		this.value = value;
		this.hash = hash;
	}

	public HelloParameterMap(String value, String hash) {
		this.value = value;
		this.hash = hash;
	}
	
	public HelloParameterMap(String value) {
		this.value = value;
	}
	
	public HelloParameterMap() {

	}	
}
