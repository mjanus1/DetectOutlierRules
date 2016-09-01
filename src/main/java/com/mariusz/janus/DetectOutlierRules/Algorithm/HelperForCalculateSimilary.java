package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;

public class HelperForCalculateSimilary<T, V> implements Comparator<HelperForCalculateSimilary<T, Double>>{

	@Getter @Setter private T object;
	@Getter @Setter private V value;
	
	public HelperForCalculateSimilary(T object, V value) {
		this.object = object;
		this.value = value;
	}
	

	@Override
	public int compare(HelperForCalculateSimilary<T, Double> o1, HelperForCalculateSimilary<T, Double> o2) {
		
		if(o1.getValue()>o2.getValue())
			return 1;
		else if(o1.getValue()==o2.getValue())
			return 0;
		else
			return -1;
	}


	public HelperForCalculateSimilary() {
		super();
	}

	
}
