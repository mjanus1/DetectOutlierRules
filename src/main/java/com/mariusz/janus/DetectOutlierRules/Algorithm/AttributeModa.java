package com.mariusz.janus.DetectOutlierRules.Algorithm;


import lombok.Getter;
import lombok.Setter;

public class AttributeModa {

	@Getter @Setter private AttributeDetails attributeDetails;
	@Getter @Setter private String value; 
	@Getter @Setter private int count;
	
	public AttributeModa(AttributeDetails attributeDetails, String value, int count) {
		this.attributeDetails = attributeDetails;
		this.value = value;
		this.count = count;
	}
	
	public AttributeModa(String value, int count) {
		this.value = value;
		this.count = count;
	}

	public AttributeModa() {}
}

