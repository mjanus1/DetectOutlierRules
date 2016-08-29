package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.CONTINOUS;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.DISCRETE;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SimilaryWithDominanta {
	
	@Getter @Setter private List<SingleVectorRule> listVectorRule;
	@Getter @Setter private SingleVectorRule dominanta;
	@Getter @Setter private Integer parametrForCalculateOutlier;
	@Getter @Setter private List<HelperForCalculateSimilary<SingleVectorRule, Double>> similary;
	@Getter @Setter private List<HelperForCalculateSimilary<SingleVectorRule, Double>> outliers;
	@Getter @Setter private List<AttributeDetails> attributesDetails;
	@Getter @Setter private int countAllAttributes;
	
	public SimilaryWithDominanta(List<SingleVectorRule> listVectorRule, SingleVectorRule dominanta, List<AttributeDetails> attributesDetails, int countAllAttributes) {
		this.listVectorRule = listVectorRule;
		this.dominanta = dominanta;
		this.attributesDetails = attributesDetails;
		this.countAllAttributes = countAllAttributes;
	}
	
	public List<HelperForCalculateSimilary<SingleVectorRule, Double>> getSimilaryBetweenRules() {
		similary = new ArrayList<>();
		int mianownik = countAllAttributes;
	
		for(SingleVectorRule singleVector : listVectorRule) {
			double licznik = 0.0;
			StringBuilder builder = new StringBuilder();
			for(AttributeDetails attDetails :attributesDetails) {
				double result = 0.0;
				switch (attDetails.getAttribute().getType()) {
				case CONTINOUS:
					result = valueForContinous(attDetails, singleVector);
					licznik = licznik + result;
					builder.append(result+", ");
					break;
				case SYMBOLIC:
					result = valueForSybolic(attDetails, singleVector);
					licznik = licznik + result; 
					builder.append(result+", ");
					break;
				case DISCRETE:
					result = valueForDiscrete(attDetails, singleVector);
					licznik = licznik + result;
					builder.append(result+", ");
					break;
				default:
					break;
				}	
			}
			System.out.println(builder.toString());
			similary.add(new HelperForCalculateSimilary<SingleVectorRule, Double>(singleVector, licznik/mianownik));
		}
		return similary;
	}
	
	private double valueForSybolic(AttributeDetails attDetails, SingleVectorRule singleVector) {
		if(attDetails.isConclussion()) {
			return 1.0;
		}
		else {
			if(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()].equals(singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
				return 1.0;
			}
		}
		return 0;	
	}
	
	private double valueForContinous(AttributeDetails attDetails, SingleVectorRule singleVector) {
		double variableForVector = Double.parseDouble(singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()]);
		double variableForDominanta = Double.parseDouble(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()]);
		
		if(variableForDominanta > 0 && variableForVector > 0) {
			return variableForVector/variableForDominanta;
		}
		else if(variableForDominanta == 0 && variableForVector == 0) {
			return 1.0;
		}
		return 0;
	}
	
	private double valueForDiscrete(AttributeDetails attDetails, SingleVectorRule singleVector) {
		if(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()].equals(singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
				return 1.0;
		}
		return 0;		
	}
	
	public List<HelperForCalculateSimilary<SingleVectorRule, Double>> getOutlier(int cutParametr) {
		
		return null;
	}
	
}
