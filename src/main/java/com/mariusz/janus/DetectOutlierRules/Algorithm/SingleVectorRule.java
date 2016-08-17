package com.mariusz.janus.DetectOutlierRules.Algorithm;

public class SingleVectorRule {

	private final static String defaulValue = "0";
	private final int countRows = 2;
	private int countColumn;
	private int idRules;
	private String[][] vectorRule;

	public SingleVectorRule(int coutColumn, int idRules) {
		this.countColumn = coutColumn;
		this.setIdRules(idRules);
		setDefaultValueForVectorRule(countColumn);
	}

	private void setDefaultValueForVectorRule(int coutColumn) {
		vectorRule = new String[countRows][coutColumn];
		for (int i = 0; i < countRows; i++) {
			for (int j = 0; j < countColumn; j++) {
				vectorRule[i][j] = defaulValue;
			}
		}
	}

	public void printVector() {
		for (int i = 0; i < countRows; i++) {
			for (int j = 0; j < countColumn; j++) {
				if (i == 0 && j == 0) {
					System.out.print("Warunek ");
				}
				if (i == 1 && j == 0) {
					System.out.print("Decyzja ");
				}
				System.out.print(vectorRule[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public int getCountColumn() {
		return countColumn;
	}

	public void setCountColumn(int countColumn) {
		this.countColumn = countColumn;
	}

	public String[][] getVectorRule() {
		return vectorRule;
	}

	public void setVectorRule(String[][] vectorRule) {
		this.vectorRule = vectorRule;
	}

	public int getIdRules() {
		return idRules;
	}

	public void setIdRules(int idRules) {
		this.idRules = idRules;
	}

}
