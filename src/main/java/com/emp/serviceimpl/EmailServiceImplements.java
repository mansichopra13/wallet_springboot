package com.emp.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.emp.enums.PlanType;
import com.emp.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImplements implements EmailService{
	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendWelcomeEmail(String toEmail, String name, PlanType plan, LocalDate startDate,
			LocalDate expiryDate) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject("Welcome! Your"+plan.name()+"Plan is Active");
			msg.setText("Hi!"+name+",\n\n"+"Your"+plan.name()+"plan has started from : "+startDate+"\n"+"Valid upto: "+expiryDate+"\n\n"+"Thank You for registering!\n\nTeam");
			mailSender.send(msg);
			
			log.info("Welcome email sent to: {}", toEmail);
		}
		catch(Exception e) {
			log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
		}
		
	}

	@Override
	public void sendExpiryReminderEmail(String toEmail, String name, LocalDate expiryDate) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(toEmail);
			msg.setSubject("Your Plan Expires Tomorrow!");
			msg.setText("Hi " + name + ",\n\n" +
	                "This is a reminder that your plan expires on: " + expiryDate + "\n" +
	                "Please renew to avoid interruption.\n\nTeam");
			mailSender.send(msg);
			 log.info("Expiry reminder sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send expiry email to {}: {}", toEmail, e.getMessage());
        }
	}
	
	
}
