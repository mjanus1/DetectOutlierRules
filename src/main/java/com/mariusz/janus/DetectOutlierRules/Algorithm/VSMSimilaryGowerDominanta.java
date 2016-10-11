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

public class VSMSimilaryGowerDominanta extends VectorSpaceModelSimilary {

	@Getter
	@Setter
	private List<SingleVectorRule> listVectorRule;
	@Getter
	@Setter
	private List<AttributeAdditionDetail> attributesDetails;
	@Getter
	@Setter
	private SingleVectorRule dominanta;
	@Getter
	@Setter
	private List<HelperForCalculateSimilary<SingleVectorRule>> similary;
	@Getter 
	@Setter 
	private List<HelperForCalculateSimilary<SingleVectorRule>> outliers;
	@Getter
	@Setter
	private int max;
	@Getter
	@Setter
	private int min;

	public VSMSimilaryGowerDominanta(List<SingleVectorRule> listVectorRule, List<AttributeAdditionDetail> attributesDetails, SingleVectorRule dominanta) {
		this.listVectorRule = listVectorRule;
		this.attributesDetails = attributesDetails;
		this.dominanta = dominanta;
		calculateGowerDominantaSimilary();
	}
	
	public List<HelperForCalculateSimilary<SingleVectorRule>> getOutlierRules(int parametr) {
		outliers = new ArrayList<>();
		int countOutlier = similary.size() * parametr / 100;
		System.out.println("count outlier: " + countOutlier);
		Collections.sort(similary);
		for(int i =0; i<countOutlier; i++){
			outliers.add(similary.get(i));
		}
		System.out.println("sprawdzenie listy "+outliers.size());
		return outliers;
	}
	

	public void calculateGowerDominantaSimilary() {
		similary = new ArrayList<>();
		for (SingleVectorRule singleVector : listVectorRule) {
			int countHeighNegativeZero = 0;
			double value = 0.0;
			double result = 0.0;
			for (AttributeAdditionDetail attDetails : attributesDetails) {
				switch (attDetails.getAttribute().getType()) {
				case CONTINOUS:
					value = valueForContinous(attDetails, singleVector);
					if(value > 0 ){countHeighNegativeZero++;}
					result += value; 
					break;
				case SYMBOLIC:
					value = valueForSybolic(attDetails, singleVector);
					if(value > 0 ){countHeighNegativeZero++;}
					result += value; 
					break;
				case DISCRETE:
					value = valueForDiscrete(attDetails, singleVector);
					if(value > 0 ){countHeighNegativeZero++;}
					result += value; 
					break;
				default:
					break;
				}
			}
			
			similary.add(new HelperForCalculateSimilary<SingleVectorRule>(singleVector, getRoundSimillary(result/countHeighNegativeZero)));
		}
	}

	private double valueForSybolic(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
		if (singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()]
				.equals(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
			return 1.0;
		}
		return 0.0;
	}

	private double valueForContinous(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
		findMaxAndMinForContinousValue(attDetails.getPossitionOnVector());
		String valueRule = singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()];
		String valueDominant = dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()];
		if (valueRule.equals("0") || valueDominant.equals("0")) {
			return 0.0;
		} else {
			double valRule = Double.parseDouble(valueRule);
			double valDominanta = Double.parseDouble(valueDominant);
			double sim = 1 - ((Math.abs(valRule - valDominanta)) / (max - min));
			// System.out.println("Sprawdzenie podobieństwa dla atrybuty
			// ciągłego: " + sim);
			return sim;
		}
	}

	private double valueForDiscrete(AttributeAdditionDetail attDetails, SingleVectorRule singleVector) {
		if (singleVector.getVectorRule()[0][attDetails.getPossitionOnVector()]
				.equals(dominanta.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
			return 1.0;
		}
		return 0.0;
	}

	private void findMaxAndMinForContinousValue(int position) {
		int tempMax = 0;
		int tempMin = 0;
		for (SingleVectorRule scr : listVectorRule) {
			int z = Integer.parseInt(scr.getVectorRule()[0][position]);
			if (tempMax == 0) {
				tempMax = z;
				tempMin = z;
			}

			if (z < tempMin) {
				tempMin = z;
			}

			if (z > tempMax) {
				tempMax = z;
			}
		}
		// System.out.println("Maksymalna wartość w kolumnie: "+tempMax);
		// System.out.println("Minimalna wartość w kolumnie: "+tempMin);
		max = tempMax;
		min = tempMin;
	}

}
