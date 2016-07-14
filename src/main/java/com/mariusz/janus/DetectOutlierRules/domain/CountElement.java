package com.mariusz.janus.DetectOutlierRules.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CountElement {

	@JsonProperty
	private String count;

}
