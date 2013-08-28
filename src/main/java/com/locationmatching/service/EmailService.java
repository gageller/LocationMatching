package com.locationmatching.service;

public interface EmailService {
	public void sendEmailWithInlinePicture(String toEmailAddress, String relativeUrlImagePath, String subject, String bodyText);
}
