package com.mariusz.janus.DetectOutlierRules.web;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class AbstracUtility {
	
	public static void addMessageGlobal(String details,String summary)
	{
		
		FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO,summary, details);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}
	
	public static void addWarningGlobal(String details,String summary)
	{
		FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_WARN,summary,details);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public static void addErrorGlobal(String details,String summary)
	{
		FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,summary, details);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	

	// below message assigned to tag
	
	
	public static void addMessage(String clientID,String details,String summary)
	{
		
		FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO,summary, details);
		FacesContext.getCurrentInstance().addMessage(clientID, msg);
	}
	
	
	public static void addError(String clientID,String details,String summary)
	{
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,summary,details);
		FacesContext.getCurrentInstance().addMessage(clientID, msg);
	}
	
	public static String getParametr(String parametr)
	{
		FacesContext ctx=FacesContext.getCurrentInstance();
		ExternalContext etx=ctx.getExternalContext();
		Map<String,String> parameters=etx.getRequestParameterMap();
		return parameters.get(parametr);
	}
	
	

}
