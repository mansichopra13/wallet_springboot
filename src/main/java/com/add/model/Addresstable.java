package com.add.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Addresstable {
	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="addgen")
	 @SequenceGenerator(name="addgen",sequenceName="addseq",allocationSize=1)
	 private int addressid;
	 
	 private int customerid;
	 private String addressline1;
	 private String addressline2;
	 private String city;
	 private String state;
	 private String pincode;
}
