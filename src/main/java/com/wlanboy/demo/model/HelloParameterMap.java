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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		HelloParameterMap other = (HelloParameterMap) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	
	
}
