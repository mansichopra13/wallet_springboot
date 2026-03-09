package com.emp.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emp.enums.PlanType;
import com.emp.model.Customer;
import com.emp.repo.CustomerRepo;
import com.emp.services.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanScheduler {
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	EmailService emailService;
	
	@Scheduled(cron="0 * * * * *")
	public void sendOneDayExpiryReminder() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		List<Customer> expiringTomorrow = customerRepo.findByPlanExpiryDateAndPlanTypeNot(tomorrow, PlanType.EXPIRED);
		
		for(Customer c :expiringTomorrow) {
			emailService.sendExpiryReminderEmail(c.getEmailid(),
					c.getFirstname(), c.getPlanExpiryDate());
			 log.info("1-day reminder sent to: {}", c.getEmailid());
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	public void markExpiredPlans() {
		LocalDate today = LocalDate.now();
		
		List<Customer> expired = customerRepo
				.findByPlanExpiryDateBeforeAndPlanTypeNot(today, PlanType.EXPIRED);
		for(Customer c:expired) {
			c.setPlanType(PlanType.EXPIRED);
			customerRepo.save(c);
			 log.info("Plan marked expired for: {}", c.getEmailid());
		}
	}
}
