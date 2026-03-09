package com.emp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.emp.enums.Gender;
import com.emp.enums.PlanType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="catgen")
	@SequenceGenerator(name="catgen",sequenceName="catseq",allocationSize=1)
	private int customerid;
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;
	@NonNull
	private String emailid;
	private String contact;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="addressFk")
	private Address address;
	@Enumerated(EnumType.STRING)
	private Gender gendervalue;
	String password;
	
	private LocalDate registerationdate;
	@OneToMany(mappedBy = "customer" , cascade=CascadeType.ALL)
	private List<Account> accounts=new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private PlanType planType;
	
	private LocalDate planStartDate;
	private LocalDate planExpiryDate;

}