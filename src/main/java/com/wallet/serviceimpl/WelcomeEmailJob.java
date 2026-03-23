package com.wallet.serviceimpl;

import java.time.LocalDate;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.enums.PlanType;
import com.wallet.services.EmailService;

@RestController
public class WelcomeEmailJob implements Job{

	@Autowired
	private EmailService emailservice;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		
		String toEmail= map.getString("toEmail");
		String name = map.getString("name");
		String planName = map.getString("plan");
		String startStr = map.getString("startDate");
		String expiryStr = map.getString("expiryDate");
		
		PlanType plan = PlanType.valueOf(planName);
		LocalDate startDate = LocalDate.parse(startStr);
		LocalDate expiryDate = LocalDate.parse(expiryStr);
		
		emailservice.sendWelcomeEmail(toEmail, name, plan, startDate, expiryDate);
	}

}
