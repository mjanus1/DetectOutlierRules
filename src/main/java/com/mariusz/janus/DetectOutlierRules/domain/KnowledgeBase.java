package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class KnowledgeBase implements Serializable
{
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonUnwrapped
	private User owner;
  
	
	public KnowledgeBase() {
	
	}

	public KnowledgeBase(int id, String name, String description, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
	}

	public int getId()
	{
		return this.id;
	}
  
	public void setId(int id)
	{
		this.id = id;
	}
  
	public String getName()
	{
		return this.name;
	}
  
	public void setName(String name)
	{
		this.name = name;
	}
  
	public String getDescription()
	{
		return this.description;
	}
  
	public void setDescription(String description)
	{
		this.description = description;
	}
  
	public User getOwner()
	{
		return this.owner;
	}
  
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
}
