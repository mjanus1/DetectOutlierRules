package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;

public class HelperForCalculateSimilary<T> implements Comparator<HelperForCalculateSimilary<T>>, Comparable<HelperForCalculateSimilary<T>>{

	@Getter @Setter private T object;
	@Getter @Setter private Double value;
	
	
	public HelperForCalculateSimilary(T object, Double value) {
		this.object = object;
		this.value = value;
	}
	

	@Override
	public int compare(HelperForCalculateSimilary<T> o1, HelperForCalculateSimilary<T> o2) {
		
		if(o1.value > o2.value)
			return 1;
		else if(o1.value == o2.value)
			return 0;
		else
			return -1;
	}

	@Override
	public int compareTo(HelperForCalculateSimilary<T> o) {
		return this.value.compareTo(o.value);
	}
}
