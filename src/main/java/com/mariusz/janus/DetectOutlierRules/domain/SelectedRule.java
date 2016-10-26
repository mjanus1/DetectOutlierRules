package com.mariusz.janus.DetectOutlierRules.domain;

import lombok.Getter;
import lombok.Setter;

public class SelectedRule {

	@Getter@Setter private boolean selected;
	@Getter@Setter private Rule rule;
	
	public SelectedRule(boolean selected, Rule rule) {
		this.selected = selected;
		this.rule = rule;
	}
}
