package com.mariusz.janus.DetectOutlierRules.domain;

import java.io.Serializable;

public class User implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private boolean activated;
	private String langKey;
	private String[] authorities;
	  
	public String getLogin()
	{
		return this.login;
	}
	  
	public void setLogin(String login)
	{
	    this.login = login;
	}
	  
	public String getPassword()
	{
	    return this.password;
	}
	  
	public void setPassword(String password)
	{
	    this.password = password;
    }
	  
	public String getFirstName()
	{
	    return this.firstName;
	}
	  
	public void setFirstName(String firstName)
	{
	    this.firstName = firstName;
	}
	  
	public String getLastName()
	{
	    return this.lastName;
	}
	  
	public void setLastName(String lastName)
	{
	    this.lastName = lastName;
	}
	  
	public String getEmail()
	{
	    return this.email;
	}
	  
	public void setEmail(String email)
	{
	    this.email = email;
	}
	  
	public boolean isActivated()
	{
	    return this.activated;
	}
	  
	public void setActivated(boolean activated)
	{
	    this.activated = activated;
	}
	  
	public String getLangKey()
	{
	    return this.langKey;
	}
	  
	public void setLangKey(String langKey)
	{
	    this.langKey = langKey;
	}
	  
	public String[] getAuthorities()
	{
	    return this.authorities;
	}
	  
	public void setAuthorities(String[] authorities)
	{
	    this.authorities = authorities;
	}
}

