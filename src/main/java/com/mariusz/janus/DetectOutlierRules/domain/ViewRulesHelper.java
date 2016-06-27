package com.mariusz.janus.DetectOutlierRules.domain;

public class ViewRulesHelper {

	private Integer id;
	private String description;
	private StringBuilder query;
	
	public ViewRulesHelper() {
	
	}
	
	public ViewRulesHelper(Integer id, String description, StringBuilder query) {
		super();
		this.id = id;
		this.description = description;
		this.query = query;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StringBuilder getQuery() {
		return query;
	}
	public void setQuery(StringBuilder query) {
		this.query = query;
	}
	
	

}
