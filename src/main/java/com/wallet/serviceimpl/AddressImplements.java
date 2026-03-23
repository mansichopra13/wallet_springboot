package com.wallet.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.model.Address;
import com.wallet.repo.AddressRepo;
import com.wallet.services.AddressService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class AddressImplements implements AddressService{

	@Autowired
	AddressRepo ar;
	
	@Override
	public Address saveAddress(Address add) {
		Address a1= ar.save(add);
		return a1;
	}

	@Override
	public Address getAddressByAddressId(int addressId) {
		Address a1= ar.findByAddressid(addressId);
		return a1;
	}

	@Override
	public List<Address> findByAddressLine2IsNull() {
		return ar.findByAddressline2IsNull();
	}

	@Override
	public List<Address> findByAddressLine2IsNotNull() {
		return ar.findByAddressline2IsNotNull();
	}

}
