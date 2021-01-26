package com.esprit.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.esprit.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private final JavaMailSender javaMailSender;

	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(String email, String username, String password) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("Your esprit password");
		msg.setText("You can use your email or the username " + username + " as login, with the password is: " + password);
		javaMailSender.send(msg);
	}

}