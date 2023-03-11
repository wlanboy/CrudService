package com.wlanboy.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_vorgang")
public class Vorgang {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vorgang other = (Vorgang) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
