package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.domain.DominantAttributes;

public class SearchAllDominantsInAttribute {

	private List<AttributeDetails> attributesDetails;
	private List<SingleVectorRule> vectorRules;
	private List<DominantAttributes> allDominants;
	
	
	public SearchAllDominantsInAttribute(List<AttributeDetails> attributesDetails, List<SingleVectorRule> vectorRules) {
		this.attributesDetails = attributesDetails;
		this.vectorRules = vectorRules;
	}
	
	public List<DominantAttributes> searchDominantesInSymbolicAttribute(){
		allDominants = new ArrayList<>();
		for(AttributeDetails attrDetails:attributesDetails) {
			if(attrDetails.getAttribute().getType().equals(SYMBOLIC) && !attrDetails.isConclussion()) {
				allDominants.add(searchModaInCondition(attrDetails));
			}
			
			if(attrDetails.getAttribute().getType().equals(SYMBOLIC) && attrDetails.isConclussion()) {
				allDominants.add(searchModaInDecision(attrDetails));
			}
			
		}
		return allDominants;
	}
	
	private DominantAttributes searchModaInCondition(AttributeDetails attributeDetails) {
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
		
		return new DominantAttributes(attributeDetails, dominanta, maxCount);
	}
	
	private DominantAttributes searchModaInDecision(AttributeDetails attributeDetails) {
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
		return new DominantAttributes(attributeDetails, dominanta, maxCount);	
	}

}
