package com.locationmatching.exception;

/**
 * We need to check if the Location being added is a duplicate. We need
 * to throw this exception because we do not want a LocationProvider to 
 * add multiple Location entries that represent the same Location so the
 * user tries to get around the free photo limit per Location.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class DuplicateLocationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String exceptionMessage;
	
	public DuplicateLocationException(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
