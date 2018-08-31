package com.wlanboy.demo.model;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.MapKeyColumn;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HelloParameters extends ResourceSupport {

	private Long identifier;
	private String target;
	private String status;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_vorgang_map")
	@MapKeyColumn(name = "KEY")
	@Column(name = "VALUE")
	Map<String, String> map;

	public HelloParameters(Vorgang vorgang) {
		this.identifier = vorgang.getId();
		this.target = vorgang.getName();
		this.status = vorgang.getStatus();
	}

}
