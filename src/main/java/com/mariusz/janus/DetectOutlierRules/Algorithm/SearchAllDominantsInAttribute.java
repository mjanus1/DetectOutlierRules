package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.DISCRETE;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.domain.DominantAttribute;

public class SearchAllDominantsInAttribute {

	private List<AttributeDetails> attributesDetails;
	private List<SingleVectorRule> vectorRules;
	private List<DominantAttribute> allDominants;
	
	
	public SearchAllDominantsInAttribute(List<AttributeDetails> attributesDetails, List<SingleVectorRule> vectorRules) {
		this.attributesDetails = attributesDetails;
		this.vectorRules = vectorRules;
	}
	
	public List<DominantAttribute> getAllDominantesInAttributes(){
		allDominants = new ArrayList<>();
		
		for(AttributeDetails attrDetails : attributesDetails) {
			if(attrDetails.getAttribute().getType().equals(SYMBOLIC) || attrDetails.getAttribute().getType().equals(DISCRETE)) {
				allDominants.add(searchModaInCondition(attrDetails));
			}
			else
				continue;
		}
			allDominants.add(searchModaInDecision());
		return allDominants;
	}
	
	private DominantAttribute searchModaInCondition(AttributeDetails attributeDetails) {
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
		
		return new DominantAttribute(new AttributeDetails(attributeDetails.getAttribute(), false, attributeDetails.getPossitionOnVector()), dominanta, maxCount);
	}
	
	private DominantAttribute searchModaInDecision() {
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
		return new DominantAttribute(new AttributeDetails(new Attribute("Decyzja"), true), dominanta, maxCount);	
	}

}
