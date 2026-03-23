package com.wallet.services;

import java.util.List;

import com.wallet.model.Address;

public interface AddressService {
	public Address saveAddress(Address add);
	public Address getAddressByAddressId(int addressId);
	 List<Address> findByAddressLine2IsNull();
	  List<Address> findByAddressLine2IsNotNull();
}
