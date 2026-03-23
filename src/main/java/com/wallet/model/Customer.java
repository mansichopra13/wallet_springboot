package com.wallet.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.wallet.enums.Gender;
import com.wallet.enums.PlanType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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