package com.cust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class Customertable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="custgen")
	@SequenceGenerator(name="custgen",sequenceName="custseq",allocationSize=1)
	private int customerid;
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;
	@NonNull
	private String emailid;
	private String contact;
	String password;
	private LocalDate registerationdate;
	
}
