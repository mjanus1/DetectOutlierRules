package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mariusz.janus.DetectOutlierRules.domain.AttributeDetails;

import lombok.Getter;
import lombok.Setter;

public class MatrixSimilaryGower extends VSMSimilaryGower {

	@Getter@Setter
	List<Cluster> listOfCluster;

	public MatrixSimilaryGower(List<SingleVectorRule> listVectorRule, List<AttributeDetails> attributesDetails) {
		super(listVectorRule, attributesDetails);
	}

	public List<HelperForCalculateSimilary<Cluster>> getOutlierByParametr(int parametr) {
		List<HelperForCalculateSimilary<Cluster>> list = new ArrayList<>();

		listOfCluster = getClusterList();
		Collections.sort(listOfCluster);
		Collections.reverse(listOfCluster);
		// System.out.println("Ile mamy: " + listOfCluster.size());
		// listOfCluster = calculateGowerSimilary();
		int countOutlier = listOfCluster.size() * parametr / 100;
		System.out.println("Count outlier: " + countOutlier);

		for (int i = 0; i < countOutlier; i++) {
			list.add(new HelperForCalculateSimilary<Cluster>(listOfCluster.get(i),
					listOfCluster.get(i).getGowerSimilary()));
		}
		System.out.println("sprawdzenie listy " + list.size());
		return list;

	}

}
