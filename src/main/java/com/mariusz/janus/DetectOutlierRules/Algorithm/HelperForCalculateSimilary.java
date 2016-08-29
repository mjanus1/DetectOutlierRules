package com.mariusz.janus.DetectOutlierRules.Algorithm;

import lombok.Getter;
import lombok.Setter;

public class HelperForCalculateSimilary<T, V> {

	@Getter @Setter private T object;
	@Getter @Setter private V value;
	
	public HelperForCalculateSimilary(T object, V value) {
		this.object = object;
		this.value = value;
	}
}
