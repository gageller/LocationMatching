package com.locationmatching.enums;

public enum UserType {
	ADMIN(Values.ADMIN),
	PROVIDER(Values.PROVIDER), 
	SCOUT(Values.SCOUT),
	CUSTOMER_SERVICE(Values.CUSTOMER_SERVICE),
	SUPPORT(Values.SUPPORT),
	SALES(Values.SALES),
	USER(Values.USER);
	
	private String value;
	
	UserType(String value) {
		this.value = value;
	}

	public static class Values {
		public static final String ADMIN = "ADMIN";
		public static final String PROVIDER = "PROVIDER";
		public static final String SCOUT = "SCOUT";
		public static final String CUSTOMER_SERVICE = "CUSTOMER_SERVICE";
		public static final String SUPPORT = "SUPPORT";
		public static final String SALES = "SALES";
		public static final String USER = "USER";
	}
}
