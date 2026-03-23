package com.wallet.dto.request;

import java.time.LocalDate;

import com.wallet.enums.AccountType;
import com.wallet.enums.Gender;
import com.wallet.model.Customer;

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
public class AccountRequestDTO {
	
	@NotNull(message="Customer cannot be null")
	public Customer customer;
	@NotNull(message="AccountType is required")
	public AccountType typeofaccount;
	
	@NotNull(message="openingbalance required")
	@Min(value = 1 ,message = "opening balance should be bw 1-1000000")
	@Max(value=1000000 ,message = "opening balance should be between 1-1000000")
	public Double openingbalance;
	public LocalDate openingdate;

	@NotBlank(message="description required")
	public String description;
	@Override
	public String toString() {
		return "AccountRequestDTO [customer=" + customer + ", typeofaccount=" + typeofaccount + ", openingbalance="
				+ openingbalance + ", openingdate=" + openingdate + ", description=" + description + "]";
	}
	
	
}
