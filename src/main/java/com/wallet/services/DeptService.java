package com.wallet.services;

import java.util.List;

import com.wallet.model.DeptModel;

public interface DeptService {
	public DeptModel saveDept(DeptModel dept);
	public List<DeptModel> getAllDepts();
}
