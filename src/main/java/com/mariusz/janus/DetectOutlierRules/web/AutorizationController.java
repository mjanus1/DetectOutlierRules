package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.ACCOUNT;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_ID;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.CLIENT_SECRET;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.REDIRECT_URL;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.SERVER_URL;
import static com.mariusz.janus.DetectOutlierRules.domain.ServerProperty.TOKEN_PATH;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
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

import com.mariusz.janus.DetectOutlierRules.domain.Token;
import com.mariusz.janus.DetectOutlierRules.domain.User;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@RequestScoped
public class AutorizationController {

	private static final Logger logger = LoggerFactory.getLogger(AutorizationController.class);
	@Getter @Setter
	private User user;
	@Getter @Setter
	private Token token;
	@Getter @Setter
	private String code;
	@Getter @Setter
	private RestTemplate rest;

	@ManagedProperty(value = "#{sessionUserController}")
	@Getter @Setter
	private SessionUserController sessionUser;

	public AutorizationController() {
		rest = new RestTemplate();
	}

	public void requestForCode() {

		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		code = params.get("code");
		
			
		logger.debug("Sprawdzenie code ={}", code);
		if (code != null) {
			requestForToken();
			requestForUser();
			setSesionParameters();
			redirect("/available-bases.xhtml");
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd!", "Niestety nie otrzymałeś dostępu do serwisu");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			redirect("/index.xhtml");
		}
	}

	private void redirect(String page) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + page);
		} catch (IOException e) {
			logger.error("Exception in redirect", e);
		}
	}

	private void requestForToken() {
		logger.debug("sprawdzenie kodowania = {}", encode());
		logger.debug("Dla pewnosci sprawdzam jeszcze raz code ={}", code);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "authorization_code");
		map.add("code", code);
		map.add("redirect_uri", REDIRECT_URL);

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Basic " + encode());

		HttpEntity<?> request = new HttpEntity<>(map, header);
		ResponseEntity<Token> response = rest.exchange(SERVER_URL + TOKEN_PATH,
				HttpMethod.POST, request, Token.class);
		token = response.getBody();

		logger.debug("Sprawdzenie żadania post pobranie akces tokena ={}", token.getAccess_token());
		logger.debug("Sprawdzenie żadania post pobranie refresh tokena ={}", token.getRefresh_token());
	}

	private void requestForUser() {
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token.getAccess_token());

		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<User> response = rest.exchange(SERVER_URL + ACCOUNT,
				HttpMethod.GET, request, User.class);
		user = response.getBody();

		logger.debug("Sprawdzenie usera ={}", user.getLogin());
	}

	private void setSesionParameters() {
		sessionUser.setToken(token);
		sessionUser.setUser(user);

	}

	private String encode() {
		String inputContent = CLIENT_ID + ":" + CLIENT_SECRET;
		byte[] plainCredsBytes = inputContent.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}
}
