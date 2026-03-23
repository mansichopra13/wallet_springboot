package com.wallet.dto.request;


import java.time.LocalDate;

import com.wallet.enums.DocType;
import com.wallet.model.Customer;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocDTO {
	
	 @NotNull(message="Customer cannot be null")
	 public Customer customer;
	 @NotNull(message="Docname cannot be null")
	 private DocType docname;
	 @NotNull(message="Filetype cannot be null")
	 private String filetype;
	 
	 private String fileName;
	 
	 private String filePath;
	
}
