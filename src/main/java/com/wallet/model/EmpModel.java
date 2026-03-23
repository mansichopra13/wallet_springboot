package com.wallet.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EMPLOYEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpModel {
	@Id
	private int empno;
	private String ename;
	private String job;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int mgr;//to handle null values - as mgr is having null value 
	private LocalDate hiredate;
	private double sal;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private double comm;//to handle null values - as comm is having null value
	@ManyToOne
	@JoinColumn(name ="deptno")
	private DeptModel deptmodel;
}
