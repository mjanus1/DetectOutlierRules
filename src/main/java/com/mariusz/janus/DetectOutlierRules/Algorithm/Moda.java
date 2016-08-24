package com.mariusz.janus.DetectOutlierRules.Algorithm;


import lombok.Getter;
import lombok.Setter;

public class Moda {

	@Getter @Setter private AttributeDetails attributeDetails;
	@Getter @Setter private String value; 
	@Getter @Setter private int count;
	
	public Moda(AttributeDetails attributeDetails, String value, int count) {
		this.attributeDetails = attributeDetails;
		this.value = value;
		this.count = count;
	}
	
	public Moda(String value, int count) {
		this.value = value;
		this.count = count;
	}
}

