package com.emp.services;

import java.time.LocalDate;

import com.emp.enums.PlanType;

public interface EmailService {
	void sendWelcomeEmail(String toEmail,String name,PlanType plan,LocalDate startDate,LocalDate expiryDate);
	void sendExpiryReminderEmail(String toEmail,String name,LocalDate expiryDate);
	
}
