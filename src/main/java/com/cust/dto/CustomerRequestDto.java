package com.cust.dto;

import lombok.Data;

@Data
public class CustomerRequestDto {
 private String firstname;
 private String lastname;
 private String emailid;
 private String contact;
 private String password;


 private String addressline1;
 private String addressline2;
 private String city;
 private String state;
 private String pincode;
}