package com.mariusz.janus.DetectOutlierRules.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mariusz.janus.DetectOutlierRules.Algorithm.Moda;
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
	@Getter @Setter private List<Moda>dominantes;
	@Getter private int ruleCount;
	
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
			ruleCount = service.countRules(tokenForRequest(), idKnowledgeBase);
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
				} 
				else if (attributes.getValue() != null) {
					vector.getVectorRule()[0][index] = attributes.getValue().getName();
				} 
				else {
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
	
	public void searchDominantes(){
		dominantes = new ArrayList<>();
		for(Attribute att:listAttributes){
			if(att.getType().equals("symbolic")){
				dominantes.add(searcsModsInCondition(att));
			}
		}
	}
	
	private Moda searcsModsInCondition(Attribute attributes) {
		Multiset<String> elements = HashMultiset.create();
		int possitionValueSymbolic = listAttributes.indexOf(attributes);
		
		for(SingleVectorRule rule: vectorRules) {
			if(rule.checkIsElementIsZero(0, possitionValueSymbolic) == false)
				elements.add(rule.returnValueOnPossition(0, possitionValueSymbolic));	
		}
		
		int maxCount = 0;
		String dominanta = "brak";
		for(String moda: elements.elementSet()) {
			int count = elements.count(moda);
			if(count > maxCount){
				maxCount = count;
				dominanta = moda;
			}
		}
		
		return new Moda(attributes, dominanta, maxCount);
	}
	
	public void showModa() {
		saveRulesAsVector();
		searchDominantes();
		for(Moda m:dominantes) {
			//if(m!=null)
				System.out.println(m.getValue() + " = " + m.getCount());
			 //System.out.println(m.getAttributes().getName());
		}
	}
	
	

}
