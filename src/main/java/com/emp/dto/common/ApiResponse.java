/////////////////////////////////////////////////////////////////////////////////////////
package com.emp.dto.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.emp.enums.Gender;
import com.emp.model.Account;
import com.emp.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;

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
