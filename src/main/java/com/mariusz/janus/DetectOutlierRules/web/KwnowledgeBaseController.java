package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;


@ManagedBean
@ViewScoped
public class KwnowledgeBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(KwnowledgeBaseController.class);
	private RestTemplate rest;
	private List<KnowledgeBase> listBases;
	private List<KnowledgeBase> filteredListBases;
	
	@ManagedProperty(value = "#{sessionUserController}")
	private SessionUserController sessionUser;
	
	
	
	public KwnowledgeBaseController() {
		rest = new RestTemplate();
		listBases = new ArrayList<>();
		filteredListBases = new ArrayList<>();
	}

	@PostConstruct
	public void initKnowledgeBase()
	{
		requstForKnowledgeBase();
	}
	
	private void requstForKnowledgeBase()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<KnowledgeBase>> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE, HttpMethod.GET, request,new ParameterizedTypeReference<List<KnowledgeBase>>() {
		});
		
		listBases=response.getBody();
		
		logger.debug("Sprawdzenie listy z bazami");
	}
	

	public RestTemplate getRest() {
		return rest;
	}
	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}
	public List<KnowledgeBase> getListBases() {
		return listBases;
	}
	public void setListBases(List<KnowledgeBase> listBases) {
		this.listBases = listBases;
	}
	public SessionUserController getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(SessionUserController sessionUser) {
		this.sessionUser = sessionUser;
	}

	public List<KnowledgeBase> getFilteredListBases() {
		return filteredListBases;
	}

	public void setFilteredListBases(List<KnowledgeBase> filteredListBases) {
		this.filteredListBases = filteredListBases;
	}
	
	
	
}
