package com.mariusz.janus.DetectOutlierRules.Algorithm;

import lombok.Getter;
import lombok.Setter;

public class SingleVectorRule {

	private final static String defaulValue = "0";
	private final int countRows = 2;
	@Getter @Setter private int countColumn;
	@Getter @Setter private int idRules;
	@Getter @Setter private String[][] vectorRule;

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
	
	public String returnValueOnPossition(int row, int column) {
		return vectorRule[row][column];
	}
	
	public boolean checkIsElementIsZero(int row, int column) {
		return vectorRule[row][column].equals("0") ? true : false;
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
}
