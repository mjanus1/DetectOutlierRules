package com.mariusz.janus.DetectOutlierRules.web;

import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstracController extends AbstractMessageHandling {

	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter
	private SessionUserController session;
	
	public String tokenForRequest(){
		return session.getAccesToken();
	};
	
	public static int getParametr(String parametr) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		Map<String, String> parameters = etx.getRequestParameterMap();
		return Integer.parseInt(parameters.get(parametr));
	}

	
}
