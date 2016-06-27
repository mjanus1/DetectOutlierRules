package com.mariusz.janus.DetectOutlierRules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {

	@JsonProperty
	private Integer id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	
	public Value() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
