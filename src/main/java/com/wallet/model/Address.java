package com.wallet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="adgen")
	 @SequenceGenerator(name="adgen",sequenceName="adseq",allocationSize=1)
	 private int addressid;
	 private String addressline1;
	 private String addressline2;
	 private String city;
	 private String state;
	 private String pincode;
}