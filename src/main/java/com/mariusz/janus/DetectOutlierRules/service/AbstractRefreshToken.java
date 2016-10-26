package com.mariusz.janus.DetectOutlierRules.service;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ACCOUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_ID;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_SECRET;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.TOKEN_PATH;

import org.apache.myfaces.shade.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;

public abstract class AbstractRefreshToken {
	private static final Logger logger = LoggerFactory.getLogger(AbstractRefreshToken.class);
	
	public RestTemplate rest = new RestTemplate();
	
	public Token refreshToken(Token token, RestTemplate rest) {
		logger.debug("Jestem w odswierzaniu tokenu");
		logger.debug("Sprawdzanie tokenu " + token);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "refresh_token");
		map.add("refresh_token", token.getRefresh_token());

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Basic " + encode());

		HttpEntity<?> request = new HttpEntity<>(map, header);
		ResponseEntity<Token> response = rest.exchange(SERVER_URL + TOKEN_PATH,
				HttpMethod.POST, request, Token.class);
		token = new Token();
		token = response.getBody();
		logger.debug("Sprawdzenie Refresh Token: {}", token);
		return token;
	}
	
	public User requestForUser(Token token) {
		User user = new User();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<User> response = rest.exchange(SERVER_URL + ACCOUNT,
				HttpMethod.GET, request, User.class);
		user = response.getBody();

		logger.debug("Sprawdzenie usera ={}", user.getLogin());
		return user;
		
	}

	private String encode() {
		String inputContent = CLIENT_ID + ":" + CLIENT_SECRET;
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}
	
	public RestTemplate getRest() {
		return rest;
	}

	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}

}
