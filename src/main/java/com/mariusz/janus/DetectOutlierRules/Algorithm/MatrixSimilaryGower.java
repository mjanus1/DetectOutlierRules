package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;

import lombok.Getter;
import lombok.Setter;

public class MatrixSimilaryGower extends VSMSimilaryGower{

	@Getter @Setter List<Cluster> listOfCluster;	

	public MatrixSimilaryGower(List<SingleVectorRule> listVectorRule, List<AttributeDetails> attributesDetails) {
		super(listVectorRule, attributesDetails);
	}
	
	public void getOutlierByParametr(int parametr) {
		listOfCluster = calculateGowerSimilary();
		System.out.println("Ile mamy: " + listOfCluster.size());
		//listOfCluster = calculateGowerSimilary();
		//int countOutlier = listOfCluster.size() * parametr / 100;
		//System.out.println("Count outlier: " + countOutlier);
		//Collections.sort(listOfCluster);
		
		for(int i =0; i<listOfCluster.size(); i++){
			System.out.println(listOfCluster.get(i).getName());
		}
		
	}

}
