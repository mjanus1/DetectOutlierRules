package com.mariusz.janus.DetectOutlierRules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AttributeValues implements Comparable<AttributeValues> {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private Attribute attribute;
	@JsonProperty
	private Integer id;
	@JsonProperty
	private boolean isFact;
	@JsonProperty
	private boolean isConclusion;
	@JsonProperty
	private boolean isGoal;
	@JsonProperty
	private String operator;
	@JsonProperty
	private Integer attributeOrder;
	@JsonProperty
	private String continousValue;
	@JsonProperty
	private Value value;
	@Override
	
	public int compareTo(AttributeValues o) {
		if(o.isConclusion)
			return 1;
		return -1;
	}
	

}
