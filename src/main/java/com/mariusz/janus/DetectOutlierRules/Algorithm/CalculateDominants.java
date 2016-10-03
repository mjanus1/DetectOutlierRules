package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.DISCRETE;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeMostOftenRepeated;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;

public class CalculateDominants extends SaveRulesAsVector {

	private static final Logger logger = LoggerFactory.getLogger(CalculateDominants.class);
	@Getter List<AttributeMostOftenRepeated> attributeMostOftenRepeated;

	public CalculateDominants(List<Rule> rules, List<Attribute> attributes) {
		super(rules, attributes);
		calculate();
	}

	public void calculate() {
		attributeMostOftenRepeated = new ArrayList<>();

		for (AttributeAdditionDetail attrAdditionDetails : getAttributeAdditionDetails()) {
			if (attrAdditionDetails.getAttribute().getType().equals(SYMBOLIC)
					|| attrAdditionDetails.getAttribute().getType().equals(DISCRETE)) {
				attributeMostOftenRepeated.add(searchModaInCondition(attrAdditionDetails));
			} else
				continue;
		}
		attributeMostOftenRepeated.add(searchModaInDecision());
	}

	private AttributeMostOftenRepeated searchModaInCondition(AttributeAdditionDetail attributeDetails) {
		Multiset<String> elements = HashMultiset.create();

		for (SingleVectorRule rule : getVectorRuleLists()) {
			if (rule.checkIsElementIsZero(0, attributeDetails.getPossitionOnVector()) == false)
				elements.add(rule.returnValueOnPossition(0, attributeDetails.getPossitionOnVector()));
		}

		int maxCount = 0;
		String dominanta = "brak";
		for (String moda : elements.elementSet()) {
			int count = elements.count(moda);
			if (count > maxCount) {
				maxCount = count;
				dominanta = moda;
			}
		}

		return new AttributeMostOftenRepeated(new AttributeAdditionDetail(attributeDetails.getAttribute(), false,
				attributeDetails.getPossitionOnVector()), dominanta, maxCount);
	}

	private AttributeMostOftenRepeated searchModaInDecision() {
		Multiset<String> elements = HashMultiset.create();

		for (SingleVectorRule rule : getVectorRuleLists()) {
			if (rule.checkIsElementIsZero(1, 0) == false)
				elements.add(rule.returnValueOnPossition(1, 0));
		}

		int maxCount = 0;
		String dominanta = "brak";
		for (String moda : elements.elementSet()) {
			int count = elements.count(moda);
			if (count > maxCount) {
				maxCount = count;
				dominanta = moda;
			}
		}
		return new AttributeMostOftenRepeated(new AttributeAdditionDetail(new Attribute("Decyzja"), true), dominanta,
				maxCount);
	}
	
	public void displayAttributeMostOftenRepeated() {
		for(AttributeMostOftenRepeated a : attributeMostOftenRepeated) {
			logger.debug("",a.getValue());
		}
	}

}
