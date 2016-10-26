package com.mariusz.janus.DetectOutlierRules.web;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@SessionScoped
public class SessionUserController {

	@Getter @Setter
	private User user;
	@Setter
	private Token token;

	public SessionUserController(User user, Token token) {
		this.user = user;
		this.token = token;
	}

	public SessionUserController() {
	}
	
	public void logoutAction() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + "/index.xhtml");
		} catch (IOException e) {
			
		}
	}
	
	public void removeSession() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public boolean checkIsLoggedUser() {
		if (user != null)
			return true;
		else
			return false;
	}

	public Token getToken() {
		return token;
	}
}
