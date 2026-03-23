package com.wallet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wallet.exception.ResourceNotFoundException;
import com.wallet.repo.CustomerRepo;

@Aspect
@Component
public class CustomerValidationAspect {

	@Autowired
	private CustomerRepo cr;
	
	@Before("@annotation(com.wallet.annotations.ValidCustomerId)")
	public void validate(JoinPoint jp) {
		String cid = (String) jp.getArgs()[0];
		int customerId = Integer.parseInt(cid);
		if(cr.findByCustomerid(customerId)==null) {
			throw new ResourceNotFoundException("Customer does not exist :"+ cid);
		}
	}
	
}
