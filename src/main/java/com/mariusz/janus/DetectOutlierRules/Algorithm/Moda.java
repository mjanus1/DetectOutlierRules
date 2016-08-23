package com.mariusz.janus.DetectOutlierRules.Algorithm;


import com.mariusz.janus.DetectOutlierRules.domain.Attribute;

import lombok.Getter;
import lombok.Setter;

public class Moda {

	@Getter @Setter private Attribute attributes;
	@Getter @Setter private String value; 
	@Getter @Setter private int count;
	
	public Moda(Attribute attributes, String value, int count) {
		this.attributes = attributes;
		this.value = value;
		this.count = count;
	}
	
	public Moda(String value, int count) {
		this.value = value;
		this.count = count;
	}
}

