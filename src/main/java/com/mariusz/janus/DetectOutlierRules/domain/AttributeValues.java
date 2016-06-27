package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttributeValues implements Serializable {

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
	
	
	
	public AttributeValues() {
		
	}
	

	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isFact() {
		return isFact;
	}
	public void setFact(boolean isFact) {
		this.isFact = isFact;
	}
	public boolean isGoal() {
		return isGoal;
	}
	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getAttributeOrder() {
		return attributeOrder;
	}
	public void setAttributeOrder(Integer attributeOrder) {
		this.attributeOrder = attributeOrder;
	}
	public boolean isConclusion() {
		return isConclusion;
	}
	public void setConclusion(boolean isConclusion) {
		this.isConclusion = isConclusion;
	}
	public String getContinousValue() {
		return continousValue;
	}
	public void setContinousValue(String continousValue) {
		this.continousValue = continousValue;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}

	
	
}
