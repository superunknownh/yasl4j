package com.github.superunknownh.yasl4j.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testGetString_GivenValidString_MustReturnThatString() {
		String expResult = "Hello world!";
		String result = StringUtils.getString("Hello world!");
		assertEquals(expResult, result);
	}

	@Test
	public void testGetString_GivenInvalidValidString_MustReturnTheDefaultString() {
		String expResult = "Default value.";
		String result = StringUtils.getString(null, "Default value.");
		assertEquals(expResult, result);
	}

	@Test
	public void testGetInt_GivenValidIntegerString_MustReturnThatInteger() {
		int expResult = 12345;
		int result = StringUtils.getInt("12345", 67890);
		assertEquals(expResult, result);
	}

	@Test
	public void testGetInt_GivenInvalidIntegerString_MustReturnTheDefaultInteger() {
		int expResult = 67890;
		int result = StringUtils.getInt("abc1", 67890);
		assertEquals(expResult, result);
	}

	@Test
	public void testGetBoolean_GivenValidBooleanString_MustReturnThatBoolean() {
		boolean expResult = true;
		boolean result = StringUtils.getBoolean("true");
		assertEquals(expResult, result);
	}

	@Test
	public void testGetBoolean_GivenInvalidBooleanString_MustReturnTheDefaultBoolean() {
		boolean expResult = false;
		boolean result = StringUtils.getBoolean("1", false);
		assertEquals(expResult, result);
	}

	@Test
	public void testCutString_GivenALargeString_MustReturnTheStringCutted() {
		String input = "Lorem ipsum dolor sit amet.";
		String expResult = "Lorem ipsum...";
		String result = StringUtils.cutString(input, 11);
		assertEquals(expResult, result);
	}

	@Test
	public void testCutString_GivenALargeString_MustReturnTheWholeString() {
		String input = "Lorem ipsum dolor sit amet.";
		String expResult = input;
		String result = StringUtils.cutString(input, 100);
		assertEquals(expResult, result);
	}

	@Test
	public void testIsNullOrEmpty_GivenAValidString_MustReturnFalse() {
		boolean expResult = false;
		boolean result = StringUtils.isNullOrEmpty("abc");
		assertEquals(expResult, result);
	}

	@Test
	public void testIsNullOrEmpty_GivenANullString_MustReturnTrue() {
		boolean expResult = true;
		boolean result = StringUtils.isNullOrEmpty(null);
		assertEquals(expResult, result);
	}

	@Test
	public void testIsNullOrEmpty_GivenAnEmptyString_MustReturnTrue() {
		boolean expResult = true;
		boolean result = StringUtils.isNullOrEmpty("  ");
		assertEquals(expResult, result);
	}

}
