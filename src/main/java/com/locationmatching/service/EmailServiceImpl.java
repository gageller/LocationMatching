package com.locationmatching.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.locationmatching.util.GlobalVars;
import com.sun.xml.internal.fastinfoset.util.StringArray;

@Service
public class EmailServiceImpl implements EmailService {

	@Override
	public void sendEmailWithInlinePicture(StringArray toEmailAddresses,
			String imageFilePath, String subject, String bodyText, String fromAddress) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		MimeMessage message;
		MimeMessageHelper messageHelper;
		StringBuilder toAddresses;
		boolean addAddressDelimiter = false;
		
		try {
			toAddresses = new StringBuilder();
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");

			sender.setJavaMailProperties(props);
			sender.setHost(GlobalVars.EMAIL_HOST_SERVER);
			sender.setPort(GlobalVars.EMAIL_HOST_PORT);
			sender.setUsername(GlobalVars.ADMIN_EMAIL_NAME);
			sender.setPassword(GlobalVars.ADMIN_EMAIL_PASSWORD);
	
			message = sender.createMimeMessage();
			
			// Loop through the toEmailAddresses array to create the toAddresses string.
			for(int index = 0; index < toEmailAddresses.getSize(); index++) {
				if(addAddressDelimiter == true) {
					toAddresses.append(";");
				}
				toAddresses.append(toEmailAddresses.get(index));
				addAddressDelimiter = true;
			}
			// use the true flag to indicate you need a multipart message
			messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(toAddresses.toString());
			messageHelper.setFrom(fromAddress);
			messageHelper.setSubject(subject);
			messageHelper.setText(bodyText, true);
			
			// Setup inline image
			FileSystemResource image = new FileSystemResource(imageFilePath);
			messageHelper.addInline("inlineImage", image);
			
			sender.send(message);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendEmail(StringArray toEmailAddresses, String subject,	String bodyText, String fromAddress) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		MimeMessage message;
		MimeMessageHelper messageHelper;
		StringBuilder toAddresses;
		boolean addAddressDelimiter = false;
		
		try {
			toAddresses = new StringBuilder();
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");

			sender.setJavaMailProperties(props);
			sender.setHost(GlobalVars.EMAIL_HOST_SERVER);
			sender.setPort(GlobalVars.EMAIL_HOST_PORT);
			sender.setUsername(GlobalVars.SUPPORT_EMAIL_ADDRESS);
			sender.setPassword(GlobalVars.SUPPORT_EMAIL_PASSWORD);
	
			message = sender.createMimeMessage();
			
			// Loop through the toEmailAddresses array to create the toAddresses string.
			for(int index = 0; index < toEmailAddresses.getSize(); index++) {
				if(addAddressDelimiter == true) {
					toAddresses.append(";");
				}
				toAddresses.append(toEmailAddresses.get(index));
				addAddressDelimiter = true;
			}
			// use the true flag to indicate you need a multipart message
			messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(toAddresses.toString());
			messageHelper.setFrom(fromAddress);
			messageHelper.setSubject(subject);
			messageHelper.setText(bodyText, true);
			
			sender.send(message);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

}
