package com.mariusz.janus.DetectOutlierRules.Algorithm;

import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.CONTINOUS;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.DISCRETE;
import static com.mariusz.janus.DetectOutlierRules.Algorithm.TypeValue.SYMBOLIC;

import java.util.ArrayList;
import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeAdditionDetail;

import lombok.Getter;
import lombok.Setter;

public class VSMSimilaryGower extends VectorSpaceModelSimilary {

	@Getter@Setter
	private List<SingleVectorRule> listVectorRule;
	@Getter@Setter
	private List<AttributeAdditionDetail> attributesDetails;
	@Setter
	private List<Cluster> clusterList;
	@Getter@Setter
	private int max;
	@Getter@Setter
	private int min;

	public VSMSimilaryGower(List<SingleVectorRule> listVectorRule, List<AttributeAdditionDetail> attributesDetails) {
		this.listVectorRule = listVectorRule;
		this.attributesDetails = attributesDetails;
		calculateGowerSimilary();
	}

	public void calculateGowerSimilary() {
		
		clusterList = new ArrayList<>();
		int licz = 0;
			for (int i = 0; i < listVectorRule.size(); i++) {
				for (int j = i + 1; j < listVectorRule.size(); j++) {
					double result = 0.0;
					for (AttributeAdditionDetail attDetails : attributesDetails) {
						switch (attDetails.getAttribute().getType()) {
						case CONTINOUS:
							result += valueForContinous(attDetails, listVectorRule.get(i), listVectorRule.get(j));
							break;
						case SYMBOLIC:
							result += valueForSybolic(attDetails, listVectorRule.get(i), listVectorRule.get(j));
							break;
						case DISCRETE:
							result += valueForDiscrete(attDetails, listVectorRule.get(i), listVectorRule.get(j));
							break;
						default:
							break;
						}
					}
					clusterList.add(new Cluster("R" + licz, listVectorRule.get(i).getRule(), listVectorRule.get(j).getRule(), getRoundSimillary(result/attributesDetails.size())));
					licz++;
				}
			}
	}

	private double valueForSybolic(AttributeAdditionDetail attDetails, SingleVectorRule singleVectorL, SingleVectorRule singleVectorR) {
		if (singleVectorL.getVectorRule()[0][attDetails.getPossitionOnVector()]
				.equals(singleVectorR.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
			return 1.0;
		}
		return 0;
	}

	private double valueForContinous(AttributeAdditionDetail attDetails, SingleVectorRule singleVectorL, SingleVectorRule singleVectorR) {
		findMaxAndMinForContinousValue(attDetails.getPossitionOnVector());
		String valueF = singleVectorL.getVectorRule()[0][attDetails.getPossitionOnVector()];
		String valueS = singleVectorR.getVectorRule()[0][attDetails.getPossitionOnVector()];
			if(valueF.equals("0") || valueS.equals("0")) {
				return 0;
			}
			else {
				double valueFirst = Double.parseDouble(valueF);
				double valueSecond = Double.parseDouble(valueS);
				double sim = 1 - ((Math.abs(valueFirst - valueSecond)) / (max - min)); 
				//System.out.println("Sprawdzenie podobieństwa dla atrybuty ciągłego: " + sim);
				return sim;
			}	
	}

	private double valueForDiscrete(AttributeAdditionDetail attDetails, SingleVectorRule singleVectorL, SingleVectorRule singleVectorR) {
		if (singleVectorL.getVectorRule()[0][attDetails.getPossitionOnVector()]
				.equals(singleVectorR.getVectorRule()[0][attDetails.getPossitionOnVector()])) {
			return 1.0;
		}
		return 0;
	}
	
	private void findMaxAndMinForContinousValue(int position) {
		int tempMax = 0;
		int tempMin = 0;
		for(SingleVectorRule scr:listVectorRule) {
			int z = Integer.parseInt(scr.getVectorRule()[0][position]);
			if(tempMax == 0) {
				tempMax = z;
				tempMin = z;
			}
			
			if(z < tempMin) {
				tempMin = z;
			}
			
			if(z > tempMax) {
				tempMax = z;
			}
		}
		//System.out.println("Maksymalna wartość w kolumnie: "+tempMax);
		//System.out.println("Minimalna wartość w kolumnie: "+tempMin);
		max = tempMax;
		min = tempMin;
	}
	
	public List<Cluster> getClusterList() {
		return clusterList;
	}
}
