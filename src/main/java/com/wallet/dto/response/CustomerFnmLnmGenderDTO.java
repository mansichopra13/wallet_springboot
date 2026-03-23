package com.wallet.dto.response;

import com.wallet.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface CustomerFnmLnmGenderDTO {
    String getFirstname();
    String getLastname();
    Gender getGendervalue();
}
