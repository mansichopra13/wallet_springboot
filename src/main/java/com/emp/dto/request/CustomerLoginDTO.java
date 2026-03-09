package com.emp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoginDTO {
	
	@NotBlank(message = "Email is required")
	@Email(message="Invalid Email")
	public String emailid;
	
	@NotBlank(message = "Password is required")
	@Size(min = 2,max = 60,message = "Password should be between 2 to 60")
	public String password;

	@Override
	public String toString() {
		return "CustomerLoginDTO [emailid=" + emailid + ", password=" + password + "]";
	}
}
