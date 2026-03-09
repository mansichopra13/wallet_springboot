package com.emp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.model.DeptModel;
import com.emp.model.EmpModel;
import com.emp.services.EmpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(value="*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/emp")
public class EmployeeController {
	@Autowired
	EmpService es ;
	
	@GetMapping(value="/create")
	public EmpModel saveEmp() {

		EmpModel e1=new EmpModel(7369, "SMITH", "CLERK", 7902, LocalDate.of(1980, 12, 17), 800.0, 0.0, new DeptModel(20, null, null));
		EmpModel e2=new EmpModel(7499, "ALLEN", "SALESMAN", 7698, LocalDate.of(1981, 2, 20), 1600.0, 300.0, new DeptModel(30, null, null));
		EmpModel e3=new EmpModel(7521, "WARD", "SALESMAN", 7698, LocalDate.of(1981, 2, 22), 1250.0, 500.0, new DeptModel(30, null, null));
		EmpModel e4=new EmpModel(7566, "JONES", "MANAGER", 7839, LocalDate.of(1981, 04, 02), 2975.0, 0, new DeptModel(20, null, null));
		EmpModel e5=new EmpModel(7654, "MARTIN", "CLERK", 7698, LocalDate.of(1981, 9, 28), 1250.0, 1400.0,  new DeptModel(30, null, null));
		EmpModel e6=new EmpModel(7698, "BLAKE", "CLERK", 7839, LocalDate.of(1981, 5, 1), 2850.0, 0.0,  new DeptModel(30, null, null));
		EmpModel e7=new EmpModel(7782, "CLARK", "CLERK", 7839, LocalDate.of(1981, 6, 9), 2450.0, 0.0,  new DeptModel(10, null, null));
		EmpModel e9=new EmpModel(7788, "SCOTT", "CLERK", 7566, LocalDate.of(1982, 12, 9), 3000.0, 0.0,  new DeptModel(20, null, null));
		EmpModel e10=new EmpModel(7839, "KING", "PRESIDENT", 0, LocalDate.of(1981, 11, 17), 5000.0, 0.0,  new DeptModel(10, null, null));
		EmpModel e11=new EmpModel(7844, "TURNER", "SALESMAN", 7698, LocalDate.of(1981, 9, 8), 1500.0, 0.0,  new DeptModel(30, null, null));
		EmpModel e12=new EmpModel(7876, "ADAMS", "CLERK", 7788, LocalDate.of(1983, 1, 12), 1100.0, 0.0,  new DeptModel(20, null, null));
		EmpModel e13=new EmpModel(7900, "JAMES", "CLERK", 7698, LocalDate.of(1981, 12, 3), 950.0,0.0,  new DeptModel(30, null, null));
		EmpModel e14=new EmpModel(7902, "FORD", "ANALYST", 7566, LocalDate.of(1980, 12, 3), 3000.0, 0.0,  new DeptModel(20, null, null));
		EmpModel e8=new EmpModel(7934, "MILLER", "CLERK", 7782, LocalDate.of(1980, 1, 23), 1300.0, 0.0,  new DeptModel(10, null, null));
		
		es.saveEmp(e1);
		es.saveEmp(e2);
		es.saveEmp(e3);
		es.saveEmp(e4);
		es.saveEmp(e5);
		es.saveEmp(e6);
		es.saveEmp(e7);
		es.saveEmp(e8);
		es.saveEmp(e9);
		es.saveEmp(e10);
		es.saveEmp(e11);
		es.saveEmp(e12);
		es.saveEmp(e13);
		
		EmpModel e=	es.saveEmp(e14);
		return e;
	}
	
	@GetMapping(value="get/findAllByDeptnoSlice/{dno}")
	public Slice<EmpModel> findAllByDeptnoSlice(@PathVariable int  dno)
	{
		return es.findAllByDeptmodelDeptid(dno);
	}
	
	@GetMapping("/allemp")
	public List<EmpModel> getListOfEmp(){
		return es.getAllEmps();
	}
	
	@GetMapping(value="getEmpByEmpno/{empno}")
	public EmpModel getEmpByEmpno(@PathVariable int empno) {	
		return es.getEmpByEmpno(empno);	
	}
	
	@GetMapping(value="removeEmpByEmpno/{empno}")
	public EmpModel removeEmpByEmpno(@PathVariable int empno) {	
		return es.removeEmp(empno);
	}
	
	@GetMapping(value="getEmpByEname/{ename}")
	public boolean getEmpByEname(@PathVariable String ename) {
		String upper = ename.toUpperCase();
		return es.existByEname(upper);
	}
	
	@GetMapping("byjob/{job}")
	public List<EmpModel> byjob(@PathVariable String job){
		String upper = job.toUpperCase();
		return es.findAllByJob(upper);
	}
}
