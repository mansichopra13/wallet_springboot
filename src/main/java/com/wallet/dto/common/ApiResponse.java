/////////////////////////////////////////////////////////////////////////////////////////
package com.wallet.dto.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.enums.Gender;
import com.wallet.model.Account;
import com.wallet.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private Integer code;
	private String message;
	private Object data;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();
}
