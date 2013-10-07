package com.locationmatching.enums;

public enum PhotoStatus {
	NOT_REVIEWED(Values.NOT_REVIEWED), 
	APPROVED(Values.APPROVED),
	DECLINED(Values.DECLINED),
	DELETED(Values.DELETED),
	SKIPPED_ON_REVIEW(Values.SKIPPED_ON_REVIEW);
	
	private String value;
	
	private PhotoStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	static class Values {
		public final static String NOT_REVIEWED = "NOT_REVIEWED";
		public final static String APPROVED = "APPROVED";
		public final static String DECLINED = "DECLINED";
		public final static String DELETED = "DELETED";
		public final static String SKIPPED_ON_REVIEW = "SKIPPED_ON_REVIEW";
	}
}
