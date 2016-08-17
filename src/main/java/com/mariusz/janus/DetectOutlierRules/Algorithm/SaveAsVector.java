package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

public class SaveAsVector {

	private static final Logger logger = LoggerFactory.getLogger(SaveAsVector.class);
	private List<Attribute> listAttributes;
	private List<Rule> listRules;
	private List<SingleVectorRule> vectorRules;

	public SaveAsVector(List<Rule> rules, List<Attribute> attributes) {
		this.listRules = rules;
		this.listAttributes = attributes;
	}

	public List<SingleVectorRule> saveRulesAsVector() {
		for (Rule rules : listRules) {
			SingleVectorRule vector = new SingleVectorRule(13, rules.getId());
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
}
