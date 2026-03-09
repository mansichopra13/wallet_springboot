package com.emp.model;

import java.time.LocalDate;

import com.emp.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="agen")
	 @SequenceGenerator(name="agen",sequenceName="aseq",allocationSize=1)
	 private int accountnumber;
	// @JsonIgnore - it will not create json for customer
	 @ToString.Exclude
	 @ManyToOne
	 @JoinColumn(name="customerFk")
	 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "accounts"}) 
	 private Customer customer;
	 @Enumerated(EnumType.STRING) 
	 private AccountType typeofaccount;
	 private double openingbalance;
	 private LocalDate openingdate;
	 private String description;
}
