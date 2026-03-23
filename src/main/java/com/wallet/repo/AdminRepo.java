package com.wallet.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer>{
	
	Optional<Admin> findByAdminemailidAndAdminpassword(String emailid, String password);

}
