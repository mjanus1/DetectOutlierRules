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
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;
import lombok.Getter;
import lombok.Setter;


@ManagedBean
@ViewScoped
public class AttributesController extends AbstracUtility{

	private static final Logger logger = LoggerFactory.getLogger(AttributesController.class);
	@Getter @Setter private List<Attribute> attributesList;
	@Getter @Setter private RestTemplate rest;
	private int index=1;
	
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter private SessionUserController sessionUser;
	
	
	public AttributesController() {
		rest = new RestTemplate();
		attributesList = new ArrayList<>();
	}
	
	@PostConstruct
	public void init()
	{
		requestForAllAttributes();
	}
	
	private void requestForAllAttributes()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+sessionUser.getToken().getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Attribute>> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/"+Integer.parseInt(getParametr("baseID"))+"/attributes", HttpMethod.GET, request,new ParameterizedTypeReference<List<Attribute>>() {
		});

		attributesList=response.getBody();
		
		logger.debug("sprawdzenie czy pobrały sie atrybuty ={}",attributesList.size());
	}

	public int getIndex() {
		return index++;
	}
	

}
