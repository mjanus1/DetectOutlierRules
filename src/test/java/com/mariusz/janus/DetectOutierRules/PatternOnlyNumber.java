package com.mariusz.janus.DetectOutierRules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mariusz.janus.DetectOutlierRules.Validator.InputForCountOutlier;


public class PatternOnlyNumber {

	private static InputForCountOutlier testInput;
	
	@Before
	public void init() {
		testInput = new InputForCountOutlier();
	}
	
	@Test
	public void shouldEqulsTrueForNumber() {
		Assert.assertEquals(true, testInput.checkIsNumberOfCountNotString("124"));
	}
	
	@Test
	public void shouldEqulsTrueForHugeNumber() {
		Assert.assertEquals(false, testInput.checkIsNumberOfCountNotString("44124"));
	}
	
	@Test
	public void shouldEqulsFalseWhenFirstZero() {
		Assert.assertEquals(false, testInput.checkIsNumberOfCountNotString("046"));
	}
	
	@Test
	public void shouldEqulsFalseForNumberAndString() {
		Assert.assertEquals(false, testInput.checkIsNumberOfCountNotString("12s4"));
	}
	
	@Test
	public void shouldEqulsFalseForNumric() {
		Assert.assertEquals(false, testInput.checkIsNumberOfCountNotString("12,5"));
	}
	
	@Test
	public void shouldEqulsFalseForNumric2() {
		Assert.assertEquals(false, testInput.checkIsNumberOfCountNotString("12.5"));
	}
}

