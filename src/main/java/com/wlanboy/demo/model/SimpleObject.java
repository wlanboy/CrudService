package com.wlanboy.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SimpleObject")
public class SimpleObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long SIMPLE_ID;

	public String SIMPLE_NUMBER;
	public String SIMPLE_HASH;
	
	public SimpleObject(long id, String number, String hash) {
		this.SIMPLE_ID = id;
		this.SIMPLE_NUMBER = number;
		this.SIMPLE_HASH = hash;
	}
	
	public SimpleObject(String number, String hash) {
		this.SIMPLE_NUMBER = number;
		this.SIMPLE_HASH = hash;
	}

	public SimpleObject() {
	}
}
