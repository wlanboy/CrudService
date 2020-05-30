package com.wlanboy.demo.model;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.hash.Hashing;

@Entity
@Table(name = "SimpleObject")
public class SimpleObject {

	public Long getSIMPLE_ID() {
		return SIMPLE_ID;
	}

	public void setSIMPLE_ID(Long sIMPLE_ID) {
		SIMPLE_ID = sIMPLE_ID;
	}

	public String getSIMPLE_NUMBER() {
		return SIMPLE_NUMBER;
	}

	public void setSIMPLE_NUMBER(String sIMPLE_NUMBER) {
		SIMPLE_NUMBER = sIMPLE_NUMBER;
	}

	public String getSIMPLE_HASH() {
		return SIMPLE_HASH;
	}

	public void setSIMPLE_HASH(String sIMPLE_HASH) {
		SIMPLE_HASH = sIMPLE_HASH;
	}

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

	public void calculateHash() throws NoSuchAlgorithmException {
		if (this.SIMPLE_NUMBER == null)
			this.SIMPLE_NUMBER = "";

		this.SIMPLE_HASH = Hashing.sha256().hashString(this.SIMPLE_NUMBER, StandardCharsets.UTF_8).toString();
		;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SIMPLE_HASH == null) ? 0 : SIMPLE_HASH.hashCode());
		result = prime * result + ((SIMPLE_ID == null) ? 0 : SIMPLE_ID.hashCode());
		result = prime * result + ((SIMPLE_NUMBER == null) ? 0 : SIMPLE_NUMBER.hashCode());
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
		SimpleObject other = (SimpleObject) obj;
		if (SIMPLE_HASH == null) {
			if (other.SIMPLE_HASH != null)
				return false;
		} else if (!SIMPLE_HASH.equals(other.SIMPLE_HASH))
			return false;
		if (SIMPLE_ID == null) {
			if (other.SIMPLE_ID != null)
				return false;
		} else if (!SIMPLE_ID.equals(other.SIMPLE_ID))
			return false;
		if (SIMPLE_NUMBER == null) {
			if (other.SIMPLE_NUMBER != null)
				return false;
		} else if (!SIMPLE_NUMBER.equals(other.SIMPLE_NUMBER))
			return false;
		return true;
	}
	
}
