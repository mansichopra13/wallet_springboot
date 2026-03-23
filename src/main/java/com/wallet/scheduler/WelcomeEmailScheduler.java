package com.wallet.scheduler;

import java.time.LocalDate;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.quartz.*;
import com.wallet.enums.PlanType;
import com.wallet.serviceimpl.WelcomeEmailJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WelcomeEmailScheduler {
	@Autowired 
	private Scheduler scheduler;
	
	@Value("${welcome.email.delay.minutes}")
	private int delayMinutes;
	
	public void scheduledWelcomeEmail(String toEmail, String name , PlanType plan, LocalDate startDate,LocalDate expiryDate) {
		try {
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("toEmail", toEmail);
			  jobDataMap.put("name",       name);
	            jobDataMap.put("plan",       plan.name());
	            jobDataMap.put("startDate",  startDate.toString());
	            jobDataMap.put("expiryDate", expiryDate.toString());
	            
	            JobDetail jobDetail= JobBuilder.newJob(WelcomeEmailJob.class)
	            		.withIdentity("WelcomeEmail_" + toEmail)
	            		.usingJobData(jobDataMap)
	            		.build();
	            
	            Date triggerTime = new Date(System.currentTimeMillis() + (long)delayMinutes*60*1000);
	            
	            Trigger trigger = TriggerBuilder.newTrigger()
	            		.withIdentity("WelcomeEmail_" + toEmail)
	            		.startAt(triggerTime)
	            		.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
	            		.build();
	            
	            scheduler.scheduleJob(jobDetail,trigger);
	            
		}catch(SchedulerException e) {
			log.error("Failed to schedule welcome email: {}", e.getMessage());
		}
	}
}
