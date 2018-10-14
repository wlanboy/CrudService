package com.wlanboy.demo.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<HelloParameterMap> map;

	public HelloParameters(Vorgang vorgang) {
		this.identifier = vorgang.getId();
		this.target = vorgang.getName();
		this.status = vorgang.getStatus();
		this.map = vorgang.getMap();
	}

}
