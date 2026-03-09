package com.emp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DEPT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptModel {
	@Id
	private int deptid;
	private String deptname;
	private String deptlocation;
}


