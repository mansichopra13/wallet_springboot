package com.wallet.dto.request;

import java.time.LocalDate;

import com.wallet.enums.Gender;
import com.wallet.enums.TransactionType;
import com.wallet.model.Account;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDepositRequestDTO {
	@Min(value=1,message="customer id should not be 0 ")
	private Integer  customerid;
	@Min(value=1,message="Account number should not be 0 ")
	private Integer  accountnumber;
	@Min(value = 1 ,message = "amount should be bw 1-1000000")
	@Max(value=10000 ,message = "amount should be between 1-10000")
	private double amount;
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	private LocalDate transactiondate;
	@NotNull(message="fromAccount details are required")
	private Account fromAccount;	
	@NotNull(message="toAccount details are required")
	private Account toAccount;
}
