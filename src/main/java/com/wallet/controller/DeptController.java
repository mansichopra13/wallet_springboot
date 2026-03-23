package com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.model.DeptModel;
import com.wallet.services.DeptService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(value="*")
@RequestMapping(value="/dept")
public class DeptController {
	@Autowired
	DeptService deptService;
	
	@GetMapping(value="/create")
	public DeptModel saveDept()//@RequestBody DeptModel dept)
	{
		DeptModel dept1=new DeptModel(10, "ACCOUNTING", "NEW YORK");
		DeptModel dept2=new DeptModel(20, "RESEARCH", "DALLAS");
		DeptModel dept3=new DeptModel(30, "SALES", "CHICAGO");
		DeptModel dept4=new DeptModel(40, "OPERATIONS", "BOSTON");
		
		deptService.saveDept(dept1);
		deptService.saveDept(dept2);
		deptService.saveDept(dept3);
		DeptModel d=deptService.saveDept(dept4);
		return d;
	}

	@GetMapping(value="/get")
	public List<DeptModel> getAllDepts()
	{
		return deptService.getAllDepts();
	}
}
