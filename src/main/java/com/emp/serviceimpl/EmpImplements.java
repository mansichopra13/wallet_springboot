package com.emp.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.emp.model.EmpModel;
import com.emp.repo.EmployeeRepo;
import com.emp.services.EmpService;

@Service
public class EmpImplements implements EmpService{
	
	@Autowired
	EmployeeRepo er;
	
	@Override
	public EmpModel saveEmp(EmpModel e) {
		EmpModel e1= er.save(e);
		return e1;
	}

	@Override
	public Slice<EmpModel> findAllByDeptmodelDeptid(int dno) {
		return er.findAllByDeptmodelDeptid(dno);	
	}

	@Override
	public List<EmpModel> getAllEmps() {
		return er.findAll();
	}

	@Override
	public EmpModel getEmpByEmpno(int empno) {
		Optional<EmpModel> o1 = er.findById(empno);
		EmpModel e1 = o1.get();
		return e1;
	}

	@Override
	public EmpModel removeEmp(int empno) {
		Optional<EmpModel> o1 = er.findById(empno);
		EmpModel e1 = o1.get();
		er.deleteById(empno);
		return e1;
	}

	@Override
	public boolean existByEname(String ename) {
		EmpModel e1 = er.findByEname(ename);
		if(e1!=null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<EmpModel> findAllByJob(String job) {
		
		return er.findAllByJob(job);
	}

	@Override
	public Page<EmpModel> findByJob(String job, Pageable pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
