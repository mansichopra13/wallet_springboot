//////////////////////////////////////////////////////

package com.wallet.dto.request;


import com.wallet.enums.Gender;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {
//	@NotNull(message="First name should not be null")
	@NotBlank(message = "First name should not be blank")
	@Size(min = 2,max = 60,message = "Name should be between 2 to 60")
	@Pattern(regexp="^[a-zA-z]+$",message="Firstname can contain letters only")
	public String firstname;
	

//	@NotNull(message="Last name should not be null")
	 @NotBlank(message = "Last name should not be blank")
	 @Size(min = 2, max = 60, message = "Last name should be between 2 to 60 characters")
	 @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters, no special characters or numbers")   
	 private String lastname;
	
	
	@NotBlank(message = "Email should not be blank")
	@Email(message ="not valid email formatrrrrrrrrrrrrrr ")
	private String emailid;
	
	
//	@NotNull(message="contactNo name should not be null")
	@NotBlank(message = "Contact number should not be blank")
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Contact must be exactly 10 digits with no letters or special characters")
	private String contact;
	
	@NotNull(message = "Gender should not be null (accepted values: MALE, FEMALE)")
	private Gender gendervalue;
	
	
	
	
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
	@NotNull(message="password name should not be null")
	@Size(min = 6, max = 20, message = "Password must be between 6 to 20 characters")
	private String  password;
//	@NotBlank(message = "confirmPassword name should not be blank")
//	@NotNull(message="confirmPassword name should not be null")
//	private String confirmPassword;

//	@NotNull(message="addressLine1 name should not be null")
	@NotBlank(message = "Address line 1 should not be blank")
	private String addressline1;
	
//	@NotNull(message="First name should not be null")
	private String addressline2;

//	@NotNull(message="First name should not be null")
	@NotBlank(message = "City should not be blank")
	@Pattern(regexp="^[a-zA-Z ]+$", message = "City must contain only letters, no special characters or numbers")
	private String city;
	
//	@NotNull(message="First name should not be null")
	@NotBlank(message = "State should not be blank")
	@Pattern(regexp="^[a-zA-Z ]+$", message = "State must contain only letters, no special characters or numbers")
	private String state;

//	@NotNull(message="First name should not be null")
	@NotBlank(message = "Pincode should not be blank")
	@Pattern(regexp="^[0-9]{6}$",message="Pincode must be exactly 6 digits")
	private String pincode;
	@Override
	public String toString() {
		return "CustomerRequestDto [firstname=" + firstname + ", lastname=" + lastname + ", emailid=" + emailid
				+ ", contact=" + contact + ", gendervalue=" + gendervalue + ", password=" + password + ", addressline1="
				+ addressline1 + ", addressline2=" + addressline2 + ", city=" + city + ", state=" + state + ", pincode="
				+ pincode + "]";
	}
	
	
		
	
	
	
	}
