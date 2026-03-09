package com.emp.model;

import java.time.LocalDate;
import java.util.List;

import com.emp.enums.Gender;
import com.emp.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tragen")
	@SequenceGenerator(name="tragen",sequenceName="traseq",allocationSize=1)
	private int transactionid;
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private LocalDate transactiondate;
	private double amount;
	private String description;
	@ManyToOne
	@JoinColumn(name="fromacc")
	private Account fromAccount;
	@ManyToOne
	@JoinColumn(name="toacc")
	private Account toAccount;
}
