package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.model.DeptModel;

public interface DeptRepo extends JpaRepository<DeptModel,Integer>{

}
