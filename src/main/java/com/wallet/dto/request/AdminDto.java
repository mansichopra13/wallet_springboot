package com.wallet.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
	@NotBlank(message = "Email is required")
	@Email(message="Invalid Email")
	public String adminemailid;
	
	@NotBlank(message = "Password is required")
	@Size(min = 2,max = 60,message = "Password should be between 2 to 60")
	public String adminpassword;

	@Override
	public String toString() {
		return "AdminDto [adminemailid=" + adminemailid + ", adminpassword=" + adminpassword + "]";
	}
}
