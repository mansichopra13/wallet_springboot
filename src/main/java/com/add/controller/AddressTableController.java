package com.add.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.dto.AddressRequesttDto;
//import com.add.dto.AddressTableDTO;
import com.add.model.Addresstable;
import com.add.repo.AddressTableRepo;
//import com.cust.model.Customertable;
//import com.cust.model.Customertable;

@RestController
@RequestMapping("/addresstable")
public class AddressTableController {
	
	@Autowired
	private AddressTableRepo addressTableRepo;
	
	@PostMapping(value="/saveadd")
	public Addresstable saveAddress(@RequestBody AddressRequesttDto dto) {
		Addresstable address = new Addresstable();
		address.setCustomerid(dto.getCustomerid());
		 address.setAddressline1(dto.getAddressline1());
	        address.setAddressline2(dto.getAddressline2());
	        address.setCity(dto.getCity());
	        address.setState(dto.getState());
	        address.setPincode(dto.getPincode());
	        
	  
				return addressTableRepo.save(address);
			
	}
}
