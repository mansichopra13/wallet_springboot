package com.emp.dto.response;

import com.emp.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface CustomerFnmLnmGenderDTO {
    String getFirstname();
    String getLastname();
    Gender getGendervalue();
}
