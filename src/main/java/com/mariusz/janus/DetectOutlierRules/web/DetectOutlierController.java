package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.AUTHORIZATION;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.BEARER;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.KNOWLEDGEBASE;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;

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

import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class DetectOutlierController extends AbstracUtility {

	private final static Logger logger = LoggerFactory.getLogger(DetectOutlierController.class);
	@Getter
	@Setter
	private List<Attribute> attributes;
	@Getter
	@Setter
	private List<SingleVectorRule> vectorRules;
	@Getter
	@Setter
	private List<Rule> rules;
	@Getter
	@Setter
	private RestTemplate restTemplate;
	

	@ManagedProperty(value = "#{sessionUserController}")
	@Getter
	@Setter
	private SessionUserController session;

	public DetectOutlierController() {
		attributes = new ArrayList<>();
		vectorRules = new ArrayList<>();
		rules = new ArrayList<>();
		restTemplate = new RestTemplate();
	}

	@PostConstruct
	public void init() {
		attributes = requestForAllAttributes();
	}

	private List<Attribute> requestForAllAttributes() {
		int idKnowledgeBase = Integer.parseInt(getParametr("baseID"));

		HttpHeaders header = new HttpHeaders();
		header.add(AUTHORIZATION, BEARER + session.getAccesToken());

		HttpEntity<String> httpEntity = new HttpEntity<>(header);
		ResponseEntity<List<Attribute>> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + "/" + idKnowledgeBase + "/attributes", HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<List<Attribute>>() {
				});

		return response.getBody();
	}

	private List<Rule> requestForRules() {
		int idKnowledgeBase = Integer.parseInt(getParametr("baseID"));

		HttpHeaders header = new HttpHeaders();
		header.add(AUTHORIZATION, BEARER + session.getAccesToken());

		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<List<Rule>> response = restTemplate.exchange(
				SERVER_URL + KNOWLEDGEBASE + "/" + idKnowledgeBase + "/rules?all=true", HttpMethod.GET, request,
				new ParameterizedTypeReference<List<Rule>>() {
				});

		return response.getBody();

	}

	private List<SingleVectorRule> saveRulesAsVector() {
		rules = requestForRules();

		for (Rule rules : rules) {
			SingleVectorRule vector = new SingleVectorRule(13, rules.getId());
			for (AttributeValues attributes : rules.getAttributeValues()) {

				int index = getAttributes().indexOf(attributes.getAttribute());
				logger.debug("check range table for attribute {}", index);
				if (attributes.isConclusion()) {
					vector.getVectorRule()[1][0] = attributes.getValue().getName();
				} else if (attributes.getValue() != null) {
					vector.getVectorRule()[0][index] = attributes.getValue().getName();
				} else {
					vector.getVectorRule()[0][index] = attributes.getContinousValue();
				}
			}
			vectorRules.add(vector);
		}
		return vectorRules;
	}

	public void review() {
		logger.debug("sprawdzenie listy atrybutów {}", getAttributes());
		logger.debug("tu jestem");
		for (SingleVectorRule v : saveRulesAsVector()) {
			v.printVector();
			System.out.println("");
		}
	}

}
