package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private Integer id;
	@JsonProperty
	private String description;
	@JsonProperty
	private String internalDescription;
	@JsonProperty
	private boolean isSingeFact;
	@JsonProperty
	private KnowledgeBase knowledgeBase;
	@JsonProperty
	private String name;
	@JsonProperty
	private String query;
	@JsonProperty
	private String type;

	
	
	
	public Attribute() {
		
	}
	
	public Attribute(Integer id, String description, String internalDescription, boolean isSingeFact,
			KnowledgeBase knowledgeBase, String name, String query, String type) {
		super();
		this.id = id;
		this.description = description;
		this.internalDescription = internalDescription;
		this.isSingeFact = isSingeFact;
		this.knowledgeBase = knowledgeBase;
		this.name = name;
		this.query = query;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInternalDescription() {
		return internalDescription;
	}
	public void setInternalDescription(String internalDescription) {
		this.internalDescription = internalDescription;
	}
	public boolean isSingeFact() {
		return isSingeFact;
	}
	public void setSingeFact(boolean isSingeFact) {
		this.isSingeFact = isSingeFact;
	}
	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}
	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
