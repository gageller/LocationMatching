package com.locationmatching.service;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public interface EmailService {
	public void sendEmailWithInlinePicture(StringArray toEmailAddresses, String relativeUrlImagePath, String subject, String bodyText);
}
