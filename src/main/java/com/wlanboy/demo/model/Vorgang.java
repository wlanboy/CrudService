package com.wlanboy.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_vorgang")
public class Vorgang {

	public Vorgang() {};
	
	public Vorgang(long id, String name, String status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	public Vorgang(String name, String status) {
		this.name = name;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String status;

	//@ElementCollection(fetch = FetchType.EAGER)
	//@CollectionTable(name = "tbl_vorgang_map",joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
	//@MapKeyColumn(name = "KEY")
	//@Column(name = "VALUE")
	//Map<String,String> map;
	//@OneToMany(cascade = {CascadeType.ALL}, targetEntity = SimpleObject.class)
	//@JoinColumn(name = "vorgang_id")
	//List<SimpleObject> map;
}
