package com.wallet.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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


