package com.wallet.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wallet.dto.response.CustomerFnmLnmGenderDTO;
import com.wallet.enums.PlanType;
import com.wallet.model.Customer;

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

	@Query(value="select LOWER(c.emailid) from Customer c where LOWER(c.emailid) IN :emails",nativeQuery=true)
	Set<String> findEmailsByEmailidIn(@Param("emails") Set<String> emails);
	
	@Query(value="select c.contact from Customer c where c.contact IN :contacts" , nativeQuery=true)
	Set<String> findContactsByContactIn(@Param("contacts")Set<String> contacts);
	
//	boolean existsByEmailid(String emailid);
//	boolean existsByContact(String contact);
	
	@Query(value = "SELECT COUNT(*) FROM customer WHERE emailid = :emailid", nativeQuery = true)
	int countByEmailidNative(@Param("emailid") String emailid);

	// Same for contact
	@Query(value = "SELECT COUNT(*) FROM customer WHERE contact = :contact", nativeQuery = true)
	int countByContactNative(@Param("contact") String contact);

	@Query("select c from Customer c where lower(c.firstname) like lower(concat('%', :kw ,'%')) OR lower(c.lastname) like lower(concat('%',:kw,'%')) or lower(c.emailid) like lower(concat('%',:kw,'%')) or c.contact like concat('%',:kw,'%')")
	Page<Customer> findBySearchKeyword(@Param("kw") String kw, Pageable pageable);

//	Page<Customer> findAll(Pageable pageable);
//	
	
}
