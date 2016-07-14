package com.mariusz.janus.DetectOutlierRules.domain;

import lombok.Data;

@Data
public class ViewRulesHelper {
	
	private Integer id;
	private String description;
	private StringBuilder query;
	
	public ViewRulesHelper(Integer id, String description, StringBuilder query) {
		super();
		this.id = id;
		this.description = description;
		this.query = query;
	}
	
}
