package com.wallet.services;

import java.util.List;

//import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.wallet.model.EmpModel;

public interface EmpService {
	public EmpModel saveEmp(EmpModel e);
	public Slice<EmpModel> findAllByDeptmodelDeptid(int dno);
	public List<EmpModel> getAllEmps();
	public EmpModel getEmpByEmpno(int empno);
	public EmpModel removeEmp(int empno);
	public boolean existByEname(String ename);
    List<EmpModel> findAllByJob(String job);
    Page<EmpModel> findByJob(String job,Pageable pagination);
	//Slice<EmpModel> findAllByDeptmodelDeptid(int dno);
	
	
}
