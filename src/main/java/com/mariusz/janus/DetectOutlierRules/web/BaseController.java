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

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class BaseController extends AbstracUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Getter @Setter private KnowledgeBase knowledgeBase;
	@Getter @Setter private RestTemplate rest;
	
	@Getter @Setter private String rulesCount="";
	@Getter @Setter  String attributeCount="";
	@Getter @Setter private String factCount="";
	
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter private SessionUserController sessionUser;

	
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
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+ServerProperty.RULES_COUNT, HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		rulesCount=c.getCount();
		
		logger.debug("Sprawdzenie count reguł ={}",rulesCount);
	}
	
	private void requestForCountAttribute()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+ServerProperty.ATTRIBUTES_COUNT, HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		attributeCount=c.getCount();
		
		logger.debug("Sprawdzenie count atybutów ={}",attributeCount);
	}
	private void requestForCountFacts()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+knowledgeBase.getId()+ServerProperty.FACTS_COUNT, HttpMethod.GET, request,CountElement.class);
		CountElement c=response.getBody();
		factCount=c.getCount();
		
		logger.debug("Sprawdzenie count faktów ={}",factCount);
	}
}
