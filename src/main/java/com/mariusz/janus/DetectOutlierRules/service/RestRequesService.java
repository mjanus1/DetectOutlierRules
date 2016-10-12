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
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.CountElement;
import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;
import com.mariusz.janus.DetectOutlierRules.web.SessionUserController;

import lombok.Getter;
import lombok.Setter;

@Service("IRestRequestService")
public class RestRequesService extends AbstractRefreshToken implements IRestRequestService {

	private static final Logger logger = LoggerFactory.getLogger(RestRequesService.class);
	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders header = new HttpHeaders();
	
	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter
	private SessionUserController sessionUser;

	@Override
	public List<Attribute> listAllAttributes(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);
		try {
			ResponseEntity<List<Attribute>> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ATTRIBUTES, GET, request,
					new ParameterizedTypeReference<List<Attribute>>() {
					});
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				listAllAttributes(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new ArrayList<>();
	}

	@Override
	public int countAttributes(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);
		try {
			ResponseEntity<CountElement> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ATTRIBUTES_COUNT, GET, request, CountElement.class);
			return Integer.parseInt(response.getBody().getCount());
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				countAttributes(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		return 0;
	}

	@Override
	public List<Rule> listAllRule(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);
		try {
			ResponseEntity<List<Rule>> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + ALL_RULES, GET, request,
					new ParameterizedTypeReference<List<Rule>>() {
					});
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				listAllRule(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new ArrayList<>();
	}

	@Override
	public int countRules(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);	
		try {
			ResponseEntity<CountElement> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + RULES_COUNT, GET, request, CountElement.class);
			return Integer.parseInt(response.getBody().getCount());
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				countRules(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return 0;
	}

	@Override
	public List<Fact> listAllFact(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> httpEntity = new HttpEntity<>(header);	
		try {
			ResponseEntity<List<Fact>> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + FACTS, GET, httpEntity,
					new ParameterizedTypeReference<List<Fact>>() {
					});
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				listAllFact(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new ArrayList<>();
	}

	@Override
	public int countFact(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);	
		try {
			ResponseEntity<CountElement> response = restTemplate.exchange(
					SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId + FACTS_COUNT, GET, request, CountElement.class);
			return Integer.parseInt(response.getBody().getCount());
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				countFact(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return 0;
	}

	@Override
	public List<KnowledgeBase> listAllKnowledgeBase(Token token) {
		header.add("Authorization", "Bearer " + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);	
		try {
			ResponseEntity<List<KnowledgeBase>> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE, GET, request,
					new ParameterizedTypeReference<List<KnowledgeBase>>() {
					});
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				listAllKnowledgeBase(token);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new ArrayList<>();
	}

	@Override
	public KnowledgeBase knowledgeBase(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);	
		try {
			ResponseEntity<KnowledgeBase> response = restTemplate.exchange(SERVER_URL + KNOWLEDGEBASE + SEPARATOR + baseId,
					GET, request, KnowledgeBase.class);
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				knowledgeBase(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new KnowledgeBase();
	}

	@Override
	public User user(Token token, int baseId) {
		header.add(AUTHORIZATION, BEARER + token.getAccess_token());
		HttpEntity<String> request = new HttpEntity<>(header);	
		try {
			ResponseEntity<User> response = restTemplate.exchange(SERVER_URL + ACCOUNT, GET, request, User.class);
			return response.getBody();
		}
		catch(HttpStatusCodeException statusExceprion) {
			if(statusExceprion.getStatusCode() == UNAUTHORIZED) {
				token = new Token();
				token = refreshToken(token, restTemplate);
				if(token != null) {
					sessionUser.setToken(token);
				}
				user(token, baseId);
			}
		}
		catch(Exception exception) {
			logger.error("Eception w RestRequestService", exception);
		}
		
		return new User();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public HttpHeaders getHeader() {
		return header;
	}
}
