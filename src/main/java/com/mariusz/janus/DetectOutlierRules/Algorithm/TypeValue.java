package com.mariusz.janus.DetectOutlierRules.Algorithm;

public enum TypeValue {

	CONTINOUS("continous"), SYMBOLIC("symbolic"), DISCRETE("discrete");

	@SuppressWarnings("unused")
	private String nameTypValue;

	private TypeValue(String nameTypValue) {
		this.nameTypValue = nameTypValue;
	}

	public String getTypValue(TypeValue typ) {
		return typ.name();
	}
}
