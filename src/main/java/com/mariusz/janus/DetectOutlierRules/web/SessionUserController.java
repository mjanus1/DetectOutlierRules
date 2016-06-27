package com.mariusz.janus.DetectOutlierRules.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;


@ManagedBean
@SessionScoped
public class SessionUserController {

	private User user;
	private Token token;
	
	
	public SessionUserController(User user, Token token) {
		this.user = user;
		this.token = token;
	}	

	public SessionUserController() {
	
	}

	public String logoutAction()
	{
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index?faces-redirect=true";
	}
	
	public boolean checkIsLoggedUser()
	{
		if(user!=null)
			return true;
		else
			return false;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
}
