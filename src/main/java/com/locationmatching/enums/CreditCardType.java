package com.locationmatching.enums;

public enum CreditCardType {
	BLANK(Values.BLANK), 
	VISA(Values.VISA), 
	MASTERCARD(Values.MASTERCARD), 
	AMERICAN_EXPRESS(Values.AMERICAN_EXPRESS),
	DISCOVER(Values.DISCOVER);
	
	private String value;
	
	private CreditCardType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	static class Values {
		public final static String BLANK = "";
		public final static String VISA = "visa";
		public final static String MASTERCARD = "mastercard";
		public final static String AMERICAN_EXPRESS = "amex";
		public final static String DISCOVER = "discover";
	}
}
