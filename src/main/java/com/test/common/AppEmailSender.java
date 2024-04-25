package com.test.common;

import com.test.common.AppEmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AppEmailSender {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MyUtility myUtils;
	
	private String SUBLECT_OTP = "TestApplication security code";
	private String SUBLECT_FORGOT_PWD = "TestApplication Forgot Passwort";
	private String TEXT_OTP = "<div>Your TestApplication OTP: <h3>USER_OTP</h3></div>";
	
	public String sendOTP(String toEmail) throws MessagingException {
		String otp = myUtils.get6DigitRandomNumber();
		String text = TEXT_OTP.replace("USER_OTP", otp);
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(toEmail);
		helper.setSubject(SUBLECT_OTP);
		helper.setText(text, true);
		javaMailSender.send(msg);
		return otp;
	}

	public void sendSimpleEmail(String toEmail, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

	public boolean sendForgotPassword(String toEmail, String appURL)  {
		boolean status = false;
		String text = "<div style='padding: 20px;'>Hi: <strong>"+toEmail+"</strong>, "
				+ "<br/>Click the link below to reset your RechargeNow password<br/><br/> "
				+ "<a href='" +appURL+ "'><strong>Reset Passwort</strong></a></div>";
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(toEmail);
			helper.setSubject(SUBLECT_FORGOT_PWD);
			helper.setText(text, true);
			javaMailSender.send(msg);
			status = true;
		}catch(MessagingException ex) {
			ex.printStackTrace();
		}
		return status;
	}
}
