package com.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.DeptModel;

public interface DeptRepo extends JpaRepository<DeptModel,Integer>{

}
