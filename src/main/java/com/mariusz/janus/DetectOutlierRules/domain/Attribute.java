package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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
	@JsonProperty
	private List<Value> valueList;
	
	
}
