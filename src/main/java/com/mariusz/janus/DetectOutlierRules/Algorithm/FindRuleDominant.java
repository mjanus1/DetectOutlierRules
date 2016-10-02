package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.DominantAttribute;

import lombok.Getter;
import lombok.Setter;

public class FindRuleDominant {

	private final static int DECISION_ROW = 1;
	private final static int DECISION_COLUMN = 0;
	private final static int CONDITION_ROW = 0;
	@Getter
	@Setter
	private List<SingleVectorRule> listVectorRules;
	@Getter
	@Setter
	private List<DominantAttribute> mods;
	@Getter
	@Setter
	private String[][] dominanta;

	public FindRuleDominant(List<SingleVectorRule> listVectorRules, List<DominantAttribute> mods) {
		this.listVectorRules = listVectorRules;
		this.mods = mods;
	}

	public FindRuleDominant() {
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
		DominantAttribute attModaToRemove = null;
		for (DominantAttribute attModa : mods) {
			if (attModa.getCount() < min) {
				min = attModa.getCount();
				attModaToRemove = attModa;
			}
		}
		mods.remove(attModaToRemove);
	}

	private boolean searchRuleWhichPorobablyDominanta(String[][] vector) {
		boolean checkIsVectorMayBeDominanta = false;

		for (DominantAttribute attModa : mods) {
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
