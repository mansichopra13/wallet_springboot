package com.wallet.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wallet.enums.AccountType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;
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
