package com.mariusz.janus.DetectOutlierRules.web;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
import com.mariusz.janus.DetectOutlierRules.domain.ServerProperty;
import com.mariusz.janus.DetectOutlierRules.domain.Token;



@ManagedBean
@ViewScoped
public class Test {
	
	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	private String code;

	public Test() {
	}

	@PostConstruct
	public void returnCode()
	{
		code="";
		Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		code = params.get("code");
		logger.debug("Jestem tutaj code={}",code);
		
		if(!code.isEmpty())
		{
			returToken();
		}
	}
	
	public void returToken()
	{
		logger.debug("sprawdzenie kodowania = {}",encode());
		logger.debug("Dla pewnosci sprawdzam jeszcze raz code ={}",code);
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "authorization_code");
		map.add("code", code);
		map.add("redirect_uri",ServerProperty.REDIRECT_URL);

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Basic "+encode());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<Token> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.TOKEN_PATH+"?grant_type=authorization_code&code="+code+"&redirect_uri="+ServerProperty.REDIRECT_URL, HttpMethod.POST, request, Token.class);
		Token token = response.getBody();
		
		
		logger.debug("Sprawdzenie żadania post pobranie tokena ={}",token.getAccess_token());
		System.out.println(token.getAccess_token());
	}

	private String encode()
	{
		String inputContent = "Test:zaqwsx";
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
		
	}
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}
