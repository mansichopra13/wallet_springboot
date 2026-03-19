package com.cust.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.add.model.Addresstable;
import com.cust.dto.AddressRequesttDto;
import com.cust.dto.CustomerRequestDto;
import com.cust.model.Customertable;
import com.cust.repo.CustomerTableRepo;

@Service
public class Customerservice {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerTableRepo ctr;
	 
	public Customertable savecustomer(CustomerRequestDto customerTable) {
		
		Customertable customer = new Customertable();
		customer.setFirstname(customerTable.getFirstname());
		customer.setLastname(customerTable.getLastname());
		customer.setEmailid(customerTable.getEmailid());
	    customer.setContact(customerTable.getContact());
	    customer.setPassword(customerTable.getPassword());
	    customer.setRegisterationdate(LocalDate.now());
		
		
		Customertable savedCustomer = ctr.save(customer);
		
		AddressRequesttDto addressDto= new AddressRequesttDto() ;
		addressDto.setCustomerid(savedCustomer.getCustomerid());
		addressDto.setAddressline1(customerTable.getAddressline1());
		addressDto.setAddressline2(customerTable.getAddressline2());
		addressDto.setCity(customerTable.getCity());
		addressDto.setState(customerTable.getState());
		addressDto.setPincode(customerTable.getPincode());
		
		try {
			restTemplate.postForObject("http://localhost:8008/addresstable/saveadd", addressDto, String.class);
			
		}catch(Exception e) {
			 System.err.println("Address service unavailable: " + e.getMessage());
			 e.printStackTrace();
		}
		
		return savedCustomer;
	}
	

	
}
