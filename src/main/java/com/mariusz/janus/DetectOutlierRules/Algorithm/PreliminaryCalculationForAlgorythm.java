package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeMostOftenRepeated;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;

public class PreliminaryCalculationForAlgorythm extends CalculateDominants {

	private final static int DECISION_ROW = 1;
	private final static int DECISION_COLUMN = 0;
	private final static int CONDITION_ROW = 0;
	
	@Getter
	private SingleVectorRule dominanta;
	
	public PreliminaryCalculationForAlgorythm(List<Rule> rules, List<Attribute> attributes) {
		super(rules, attributes);
		calculateDominanta();
	}

	public void calculateDominanta() {
		boolean checkIsSelectDominanta = true;
		while (checkIsSelectDominanta) {
			for (SingleVectorRule vector : getVectorRuleLists()) {
				if (searchRuleWhichPorobablyDominanta(vector.getVectorRule())) {
					vector.printVector();
					checkIsSelectDominanta = false;
					dominanta  = vector;
				}
			}
			removeAttributeWithTheSmallestCount();
		}
	}

	private void removeAttributeWithTheSmallestCount() {
		int min = 1000;
		AttributeMostOftenRepeated attModaToRemove = null;
		for (AttributeMostOftenRepeated attModa : getAttributeMostOftenRepeated()) {
			if (attModa.getCount() < min) {
				min = attModa.getCount();
				attModaToRemove = attModa;
			}
		}
		getAttributeMostOftenRepeated().remove(attModaToRemove);
	}

	private boolean searchRuleWhichPorobablyDominanta(String[][] vector) {
		boolean checkIsVectorMayBeDominanta = false;

		for (AttributeMostOftenRepeated attModa : getAttributeMostOftenRepeated()) {
			boolean isConclusion = attModa.getAttributeDetails().isConclusion();

			if (isConclusion) {
				if (attModa.getValue().equals(vector[DECISION_ROW][DECISION_COLUMN])) {
					checkIsVectorMayBeDominanta = true;
					continue;
				} else {
					checkIsVectorMayBeDominanta = false;
					break;
				}
				
			} else {
				if (attModa.getValue().equals(vector[CONDITION_ROW][attModa.getAttributeDetails().getPossitionOnVector()])) {
					checkIsVectorMayBeDominanta = true;
					continue;
				} else {
					checkIsVectorMayBeDominanta = false;
					break;
				}
			}
		}
		return checkIsVectorMayBeDominanta;
	}
}
