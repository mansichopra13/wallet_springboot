package com.wallet.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.AdminDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.BulkUploadResultDTO;
import com.wallet.model.Customer;

public interface AdminService {
	public boolean isValidAdminByEmailidAndPwd(AdminDto adminlogin);
	public Integer createCustomer(@Validated CustomerRequestDto customerRequest);
//	 BulkUploadResultDTO bulkCreateCustomers(MultipartFile file);
	 public Page<Customer> getAllCustomers(String search, Pageable pageable);
}
