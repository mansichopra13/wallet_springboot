package com.emp.services;

import java.util.List;

import com.emp.model.DeptModel;

public interface DeptService {
	public DeptModel saveDept(DeptModel dept);
	public List<DeptModel> getAllDepts();
}
