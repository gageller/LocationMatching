package com.locationmatching.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Override
	public void sendEmailWithInlinePicture(String toEmailAddress,
			String imageFilePath, String subject, String bodyText) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		MimeMessage message;
		MimeMessageHelper messageHelper;
	
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");

			sender.setJavaMailProperties(props);
			sender.setHost("smtp-server.socal.rr.com");
			sender.setPort(587);
			sender.setUsername("gageller");
			sender.setPassword("rayray");
	
			message = sender.createMimeMessage();
			
			// use the true flag to indicate you need a multipart message
			messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(toEmailAddress);
			messageHelper.setFrom("gageller@roadrunner.com");
			messageHelper.setSubject(subject);
			messageHelper.setText(bodyText, true);
			
			// Setup inline image
			FileSystemResource image = new FileSystemResource(imageFilePath);
			messageHelper.addInline("coverImage", image);
			
			sender.send(message);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}


	}

}
