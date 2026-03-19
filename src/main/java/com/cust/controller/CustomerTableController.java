package com.cust.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cust.dto.CustomerRequestDto;
import com.cust.model.Customertable;
import com.cust.repo.CustomerTableRepo;
import com.cust.service.Customerservice;

@RestController
@RequestMapping("/customertable")
public class CustomerTableController {
	
	@Autowired
	private Customerservice customerService;
	
	@PostMapping(value="/savingcustomer")
	public Customertable saveCustomer(@RequestBody CustomerRequestDto customerTable) {
		return customerService.savecustomer(customerTable);
	}
	
}
