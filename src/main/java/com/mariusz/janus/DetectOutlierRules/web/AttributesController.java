package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import org.slf4j.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import lombok.*;


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
		int idKnowledgeBase = Integer.parseInt(getParametr("baseID"));
		
		HttpHeaders header = new HttpHeaders();
		header.add(AUTHORIZATION,BEARER + sessionUser.getAccesToken());
		
		HttpEntity<String> httpEntity = new HttpEntity<>(header);
		ResponseEntity<List<Attribute>> response = rest.exchange(SERVER_URL + KNOWLEDGEBASE + "/" + idKnowledgeBase + "/attributes", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Attribute>>() {
		});

		attributesList=response.getBody();
		
		logger.debug("sprawdzenie czy pobrały sie atrybuty ={}",attributesList.size());
	}

	public int getIndex() {
		return index++;
	}
	

}
