package com.mariusz.janus.DetectOutlierRules.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
public class AttributesController extends AbstracController {

	private static final Logger logger = LoggerFactory.getLogger(AttributesController.class);
	@Getter @Setter
	private List<Attribute> attributesList;
	private int index = 1;

	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public AttributesController() {	
	}

	@PostConstruct
	public void init() {
		attributesList = service.listAllAttributes(tokenForRequest(), getParametr("baseID"));
		logger.debug("Pobrano liste atrybutów ");
	}
	
	public int getIndex() {
		return index++;
	}

}
