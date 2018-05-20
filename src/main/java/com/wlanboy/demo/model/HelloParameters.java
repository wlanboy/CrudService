package com.wlanboy.demo.model;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;

import org.springframework.hateoas.ResourceSupport;

public class HelloParameters extends ResourceSupport {
	private Long identifier;
	private String target;
	private String status;
	
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "tbl_vorgang_map")
    @MapKeyColumn(name = "KEY")
    @Column(name = "VALUE")
    Map<String,String> map;
    
	public HelloParameters() {
		
	}

	public HelloParameters(Vorgang vorgang) {
		this.identifier = vorgang.getId();
		this.target = vorgang.getName();
		this.status = vorgang.getStatus();
		
	}

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

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "HelloParameters [identifier=" + identifier + ", target=" + target + ", status=" + status + "]";
	}
	
}
