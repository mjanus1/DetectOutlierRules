package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String description;
	
	@JsonProperty
	private KnowledgeBase knowledgeBase;

	@JsonProperty
	private List<AttributeValues> attributeValues;
	
	public Rule() {
		
	}
	

	public Rule(Integer id, String description, KnowledgeBase knowledgeBase, List<AttributeValues> attributeValues) {
		super();
		this.id = id;
		this.description = description;
		this.knowledgeBase = knowledgeBase;
		this.attributeValues = attributeValues;
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

	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}


	public List<AttributeValues> getAttributeValues() {
		return attributeValues;
	}


	public void setAttributeValues(List<AttributeValues> attributeValues) {
		this.attributeValues = attributeValues;
	}

	
	
}
