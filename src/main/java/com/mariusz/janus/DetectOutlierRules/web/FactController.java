package com.mariusz.janus.DetectOutlierRules.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Fact;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;


@ManagedBean
@ViewScoped
public class FactController extends AbstracController {

	private static final Logger logger = LoggerFactory.getLogger(FactController.class);
	private int index = 1;

	@Getter @Setter
	private List<Fact> listOfFact;
	
	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public FactController() {
	}

	@PostConstruct
	public void init() {
		listOfFact = service.listAllFact(tokenForRequest(), getParametr("baseID"));
		logger.debug("Pobrano list faktów {}",listOfFact);
	}
	
	public int getIndex() {
		return index++;
	}

}
