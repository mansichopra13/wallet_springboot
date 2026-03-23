package com.wallet.model;

import java.time.LocalDate;
import java.util.List;

import com.wallet.enums.Gender;
import com.wallet.enums.TransactionType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
