package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_ID;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_SECRET;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.TOKEN_PATH;

import org.apache.myfaces.shade.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mariusz.janus.DetectOutlierRules.domain.Token;

public class Testowanie {

	public static void main(String[] args) {
		
//		Testowanie t = new Testowanie();
//		
//		Token token = new Token();
//		RestTemplate rest = new RestTemplate();
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.add("grant_type", "refresh_token");
//		map.add("refresh_token", "31c4b522-9557-43a8-8602-94e0784737d7");
//
//		HttpHeaders header = new HttpHeaders();
//		header.add("Authorization", "Basic " + t.encode());
//
//		HttpEntity<?> request = new HttpEntity<>(map, header);
//		ResponseEntity<Token> response = rest.exchange(SERVER_URL + TOKEN_PATH,
//				HttpMethod.POST, request, Token.class);
//		token = response.getBody();
//		



	}
	
	public String encode() {
		String inputContent = CLIENT_ID + ":" + CLIENT_SECRET;
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}

}
