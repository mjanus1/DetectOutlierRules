package com.mariusz.janus.DetectOutlierRules.domain;

import lombok.Getter;
import lombok.Setter;

public class AttributeAdditionDetail implements Comparable<AttributeAdditionDetail>{

	@Getter @Setter private Attribute attribute;
	@Getter @Setter private boolean isConclusion;
	@Getter @Setter private int possitionOnVector;
	
	public AttributeAdditionDetail(Attribute attribute, boolean isConclussion) {
		this.attribute = attribute;
		this.isConclusion = isConclussion;
	}

	public AttributeAdditionDetail(Attribute attribute, boolean isConclussion, int possitionOnVector) {
		this.attribute = attribute;
		this.isConclusion = isConclussion;
		this.possitionOnVector = possitionOnVector;
	}

	@Override
	public int compareTo(AttributeAdditionDetail o) {
		if(o.isConclusion)
			return 1;
		return -1;
	}
}
