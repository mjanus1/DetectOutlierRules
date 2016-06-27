package com.mariusz.janus.DetectOutlierRules.web;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
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
import com.mariusz.janus.DetectOutlierRules.domain.User;


@ManagedBean
@RequestScoped
public class AutorizationController {

	private static final Logger logger = LoggerFactory.getLogger(AutorizationController.class);
	private User user;
	private Token token;
	private String code;
	private RestTemplate rest;
	
	@ManagedProperty(value = "#{sessionUserController}")
	private SessionUserController sessionUser;

	public AutorizationController() {
		rest = new RestTemplate();
	}
	 
	public void requestForCode()
	{
			
			Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			code = params.get("code");
			logger.debug("Sprawdzenie code ={}",code);
			if(code!=null)
			{
				requestForToken();
				requestForUser();
				setSesionParameters();
			}
		
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + "/main.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	private void requestForToken()
	{
		logger.debug("sprawdzenie kodowania = {}",encode());
		logger.debug("Dla pewnosci sprawdzam jeszcze raz code ={}",code);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "authorization_code");
		map.add("code", code);
		map.add("redirect_uri",ServerProperty.REDIRECT_URL);

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Basic "+encode());
		
		HttpEntity<?> request = new HttpEntity<>(map,header);
		ResponseEntity<Token> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.TOKEN_PATH, HttpMethod.POST, request, Token.class);
		token = response.getBody();
		
		logger.debug("Sprawdzenie żadania post pobranie tokena ={}",token.getAccess_token());
		
	}
	
	private void requestForUser()
	{
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+token.getAccess_token());
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<User> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.ACCOUNT, HttpMethod.GET, request, User.class);
		user = response.getBody();
		
		logger.debug("Sprawdzenie usera ={}",user.getLogin());
	}
	
	
	private void setSesionParameters()
	{
		sessionUser.setToken(token);
		sessionUser.setUser(user);
		
	}	

	private String encode()
	{
		String inputContent = ServerProperty.CLIENT_ID+":"+ServerProperty.CLIENT_SECRET;
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
		
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public SessionUserController getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(SessionUserController sessionUser) {
		this.sessionUser = sessionUser;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public RestTemplate getRest() {
		return rest;
	}
	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}

	
}
