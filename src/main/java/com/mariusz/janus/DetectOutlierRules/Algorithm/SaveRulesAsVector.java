package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.ArrayList;
import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeValues;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;

public class SaveRulesAsVector extends DetailsAttribute {
	@Getter private List<SingleVectorRule> vectorRuleLists;

	public SaveRulesAsVector(List<Rule> rules, List<Attribute> attributes) {
		super(rules, attributes);
		createRulesAsVector();
	}

	private void createRulesAsVector() {
		vectorRuleLists = new ArrayList<>();
		for (Rule rule : getRules()) {
			SingleVectorRule vector = new SingleVectorRule(getAttributeAdditionDetails().size(), rule);
			for (AttributeValues attributes : rule.getAttributeValues()) {
				int index = getIndexInVectorByAttribute(attributes.getAttribute());
				if (attributes.isConclusion()) {
					vector.getVectorRule()[1][0] = attributes.getValue().getName();
				} else if (attributes.getValue() != null) {
					vector.getVectorRule()[0][index] = attributes.getValue().getName();
				} else {
					vector.getVectorRule()[0][index] = attributes.getContinousValue();
				}
			}
			vectorRuleLists.add(vector);
			vector.printVector();
			System.out.println();
		}
	}

	private int getIndexInVectorByAttribute(Attribute attribute) {
		for (AttributeAdditionDetail attDetails : getAttributeAdditionDetails()) {
			if (attDetails.getAttribute().equals(attribute)) {
				return attDetails.getPossitionOnVector();
			}
		}
		return 0;
	}
}
