package com.emp.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp.enums.AccountType;
import com.emp.model.Account;

// USEFUL
//@Query("SELECT u from User u WHERE upper(u.lastName) like %:lastName% ")
//List<User> findAllCustom(@Param("lastName") String lastName);
//
//@Query(value = "SELECT count(*) from TABLE_USER", nativeQuery = true)
//int getAllUserCount();

public interface AccountRepo extends JpaRepository<Account,Integer> {

	List<Account> findByCustomerCustomerid(int intCustId);

	List<Account> findByCustomerEmailid(String emid);

   // @Query("SELECT a FROM Account a WHERE a.accountnumber = :accountNumber")
    Account findByAccountnumber(int accountNumber);

	List<Account> findDistinctByOpeningbalance(double openingBalance);

	List<Account> findByOpeningbalanceLessThan(double openingBalance);

	List<Account> findByOpeningbalanceLessThanEqual(double openingBalance);

	List<Account> findDistinctByTypeofaccountAndOpeningbalance(AccountType accType, double openingBalance);

	List<Account> findByOpeningbalanceGreaterThan(double openingBalance);
	@Query("select distinct a.typeofaccount from Account a")
	List<String> findDistinctByTypeofaccount();

	List<Account> findByOpeningdateBetween(LocalDate startDate, LocalDate endDate);

	List<Account> findByOpeningdateAfter(LocalDate dt);

	List<Account> findByOrderByOpeningbalanceAsc();

	List<Account> findByOpeningbalanceNot(double ob);

	List<Account> findByTypeofaccountIn(List<AccountType> accTypes);
	
	
	
}
