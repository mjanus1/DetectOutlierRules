package com.mariusz.janus.DetectOutlierRules.service;

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

public abstract class AbstractRefreshToken {
	private static final Logger logger = LoggerFactory.getLogger(AbstractRefreshToken.class);
	
	
	public Token refreshToken(Token token, RestTemplate rest) {
		
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

	private String encode() {
		String inputContent = CLIENT_ID + ":" + CLIENT_SECRET;
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}
}
