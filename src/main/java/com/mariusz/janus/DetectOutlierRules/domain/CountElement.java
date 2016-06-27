package com.mariusz.janus.DetectOutlierRules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountElement {

	@JsonProperty
	String count;

	public CountElement() {
		
	}

	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
}
