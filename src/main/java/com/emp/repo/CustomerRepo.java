package com.emp.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp.dto.response.CustomerFnmLnmGenderDTO;
import com.emp.enums.PlanType;
import com.emp.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

	Optional<Customer> findByEmailid(String strEmailId);

	Optional<Customer> findByCustomerid(String strCustId);

	@Query("select c from Customer c where c.firstname like ?1%")
	List<Customer> findByFirstnameLike(String fn);
	
	@Query("select c from Customer c where c.firstname like %?1%")
	List<Customer> findByFirstnameContaining(String fnm);
	
	@Query("select c from Customer c where lower(c.firstname) like %?1%")
	List<Customer> findByFirstnameIgnorecase(String lower);

	Optional<Customer> findByEmailidAndPassword(String email, String password);

	Customer findByCustomerid(int customerID);

	List<CustomerFnmLnmGenderDTO> findByLastname(String lnm);
	
	@Query("select c from Customer c where c.firstname like %?1%")
	List<Customer> findByFirstnameContains(String fnm);
	
	@Query("select c from Customer c where c.firstname like %?1%")
	List<Customer> findByFirstnameIsContaining(String fnm);
	
	
	List<Customer> findByPlanExpiryDateAndPlanTypeNot(LocalDate date,PlanType planType);
	List<Customer> findByPlanExpiryDateBeforeAndPlanTypeNot(LocalDate date, PlanType planType);
}
