package com.mariusz.janus.DetectOutlierRules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Fact {

	@JsonProperty
	private Integer id;
	@JsonProperty
	private Attribute attribute;
	@JsonProperty
	private String continousValue;
	@JsonProperty
	private KnowledgeBase knowledgeBase;
	@JsonProperty
	private String operator;
	@JsonProperty
	private Value value;
}
