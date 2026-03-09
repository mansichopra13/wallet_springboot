package com.emp.dto.request;

import java.time.LocalDate;

import com.emp.enums.AccountType;
import com.emp.enums.Gender;
import com.emp.model.Customer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
