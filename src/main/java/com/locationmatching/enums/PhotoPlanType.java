package com.locationmatching.enums;

public enum PhotoPlanType {
	FREE_PHOTO(Values.FREE_PHOTO), 
	PAID_PHOTO(Values.PAID_PHOTO);
	
	private String value;
	
	private PhotoPlanType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	static class Values {
		public final static String FREE_PHOTO = "Free Photo";
		public final static String PAID_PHOTO = "Paid Photo";
	}
}
