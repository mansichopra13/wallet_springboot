package com.emp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Integer>{
	@Query("select t from Transaction t where t.fromAccount  = ?1 or t.toAccount = ?1")
	List<Transaction> findByAccountnumber(int accountNumber);

}
