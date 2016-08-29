package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FindDominanta {

	private final static int DECISION_ROW = 1;
	private final static int DECISION_COLUMN = 0;
	private final static int CONDITION_ROW = 0;
	@Getter @Setter
	private List<SingleVectorRule> listVectorRules;
	@Getter @Setter
	private List<AttributeModa> mods;
	@Getter @Setter
	private String[][] dominanta;

	public FindDominanta(List<SingleVectorRule> listVectorRules, List<AttributeModa> mods) {
		this.listVectorRules = listVectorRules;
		this.mods = mods;
	}

	public SingleVectorRule calculateDominanta() {
		boolean checkIsSelectDominanta = true;
		while (checkIsSelectDominanta) {
			for (SingleVectorRule vector : listVectorRules) {
				if (searchRuleWhichPorobablyDominanta(vector.getVectorRule())) {
					vector.printVector();
					checkIsSelectDominanta = false;
					return vector;
				}
			}
			removeAttributeWithTheSmallestCount();
		}
		return null;
	}

	private void removeAttributeWithTheSmallestCount() {
		int min = 1000;
		AttributeModa attModaToRemove = null;
		for (AttributeModa attModa : mods) {
			if (attModa.getCount() < min) {
				min = attModa.getCount();
				attModaToRemove = attModa;
			}
		}
		mods.remove(attModaToRemove);
		// System.out.println("Usunięto jeden obiekt");
	}

	private boolean searchRuleWhichPorobablyDominanta(String[][] vector) {
		boolean checkIsVectorMayBeDominanta = false;

		for (AttributeModa attModa : mods) {
			if (attModa.getAttributeDetails().isConclussion()) {
				if (attModa.getValue().equals(vector[DECISION_ROW][DECISION_COLUMN])) {
					checkIsVectorMayBeDominanta = true;
				} else {
					checkIsVectorMayBeDominanta = false;
					break;
				}
			} else if (!attModa.getAttributeDetails().isConclussion()) {
				if (attModa.getValue()
						.equals(vector[CONDITION_ROW][attModa.getAttributeDetails().getPossitionOnVector()])) {
					checkIsVectorMayBeDominanta = true;
				} else {
					checkIsVectorMayBeDominanta = false;
					break;
				}
			} else {
				checkIsVectorMayBeDominanta = false;
				break;
			}
		}
		return checkIsVectorMayBeDominanta;
	}
}
