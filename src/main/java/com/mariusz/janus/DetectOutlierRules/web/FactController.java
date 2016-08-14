package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.FACTS;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;
import static org.springframework.http.HttpMethod.GET;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import org.slf4j.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import lombok.*;


@ManagedBean
@ViewScoped
public class FactController extends AbstracUtility {

	private static final Logger logger = LoggerFactory.getLogger(FactController.class);
	private int index = 1;
	@Getter
	@Setter
	private RestTemplate restTemplate;
	@Getter
	@Setter
	private List<Fact> listOfFact;

	@ManagedProperty(value = "#{sessionUserController}")
	@Getter
	@Setter
	private SessionUserController sesionUser;

	public FactController() {
		listOfFact = new ArrayList<>();
		restTemplate = new RestTemplate();
	}

	@PostConstruct
	public void init() {
		requestForFact();
	}

	private void requestForFact() {
		int idKnowledgeBase = Integer.parseInt(getParametr("baseID"));
		
		HttpHeaders header = new HttpHeaders();
		header.add(AUTHORIZATION, BEARER + sesionUser.getAccesToken());
		
		HttpEntity<String> httpEntity = new HttpEntity<>(header);
		
		ResponseEntity<List<Fact>> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE + "/" + idKnowledgeBase + FACTS , GET, httpEntity, new ParameterizedTypeReference<List<Fact>>() {} );
		listOfFact = response.getBody();
		
		logger.debug("sprawdzenie listy faktów = {} ",listOfFact);
	}

	public int getIndex() {
		return index++;
	}

}
