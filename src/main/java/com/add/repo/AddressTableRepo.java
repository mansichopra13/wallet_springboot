package com.add.repo;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.add.dto.AddressTableDTO;
import com.add.model.Addresstable;
//import com.cust.model.Customertable;

public interface AddressTableRepo  extends JpaRepository<Addresstable,Integer> {


	
}
