package com.wallet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wallet.exception.ResourceNotFoundException;
import com.wallet.repo.CustomerRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(2)
public class CustomerValidationAspect {

	@Autowired
	private CustomerRepo cr;
	
	@Pointcut("execution(* com.wallet.controller.CustomerController.*(..))")
	public void customerControllerMethods() {}
	
	@Pointcut("execution(* com.wallet.serviceimpl.CustomerImplements.*(..))")
	public void customerServiceMethods() {}
	
	@Before("@annotation(com.wallet.annotations.ValidCustomerId)")
	public void validate(JoinPoint jp) {
		String cid = (String) jp.getArgs()[0];
		int customerId = Integer.parseInt(cid);
		if(cr.findByCustomerid(customerId)==null) {
			throw new ResourceNotFoundException("Customer does not exist :"+ cid);
		}
		log.info("[@Before] Customer Id : {} validated successfully", customerId);
	}
	
	@Around("@annotation(com.wallet.annotations.TrackTime)")
	public Object trackTime (ProceedingJoinPoint pjp) throws Throwable{
		long start =System.currentTimeMillis();
		Object result = pjp.proceed();
		long end =System.currentTimeMillis();
		
		log.info("Method: {}() | Time Taken: {}ms", pjp.getSignature().getName(),(end-start));
		return result;
	}
	
	@After("customerControllerMethods()")
	public void afterMethod(JoinPoint jp) {
		log.info("[@After] Method: {}() finished executing",jp.getSignature().getName());
	}
	
	@AfterReturning(pointcut ="customerControllerMethods()", returning ="result")
	public void afterReturn(JoinPoint jp,Object result) {
		log.info("[@AfterReturning] Method: {}() returned: {}", jp.getSignature().getName(),result);
	}
	
	
	@AfterThrowing(pointcut="customerServiceMethods()",throwing = "ex")
	public void afterThrowing(JoinPoint jp, Exception ex) {
		log.error("[@AfterThrowing] Method: {}() threw: {}",jp.getSignature().getName(),ex.getMessage());
	}
	
}
