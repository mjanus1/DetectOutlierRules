package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


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
	
	public String getAccess_token()
	{
	  return this.access_token;
	}
	
	public void setAccess_token(String access_token)
	{
	  this.access_token = access_token;
	}
	
	public String getToken_type()
	{
	  return this.token_type;
	}
	
	public void setToken_type(String token_type)
	{
	  this.token_type = token_type;
	}
	
	public String getRefresh_token()
	{
	  return this.refresh_token;
	}
	
	public void setRefresh_token(String refresh_token)
	{
	  this.refresh_token = refresh_token;
	}
	
	public int getExpires_in()
	{
	  return this.expires_in;
	}
	
	public void setExpires_in(int expires_in)
	{
	  this.expires_in = expires_in;
	}
	
	public String getScope()
	{
	  return this.scope;
	}
	
	public void setScope(String scope)
	{
	  this.scope = scope;
	}
}
