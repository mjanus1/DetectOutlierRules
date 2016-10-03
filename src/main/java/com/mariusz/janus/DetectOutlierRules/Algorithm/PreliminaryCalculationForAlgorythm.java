package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;
import com.mariusz.janus.DetectOutlierRules.domain.AttributeMostOftenRepeated;
import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;
import lombok.Setter;

public class PreliminaryCalculationForAlgorythm extends CalculateDominants {

	private final static int DECISION_ROW = 1;
	private final static int DECISION_COLUMN = 0;
	private final static int CONDITION_ROW = 0;
	
	@Getter
	private SingleVectorRule dominanta;
	@Getter @Setter
	private List<AttributeMostOftenRepeated> attributesMostOftenRepeated;
	
	public PreliminaryCalculationForAlgorythm(List<Rule> rules, List<Attribute> attributes) {
		super(rules, attributes);
		calculateDominanta();
	}

	public void calculateDominanta() {
		attributesMostOftenRepeated = getAttributeMostOftenRepeated();
		boolean checkIsSelectDominanta = true;
		while (checkIsSelectDominanta) {
			for (SingleVectorRule vector : getVectorRuleLists()) {
				if (searchRuleWhichPorobablyDominanta(vector.getVectorRule())) {
					displaySelectDominant(vector);
					checkIsSelectDominanta = false;
					dominanta  = vector;
					break;
				}
			}
			removeAttributeWithTheSmallestCount();
		}
	}
	
	private void displaySelectDominant(SingleVectorRule dominanta) {
		System.out.println();
		System.out.println("WYBRANA DOMINANTA:");
		dominanta.printVector();
		System.out.println();
	}

	private void removeAttributeWithTheSmallestCount() {
		int min = 1000;
		AttributeMostOftenRepeated attModaToRemove = null;
		for (AttributeMostOftenRepeated attModa : attributesMostOftenRepeated) {
			if (attModa.getCount() < min) {
				min = attModa.getCount();
				attModaToRemove = attModa;
			}
		}
		attributesMostOftenRepeated.remove(attModaToRemove);
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
