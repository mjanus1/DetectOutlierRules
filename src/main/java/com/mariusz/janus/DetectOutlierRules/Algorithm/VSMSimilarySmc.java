package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.CONTINOUS;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.DISCRETE;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;

import lombok.Getter;
import lombok.Setter;

public class VSMSimilarySmc extends VectorSpaceModelSimilary{

	@Getter @Setter private List<SingleVectorRule> listVectorRule;
	@Getter @Setter private SingleVectorRule dominanta;
	@Getter @Setter private Integer parametrForCalculateOutlier;
	@Getter @Setter private List<HelperForCalculateSimilary<SingleVectorRule>> similary;
	@Getter @Setter private List<HelperForCalculateSimilary<SingleVectorRule>> outliers;
	@Getter @Setter private List<AttributeAdditionDetail> attributesDetails;
	@Getter @Setter private int countAllAttributes;
	
	public VSMSimilarySmc(List<SingleVectorRule> listVectorRule, SingleVectorRule dominanta, List<AttributeAdditionDetail> attributesDetails, int countAllAttributes) {
		this.listVectorRule = listVectorRule;
		this.dominanta = dominanta;
		this.attributesDetails = attributesDetails;
		this.countAllAttributes = countAllAttributes;
	}
	
	public List<HelperForCalculateSimilary<SingleVectorRule>> getOutlierRules(int parametr) {
		outliers = new ArrayList<>();
		int countOutlier = getSimilaryBetweenRules().size() * parametr / 100;
//		System.out.println("count outlier: "+countOutlier);
		Collections.sort(similary);
		for(int i = 0; i<countOutlier; i++){
			outliers.add(similary.get(i));
		}
//		System.out.println("sprawdzenie listy "+outliers.size());
		return outliers;
	}
	
	public List<HelperForCalculateSimilary<SingleVectorRule>> getSimilaryBetweenRules() {
		similary = new ArrayList<>();
		int mianownik = countAllAttributes;
	
		for(SingleVectorRule singleVector : listVectorRule) {
			double licznik = 0.0;
			StringBuilder builder = new StringBuilder();
			for(AttributeAdditionDetail attDetails :attributesDetails) {
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
//			System.out.println("Reguła: " + singleVector.getRule().getId() + "  " + builder.toString());
			similary.add(new HelperForCalculateSimilary<SingleVectorRule>(singleVector, getRoundSimillary(licznik/mianownik)));
		}
		return similary;
	}
	
	private double valueForSybolic(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
		if(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()].equals(singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
			return 1.0;
		}		
		return 0;	
	}
	
	private double valueForContinous(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
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
	
	private double valueForDiscrete(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
		if(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()].equals(singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
				return 1.0;
		}
		return 0;		
	}
}
