package com.wallet.repo;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.model.EmpModel;

public interface EmployeeRepo extends JpaRepository<EmpModel,Integer>{

//	Slice<EmpModel> findAllByDeptmodelDeptno(int dno);

	Slice<EmpModel> findAllByDeptmodelDeptid(int dno);



	EmpModel findByEname(String ename);



	List<EmpModel> findAllByJob(String job);

	


}
