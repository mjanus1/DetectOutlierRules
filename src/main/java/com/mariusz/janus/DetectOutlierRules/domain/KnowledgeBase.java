package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
public class KnowledgeBase implements Serializable
{
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonUnwrapped
	private User owner;
  
}
