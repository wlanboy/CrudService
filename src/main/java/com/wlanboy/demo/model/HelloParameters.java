package com.wlanboy.demo.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloParameters extends ResourceSupport {

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

	private Long identifier;
	private String target;
	private String status;

	public HelloParameters(Vorgang vorgang) {
		this.identifier = vorgang.getId();
		this.target = vorgang.getName();
		this.status = vorgang.getStatus();
	}

	public HelloParameters(long identifier, String target, String status) {
		this.identifier = identifier;
		this.target = target;
		this.status = status;
	}

	public HelloParameters(String target, String status) {
		this.target = target;
		this.status = status;
	}
	
	public HelloParameters() {

	}

	@Override
	public String toString() {
		return "HelloParameters [identifier=" + identifier + ", target=" + target + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HelloParameters other = (HelloParameters) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
