//package com.wallet.annotations;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.wallet.repo.CustomerRepo;
//
//public class CustomerIdValidator implements ConstraintValidator<ValidCustomerId,String>{
//	@Autowired
//	private CustomerRepo cr;
//	
//	@Override
//	public boolean isValid(String cid, ConstraintValidatorContext context) {
//		return cr.findByCustomerid(cid).isPresent();
//	}
//	
//}
