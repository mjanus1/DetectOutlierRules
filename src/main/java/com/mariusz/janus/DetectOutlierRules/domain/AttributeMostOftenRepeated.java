package com.mariusz.janus.DetectOutlierRules.domain;


import lombok.Getter;
import lombok.Setter;

public class AttributeMostOftenRepeated {

	@Getter @Setter private AttributeAdditionDetail attributeDetails;
	@Getter @Setter private String value; 
	@Getter @Setter private int count;
	
	public AttributeMostOftenRepeated(AttributeAdditionDetail attributeDetails, String value, int count) {
		this.attributeDetails = attributeDetails;
		this.value = value;
		this.count = count;
	}
	
	public AttributeMostOftenRepeated(String value, int count) {
		this.value = value;
		this.count = count;
	}

	public AttributeMostOftenRepeated() {}
}

