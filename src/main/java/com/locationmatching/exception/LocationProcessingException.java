package com.locationmatching.exception;

/**
 * This exception is thrown if there is a database problem 
 * when adding, modifying or deleting a location.
 * 
 * @author Greg Geller
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class LocationProcessingException extends RuntimeException {
	String exceptionMessage;
	
	public LocationProcessingException(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}
