package com.mariusz.janus.DetectOutlierRules.service;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ACCOUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ALL_RULES;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ATTRIBUTES;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ATTRIBUTES_COUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.FACTS;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.FACTS_COUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.RULES_COUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SEPARATOR;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.CountElement;
import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.User;

@Service("IRestRequestService")
public class RestRequesService implements IRestRequestService {

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders header = new HttpHeaders();

	@Override
	public List<Attribute> listAllAttributes(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Attribute>> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ATTRIBUTES, GET, request,
				new ParameterizedTypeReference<List<Attribute>>() {
				});
		return response.getBody();
	}

	@Override
	public int countAttributes(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ATTRIBUTES_COUNT, GET, request, CountElement.class);
		return Integer.parseInt(response.getBody().getCount());
	}

	@Override
	public List<Rule> listAllRule(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Rule>> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ALL_RULES, GET, request,
				new ParameterizedTypeReference<List<Rule>>() {
				});
		return response.getBody();
	}

	@Override
	public int countRules(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + RULES_COUNT, GET, request, CountElement.class);
		return Integer.parseInt(response.getBody().getCount());
	}

	@Override
	public List<Fact> listAllFact(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> httpEntity = new HttpEntity<>(header);
		ResponseEntity<List<Fact>> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + FACTS, GET, httpEntity,
				new ParameterizedTypeReference<List<Fact>>() {
				});
		return response.getBody();
	}

	@Override
	public int countFact(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + FACTS_COUNT, GET, request, CountElement.class);
		return Integer.parseInt(response.getBody().getCount());
	}

	@Override
	public List<KnowledgeBase> listAllKnowledgeBase(String token) {
		header.add("Authorization", "Bearer " + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<KnowledgeBase>> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE, GET, request,
				new ParameterizedTypeReference<List<KnowledgeBase>>() {
				});
		return response.getBody();
	}

	@Override
	public KnowledgeBase knowledgeBase(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<KnowledgeBase> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId,
				GET, request, KnowledgeBase.class);
		return response.getBody();
	}

	@Override
	public User user(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<User> response = restTemplate.exchange(SERVER_URL + ACCOUNT, GET, request, User.class);
		return response.getBody();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public HttpHeaders getHeader() {
		return header;
	}

}
