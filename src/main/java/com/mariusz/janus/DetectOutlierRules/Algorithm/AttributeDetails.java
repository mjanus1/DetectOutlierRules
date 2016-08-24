package com.mariusz.janus.DetectOutlierRules.Algorithm;

import com.mariusz.janus.DetectOutlierRules.domain.Attribute;

import lombok.Getter;
import lombok.Setter;

public class AttributeDetails {

	@Getter @Setter private Attribute attribute;
	@Getter @Setter private boolean isConclussion;
	@Getter @Setter private int possitionOnVector;
	
	public AttributeDetails(Attribute attribute, boolean isConclussion, int possitionOnVector) {
		this.attribute = attribute;
		this.isConclussion = isConclussion;
		this.possitionOnVector = possitionOnVector;
	}
}
