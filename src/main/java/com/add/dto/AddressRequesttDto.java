package com.add.dto;

import lombok.Data;

@Data
public class AddressRequesttDto {
 private int customerid;      
 private String addressline1;
 private String addressline2;
 private String city;
 private String state;
 private String pincode;
}