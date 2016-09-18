package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;
import lombok.Setter;

public class SaveRulesAsVector {

	private static final Logger logger = LoggerFactory.getLogger(SaveRulesAsVector.class);

	@Getter @Setter
	private List<Rule> rules;
	@Getter @Setter
	private List<SingleVectorRule> vectorRules;
	@Getter @Setter
	private List<AttributeDetails> attributesDetails;



	public SaveRulesAsVector(List<Rule> listRules, List<AttributeDetails> listAttributesDetails) {
		this.rules = listRules;
		this.attributesDetails = listAttributesDetails;
	}

	public List<SingleVectorRule> createRulesAsVector() {
		vectorRules = new ArrayList<>();
		System.out.println();
		for (Rule rules : rules) {
			SingleVectorRule vector = new SingleVectorRule(attributesDetails.size(), rules);
			for (AttributeValues attributes : rules.getAttributeValues()) {
				int index = getIndexInVectorByAttribute(attributes.getAttribute());
				//System.out.println("tworzenie vektora, dodano: "+ attributes.getAttribute().getName()+" na pozycji " + index);
				if (attributes.isConclusion()) {
					vector.getVectorRule()[1][0] = attributes.getValue().getName();
				} else if (attributes.getValue() != null) {
					vector.getVectorRule()[0][index] = attributes.getValue().getName();
				} else {
					vector.getVectorRule()[0][index] = attributes.getContinousValue();
				}
			}
			vectorRules.add(vector);
			
			vector.printVector();
			System.out.println();
			
		}
		return vectorRules;
	}
	
	private int getIndexInVectorByAttribute(Attribute attribute) {
		for (AttributeDetails attDetails : attributesDetails) {
			if (attDetails.getAttribute().equals(attribute)) {
				return attDetails.getPossitionOnVector();
			}
		}
		return 0;
	}



}
