package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.Comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.Getter;
import lombok.Setter;

public class HelperForCalculateSimilary implements Comparator<HelperForCalculateSimilary>, Comparable<HelperForCalculateSimilary>{

	@Getter @Setter private SingleVectorRule object;
	@Getter @Setter private Double value;
	
	
	public HelperForCalculateSimilary(SingleVectorRule object, Double value) {
		this.object = object;
		this.value = value;
	}
	

	@Override
	public int compare(HelperForCalculateSimilary o1, HelperForCalculateSimilary o2) {
		
		if(o1.getValue()>o2.getValue())
			return 1;
		else if(o1.getValue()==o2.getValue())
			return 0;
		else
			return -1;
	}

	@Override
	public int compareTo(HelperForCalculateSimilary o) {
		return this.value.compareTo(o.value);
	}
}
