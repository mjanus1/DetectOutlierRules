package com.mariusz.janus.DetectOutlierRules.web;

import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Token;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstracController extends AbstractMessageHandling {

	private static final Logger logger = LoggerFactory.getLogger(AbstracController.class);
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter
	private SessionUserController session;
	
	public Token tokenForRequest(){
		return session.getToken();
	};
	
	public static int getParametr(String parametr) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext etx = ctx.getExternalContext();
		Map<String, String> parameters = etx.getRequestParameterMap();
		String param = parameters.get(parametr);
		if(param == null || param.isEmpty()) {
			System.out.println("parametr nulem");
			
		} else {
			try{
				return Integer.parseInt(parameters.get(parametr));
			} catch(NumberFormatException e){logger.error("Błąd podczas formatu liczby", e);}	
		}
		return 5;
	}
	
	
}
