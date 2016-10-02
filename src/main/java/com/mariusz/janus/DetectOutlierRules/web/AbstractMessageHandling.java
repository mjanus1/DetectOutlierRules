package com.mariusz.janus.DetectOutlierRules.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class AbstractMessageHandling {

	public static void addMessageGlobal(String message) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Wiadomość!", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addWarningGlobal(String message) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ostrzeżenie!", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addErrorGlobal(String message) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd!", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	// below message assigned to tag

	public static void addMessage(String clientID, String details, String summary) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, details);
		FacesContext.getCurrentInstance().addMessage(clientID, msg);
	}

	public static void addError(String clientID, String details, String summary) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
		FacesContext.getCurrentInstance().addMessage(clientID, msg);
	}
}
