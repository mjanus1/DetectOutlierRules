package com.mariusz.janus.DetectOutlierRules.Algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
 
public class VectorSpaceModelSimilary {
	

	public double getRoundSimillary(double sim) {		
		BigDecimal b = new BigDecimal(sim);
		return b.setScale(3, RoundingMode.HALF_EVEN).doubleValue();
	}
	
	
}
