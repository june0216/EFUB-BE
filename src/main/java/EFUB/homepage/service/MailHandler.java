package EFUB.homepage.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailHandler {
	private JavaMailSender sender;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;

	public MailHandler(JavaMailSender jsender) throws MessagingException {
		this.sender = jsender;
		message = jsender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}

	public void setFrom(String fromAddress) throws MessagingException {
		messageHelper.setFrom(fromAddress);
	}

	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}

	public void setSubject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
	}

	public void setText(String text, boolean useHtml) throws MessagingException {
		messageHelper.setText(text, useHtml);
	}

	public void send() {
		try {
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}