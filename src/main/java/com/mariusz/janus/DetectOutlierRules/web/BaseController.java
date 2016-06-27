package com.mariusz.janus.DetectOutlierRules.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.CountElement;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;

@ManagedBean
@ViewScoped
public class BaseController extends AbstracUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	private KnowledgeBase knowledgeBase;
	private RestTemplate rest;
	private String rulesCount="";
	private String attributeCount="";
	private String factCount="";
	
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
		requestForCountRules();
		requestForCountAttribute();
		requestForCountFacts();
	}
	
	private void requestForKwnowlwegeBase()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<KnowledgeBase> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+Integer.parseInt(getParametr("baseID")), HttpMethod.GET, request,KnowledgeBase.class);
		
		knowledgeBase=response.getBody();
		
		logger.debug("Sprawdzenie pobranej bazy ={}",knowledgeBase.getName());
	}
	private void requestForCountRules()
	{
		logger.debug("sprawdzenie parametru={}",getParametr("baseID"));
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+"/rules/count", HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		rulesCount=c.getCount();
		
		logger.debug("Sprawdzenie count reguł ={}",rulesCount);
	}
	
	private void requestForCountAttribute()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+"/attributes/count", HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		attributeCount=c.getCount();
		
		logger.debug("Sprawdzenie count atybutów ={}",attributeCount);
	}
	private void requestForCountFacts()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+"/facts/count", HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		factCount=c.getCount();
		
		logger.debug("Sprawdzenie count faktów ={}",factCount);
	}
	
	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}
	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
	public SessionUserController getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(SessionUserController sessionUser) {
		this.sessionUser = sessionUser;
	}
	public RestTemplate getRest() {
		return rest;
	}
	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}

	public String getRulesCount() {
		return rulesCount;
	}

	public void setRulesCount(String rulesCount) {
		this.rulesCount = rulesCount;
	}

	public String getAttributeCount() {
		return attributeCount;
	}
	public void setAttributeCount(String attributeCount) {
		this.attributeCount = attributeCount;
	}
	public String getFactCount() {
		return factCount;
	}
	public void setFactCount(String factCount) {
		this.factCount = factCount;
	}
	
	
	
	
	
}
