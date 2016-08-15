package com.mariusz.janus.DetectOutlierRules.service;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ATTRIBUTES;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ATTRIBUTES_COUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
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

@Service
public class RestReques implements IRestRequest{

	private RestTemplate restTemplate;
	private HttpHeaders header;
	
	@Override
	public List<Attribute> requestForAllAttributes(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Attribute>> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE + "/" + baseId + ATTRIBUTES, GET, request, new ParameterizedTypeReference<List<Attribute>>() {});
		return response.getBody();
	}

	@Override
	public int countAttributes(String token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token);
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<CountElement> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE+"/" + baseId + ATTRIBUTES_COUNT, GET, request, CountElement.class);
		return Integer.parseInt(response.getBody().getCount());
	}

	
	public RestTemplate getRestTemplate() {
		restTemplate = new RestTemplate();
		return restTemplate;
	}
	public HttpHeaders getHeader() {
		header = new HttpHeaders();
		return header;
	}



	
}
