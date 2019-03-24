package com.wlanboy.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloParameterMap {

	private Long id;
	private String value;
	private String hash;
	
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
