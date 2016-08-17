package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

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
public class KwnowledgeBaseController extends AbstracController {
	
	private static final Logger logger = LoggerFactory.getLogger(KwnowledgeBaseController.class);

	@Getter @Setter private List<KnowledgeBase> listBases;
	@Getter @Setter private List<KnowledgeBase> filteredListBases;
	private int index=1;
	
	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;
	
	public KwnowledgeBaseController() {
		filteredListBases = new ArrayList<>();
	}

	@PostConstruct
	public void initKnowledgeBase() {
		listBases = service.listAllKnowledgeBase(tokenForRequest());
		logger.debug("Pobrano liste baza wiedzy ={}",listBases);
	}

	public int getIndex() {
		return index++;
	}
	
}
