package com.cust.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cust.model.Customertable;



public interface CustomerTableRepo  extends JpaRepository<Customertable,Integer>{

}
