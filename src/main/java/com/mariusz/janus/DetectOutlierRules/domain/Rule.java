package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
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

}
