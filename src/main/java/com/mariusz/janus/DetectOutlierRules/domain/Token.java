package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Token implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String access_token;
	@JsonProperty
	private String token_type;
	@JsonProperty
	private String refresh_token;
	@JsonProperty
	private int expires_in;
	@JsonProperty
	private String scope;

}
