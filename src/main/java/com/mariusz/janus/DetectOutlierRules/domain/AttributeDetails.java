package com.mariusz.janus.DetectOutlierRules.domain;

import lombok.Getter;
import lombok.Setter;

public class AttributeDetails implements Comparable<AttributeDetails>{

	@Getter @Setter private Attribute attribute;
	@Getter @Setter private boolean isConclusion;
	@Getter @Setter private int possitionOnVector;
	
	public AttributeDetails(Attribute attribute, boolean isConclussion) {
		this.attribute = attribute;
		this.isConclusion = isConclussion;
	}

	public AttributeDetails(Attribute attribute, boolean isConclussion, int possitionOnVector) {
		this.attribute = attribute;
		this.isConclusion = isConclussion;
		this.possitionOnVector = possitionOnVector;
	}

	@Override
	public int compareTo(AttributeDetails o) {
		if(o.isConclusion)
			return 1;
		return -1;
	}
}
