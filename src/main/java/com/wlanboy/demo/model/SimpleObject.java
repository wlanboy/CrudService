package com.wlanboy.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SimpleObject")
public class SimpleObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	public Long SIMPLE_ID;

	@Column(nullable = false, length = 60)
	@Getter
	@Setter
	public String SIMPLE_NUMBER;

	@Column(nullable = false, length = 60)
	@Getter
	@Setter
	public String SIMPLE_HASH;
}
