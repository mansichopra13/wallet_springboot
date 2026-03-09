package com.emp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emp.enums.DocType;
import com.emp.model.CustomerDocuments;

public interface CustDocRepo extends JpaRepository<CustomerDocuments,Integer>{

	List<CustomerDocuments> findByCustomer_CustomeridAndStatusTrue(int cid);
	
	@Query("select f from CustomerDocuments f where f.custdocid=?1")
	CustomerDocuments findByDocid(int custdocid);
	
	Optional<CustomerDocuments> findByCustomer_CustomeridAndDocnameAndStatusTrue(int cid,DocType docname);

}
