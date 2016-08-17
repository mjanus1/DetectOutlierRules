package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.Algorithm.SingleVectorRule;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;
import com.mariusz.janus.DetectOutlierRules.service.IRestRequestService;

import lombok.Getter;
import lombok.Setter;


@ManagedBean
@ViewScoped
public class DetectOutlierController extends AbstracController {

	private final static Logger logger = LoggerFactory.getLogger(DetectOutlierController.class);
	@Getter @Setter private List<Attribute> listAttributes;
	@Getter @Setter private List<SingleVectorRule> vectorRules;
	@Getter @Setter private List<Rule>rules;

	@Getter @Setter
	@ManagedProperty(value = "#{IRestRequestService}")
	private IRestRequestService service;

	public DetectOutlierController() {

	}

	@PostConstruct
	public void init() {
			int idKnowledgeBase = getParametr("baseID");
			listAttributes = service.listAllAttributes(tokenForRequest(), idKnowledgeBase);
			rules = service.listAllRule(tokenForRequest(), idKnowledgeBase);
	}

	private List<SingleVectorRule> saveRulesAsVector() {
		vectorRules = new ArrayList<>();
		for (Rule rules : rules) {
			SingleVectorRule vector = new SingleVectorRule(listAttributes.size(), rules.getId());
			for (AttributeValues attributes : rules.getAttributeValues()) {

				int index = listAttributes.indexOf(attributes.getAttribute());
				logger.debug("check range table for attribute {}", index);
				if (attributes.isConclusion()) {
					vector.getVectorRule()[1][0] = attributes.getValue().getName();
				} else if (attributes.getValue() != null) {
					vector.getVectorRule()[0][index] = attributes.getValue().getName();
				} else {
					vector.getVectorRule()[0][index] = attributes.getContinousValue();
				}
			}
			vectorRules.add(vector);
		}
		return vectorRules;
	}

	public void review() {
		logger.debug("tu jestem");
		for (SingleVectorRule v : saveRulesAsVector()) {
			v.printVector();
			System.out.println("");
		}
	}
	
	public int zliczIleJestAtrybutow(){
		int variable = 0;
		for(Attribute att:listAttributes){
			if(att.getType().equals("symbolic")){
				variable++;
			}
		}
		return variable;
	}

}
