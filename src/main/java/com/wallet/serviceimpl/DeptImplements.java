package com.wallet.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.model.DeptModel;
import com.wallet.repo.DeptRepo;
import com.wallet.services.DeptService;

@Service
public class DeptImplements implements DeptService{

	@Autowired
	DeptRepo dr;
	
	@Override
	public DeptModel saveDept(DeptModel dept) {
		DeptModel d1 = dr.save(dept);
		return d1;
	}

	@Override
	public List<DeptModel> getAllDepts() {
		return dr.findAll();
	}

}
