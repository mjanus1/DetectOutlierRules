package com.mariusz.janus.DetectOutlierRules.domain;


import lombok.Getter;
import lombok.Setter;

public class DominantAttributes {

	@Getter @Setter private AttributeDetails attributeDetails;
	@Getter @Setter private String value; 
	@Getter @Setter private int count;
	
	public DominantAttributes(AttributeDetails attributeDetails, String value, int count) {
		this.attributeDetails = attributeDetails;
		this.value = value;
		this.count = count;
	}
	
	public DominantAttributes(String value, int count) {
		this.value = value;
		this.count = count;
	}

	public DominantAttributes() {}
}
