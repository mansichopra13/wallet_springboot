package com.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dto.common.ApiResponse;
import com.emp.model.Address;
import com.emp.model.Customer;
import com.emp.services.AddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	AddressService as;
	
	@GetMapping("/save")
	public Address saveAddress() {
		//Address a1  = new Address(0,"pnp","haryana","pnp","haryana","132103");
		
		Address a1 = Address.builder()
				.addressline1("pnp")
				.addressline2("pnp")
				.city("pnp")
				.state("hr")
				.pincode("112311")
				.build();
		as.saveAddress(a1);
		return a1;
	}
	
	@GetMapping("/aid/{aid}")
	public Address getAddressByAid(@PathVariable int aid) {
		Address a1 = as.getAddressByAddressId(aid);
		return a1;
	}
	
	@GetMapping(value="/nulladdress2")
	public ResponseEntity<ApiResponse> getNullAddressline2(){
	//	return as.findByAddressLine2IsNull();
		ApiResponse api = ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("addwith not nullvalue")
				.data(as.findByAddressLine2IsNull())
				.build();
		return new ResponseEntity<ApiResponse> (api,HttpStatus.OK);
	}
	
	@GetMapping(value="/notnulladdress2")
	public ResponseEntity<ApiResponse> getNotNullAddressline2(){
		//return as.findByAddressLine2IsNotNull();
		
		ApiResponse api = ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("addwith nullvalue")
				.data(as.findByAddressLine2IsNotNull())
				.build();
		return new ResponseEntity<ApiResponse> (api,HttpStatus.OK);
	}
}
