package com.mariusz.janus.DetectOutlierRules.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.KnowledgeBase;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class BaseController extends AbstracController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Getter @Setter private KnowledgeBase knowledgeBase;
	
	@Getter @Setter private int rulesCount;
	@Getter @Setter  int attributeCount;
	@Getter @Setter private int factCount;
	
	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;
	
	public BaseController() {
	}

	@PostConstruct
	public void init() {
		requestForKwnowlwegeBase();
		requestForCountRules();
		requestForCountAttribute();
		requestForCountFacts();
	}
	
	private void requestForKwnowlwegeBase() {
		knowledgeBase=service.knowledgeBase(tokenForRequest(), getParametr("baseID"));
		logger.debug("Sprawdzenie pobranej bazy ={}",knowledgeBase.getName());
	}
	private void requestForCountRules() {
		rulesCount=service.countRules(tokenForRequest(), getParametr("baseID"));
		logger.debug("Sprawdzenie count reguł ={}",rulesCount);
	}
	
	private void requestForCountAttribute() {	
		attributeCount=service.countAttributes(tokenForRequest(), getParametr("baseID"));	
		logger.debug("Sprawdzenie count atybutów ={}",attributeCount);
	}
	
	private void requestForCountFacts() {	
		factCount=service.countFact(tokenForRequest(), getParametr("baseID"));
		logger.debug("Sprawdzenie count faktów ={}",factCount);
	}
}
