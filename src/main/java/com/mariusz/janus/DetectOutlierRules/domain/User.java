package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User implements Serializable
{	
	private static final long serialVersionUID = 1L;	
	@JsonProperty
	private String login;
	@JsonProperty
	private String password;
	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;
	@JsonProperty
	private String email;
	@JsonProperty
	private boolean activated;
	@JsonProperty
	private String langKey;
	@JsonProperty
	private String[] authorities;	  
}

