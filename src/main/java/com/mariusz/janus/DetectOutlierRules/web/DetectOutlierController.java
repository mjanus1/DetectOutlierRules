package com.mariusz.janus.DetectOutlierRules.web;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

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
import com.mariusz.janus.DetectOutlierRules.Algorithm.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.Algorithm.AttributeModa;
import com.mariusz.janus.DetectOutlierRules.Algorithm.FindDominanta;
import com.mariusz.janus.DetectOutlierRules.Algorithm.HelperForCalculateSimilary;
import com.mariusz.janus.DetectOutlierRules.Algorithm.SimilaryWithDominanta;
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
	@Getter @Setter private List<AttributeDetails> listAttributesDetails;
	@Getter @Setter private List<SingleVectorRule> vectorRules;
	@Getter @Setter private List<Rule>rules;
	@Getter @Setter private List<AttributeModa>dominantes;
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

	private void saveRulesAsVector() {
		vectorRules = new ArrayList<>();
		for (Rule rules : rules) {
			SingleVectorRule vector = new SingleVectorRule(listAttributes.size(), rules.getId());
			for (AttributeValues attributes : rules.getAttributeValues()) {
				int index = getIndexInVectorByAttribute(attributes.getAttribute());
				System.out.println("sprawdzenie pozycji przy zapisie " + index);
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
	}
	
	private int getIndexInVectorByAttribute(Attribute attribute) {
		for(AttributeDetails attDetails : listAttributesDetails) {
			if(attDetails.getAttribute().equals(attribute)){
				return attDetails.getPossitionOnVector();
			}
		}
		return 0;
	}
	
	private void createListAttributeDetails() {
		listAttributesDetails = new ArrayList<>();
		for (Rule rules : rules) {
			for (AttributeValues attributes : rules.getAttributeValues()) {
				int index = listAttributes.indexOf(attributes.getAttribute());
				
				if (attributes.isConclusion()) {
					if(checkIsExistAttribute(attributes.getAttribute().getName())){
						listAttributesDetails.add(new AttributeDetails(attributes.getAttribute(), true, index));
						logger.debug("dodano: {} nr pozycji: {}",attributes.getAttribute().getName(),index);
					}
					continue;
				} 
				else if (attributes.getValue() != null) {
					if(checkIsExistAttribute(attributes.getAttribute().getName())) {
						listAttributesDetails.add(new AttributeDetails(attributes.getAttribute(), false, index));
						logger.debug("dodano: {} nr pozycji: {}",attributes.getAttribute().getName(),index);
					}
					continue;
				} 
				else {
					if(checkIsExistAttribute(attributes.getAttribute().getName())){
						listAttributesDetails.add(new AttributeDetails(attributes.getAttribute(), false, index));
						logger.debug("dodano: {} nr pozycji: {}",attributes.getAttribute().getName(),index);
					}
					continue;
				}
			}
		}
		compareAttributesAndAddIfNoExistInAttributeDetails();
		System.out.println("sprawdzenie ile elementów na liscie ="+listAttributesDetails.size());
	}
	
	private void compareAttributesAndAddIfNoExistInAttributeDetails() {
		for(Attribute attribute : listAttributes) {
			if(checkIsExistAttribute(attribute.getName())) {
				listAttributesDetails.add(new AttributeDetails(attribute, false, listAttributes.indexOf(attribute)));
			}
		}
	}
	
	private boolean checkIsExistAttribute(String attributeName) {
		for(AttributeDetails attDetails :listAttributesDetails) {
			if((attDetails.getAttribute().getName()).equals(attributeName)) {
				return false;
			}
		}
		return true;
	}

	
	public void review() {
		createListAttributeDetails();
		saveRulesAsVector();
		logger.debug("tu jestem");
		for (SingleVectorRule v : vectorRules) {
			v.printVector();
			System.out.println("");
		}
	}
	
	public void searchDominantesInSymbolicAttribute(){
		dominantes = new ArrayList<>();
		for(AttributeDetails attrDetails:listAttributesDetails) {
			if(attrDetails.getAttribute().getType().equals(SYMBOLIC) && !attrDetails.isConclussion()) {
				dominantes.add(searcsModsInCondition(attrDetails));
			}
			
			if(attrDetails.getAttribute().getType().equals(SYMBOLIC) && attrDetails.isConclussion()) {
				dominantes.add(searchModaInDecision(attrDetails));
			}
			
		}
	}
	
	private AttributeModa searcsModsInCondition(AttributeDetails attributeDetails) {
		Multiset<String> elements = HashMultiset.create();
	
		for(SingleVectorRule rule: vectorRules) {
			if(rule.checkIsElementIsZero(0, attributeDetails.getPossitionOnVector()) == false) 
				elements.add(rule.returnValueOnPossition(0, attributeDetails.getPossitionOnVector()));	
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
		
		return new AttributeModa(attributeDetails, dominanta, maxCount);
	}
	private AttributeModa searchModaInDecision(AttributeDetails attributeDetails) {
		Multiset<String> elements = HashMultiset.create();

		for(SingleVectorRule rule: vectorRules) {
			if(rule.checkIsElementIsZero(1, 0) == false)
				elements.add(rule.returnValueOnPossition(1, 0));	
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
		return new AttributeModa(attributeDetails, dominanta, maxCount);	
	}
	
	public void showModa() {
		createListAttributeDetails();
		saveRulesAsVector();
		searchDominantesInSymbolicAttribute();
		for(AttributeModa m:dominantes) {
			if(m!=null)
				System.out.println(m.getAttributeDetails().getAttribute().getName()+": " + m.getValue() + " = " + m.getCount());
			 //System.out.println(m.getAttributes().getName());
		}
		
		FindDominanta fD = new FindDominanta(vectorRules, dominantes);
		
		
		System.out.println("Dominanta to: " + fD.calculateDominanta().getIdRules());
		System.out.println();
		System.out.println();
		
		SimilaryWithDominanta sm = new SimilaryWithDominanta(vectorRules, fD.calculateDominanta(), listAttributesDetails, listAttributes.size());
		
		for(HelperForCalculateSimilary<SingleVectorRule, Double> help : sm.getSimilaryBetweenRules()) {
			System.out.println("reguła = " + help.getObject().getIdRules() + " similary: "+help.getValue());
		}
	}
	
	

}
