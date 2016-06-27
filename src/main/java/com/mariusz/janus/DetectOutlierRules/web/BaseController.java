package com.mariusz.janus.DetectOutlierRules.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;

@ManagedBean
@ViewScoped
public class BaseController {

	private KnowledgeBase knowledgeBase;
	private RestTemplate rest;
	private int rulesCount;
	private int attributeCount;
	private int factCount;
	
	@ManagedProperty(value = "#{sessionUserController}")
	private SessionUserController sessionUser;

	
	public BaseController() {
		rest = new RestTemplate();
		knowledgeBase = new KnowledgeBase();
	}

	@PostConstruct
	public void init()
	{
		requestForKwnowlwegeBase();
	}
	
	private void requestForKwnowlwegeBase()
	{
		
	}
	
	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}
	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
	public int getRulesCount() {
		return rulesCount;
	}
	public void setRulesCount(int rulesCount) {
		this.rulesCount = rulesCount;
	}
	public int getFactCount() {
		return factCount;
	}
	public void setFactCount(int factCount) {
		this.factCount = factCount;
	}
	public SessionUserController getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(SessionUserController sessionUser) {
		this.sessionUser = sessionUser;
	}
	public int getAttributeCount() {
		return attributeCount;
	}
	public void setAttributeCount(int attributeCount) {
		this.attributeCount = attributeCount;
	}
	public RestTemplate getRest() {
		return rest;
	}
	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}
	
	
	
	
}
