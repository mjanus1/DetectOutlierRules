package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.Comparator;

import com.mariusz.janus.DetectOutlierRules.domain.Rule;

import lombok.Getter;
import lombok.Setter;

public class Cluster implements Comparable<Cluster>, Comparator<Cluster>{

	@Getter @Setter private String name;
	@Getter @Setter private Rule leftRule;
	@Getter @Setter private Rule rightRule;
	@Getter @Setter private Double gowerSimilary;
	
	public Cluster(String name, Rule leftRule, Rule rightRule, double gowerSimilary) {
		this.name = name;
		this.leftRule = leftRule;
		this.rightRule = rightRule;
		this.gowerSimilary = gowerSimilary;
	}

	@Override
	public int compare(Cluster o1, Cluster o2) {
		if(o1.gowerSimilary > o2.gowerSimilary)
			return 1;
		else if(o1.gowerSimilary == o2.gowerSimilary)
			return 0;
		else
			return -1;
	}

	@Override
	public int compareTo(Cluster o) {
		return this.gowerSimilary.compareTo(o.gowerSimilary);
	}
}
