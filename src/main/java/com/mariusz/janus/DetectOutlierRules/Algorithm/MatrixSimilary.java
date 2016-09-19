package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.Collections;
import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;

import lombok.Getter;
import lombok.Setter;

public class MatrixSimilary extends VSMSimilaryGower{

	@Getter@Setter List<Cluster> listOfCluster;	

	public MatrixSimilary(List<SingleVectorRule> listVectorRule, List<AttributeDetails> attributesDetails) {
		super(listVectorRule, attributesDetails);
		listOfCluster = calculateGowerSimilary();
	}
	
	public void getOutlierByParametr(int parametr) {
		int countOutlier = listOfCluster.size() * parametr / 100;
		System.out.println("Count outlier: " + countOutlier);
		Collections.sort(listOfCluster);
		
		for(int i =0; i<countOutlier; i++){
			//outliers.add(similary.get(i));
		}
		//System.out.println("sprawdzenie listy "+outliers.size());
		//return outliers;
	}

}
