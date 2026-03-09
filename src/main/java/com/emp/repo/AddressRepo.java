package com.emp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp.model.Address;

public interface AddressRepo extends JpaRepository<Address,Integer> {

	Address findByAddressid(int addressId);
	
	@Query("select a from Address a where a.addressline2 is null")
	List<Address> findByAddressline2IsNull();
	
	@Query("select a from Address a where a.addressline2 is not null")
	List<Address> findByAddressline2IsNotNull();

}
