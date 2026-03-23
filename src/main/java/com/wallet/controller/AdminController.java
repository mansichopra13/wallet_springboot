package com.wallet.controller;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.AdminDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.BulkUploadResultDTO;
import com.wallet.model.Customer;
import com.wallet.services.AdminService;
import com.wallet.services.CustomerService;
import com.wallet.services.FileService;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/adm")
public class AdminController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AdminService as;
	
	@Autowired
	private CustomerService cs;
	
	@PostMapping(value="/get/all")
	public ResponseEntity<ApiResponse> getAllCustomers(@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int size,
			@RequestParam(defaultValue="customerid") String sortBy,
			@RequestParam(defaultValue="asc") String sortDir,
			@RequestParam(required=false) String search
			){
		
		log.info("Admin fetching all customers");
		
		Pageable pageable = sortDir.equalsIgnoreCase("desc")
				? PageRequest.of(page-1, size,Sort.by(sortBy).descending())
				: PageRequest.of(page-1, size , Sort.by(sortBy).ascending());
		
		Page<Customer> pageResult = cs.getAllCustomers(search,pageable);
		
		if(pageResult.isEmpty()) {
			ApiResponse apiresponse = ApiResponse.builder()
					.code(HttpStatus.NOT_FOUND.value())
					.message("Customers not found")
					.build();
			return new ResponseEntity<>(apiresponse,HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(
				ApiResponse.builder().code(200).message("customers fetched").data(pageResult).build()
				);
		
	}
	
	//check if admin is valid or not
	@PostMapping(value="/isValidAdmin")
	public ResponseEntity<ApiResponse> isValidAdmin(@RequestBody @Valid AdminDto adminlogin){
		log.info("Authenticating user - valid or not");
		boolean res = as.isValidAdminByEmailidAndPwd(adminlogin);
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Validated").data(res).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	
	//download template
	@GetMapping(value="/download-template", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<ClassPathResource> downloadTemplate() throws IOException{
		return fileService.downloadTemplate();
	}
	
	//upload excel
	@PostMapping(value="/bulk-upload", consumes="multipart/form-data")
	public ResponseEntity<ApiResponse> bulkUpload(@RequestParam("file") MultipartFile file){
		log.info(" Admin Bulk upload triggered");
	     return fileService.bulkUpload(file,cs);
	}
	
	//add new customer
	 @PostMapping(value="/create")
	    public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequest) {
	        log.info("Admin creating customer");
	        Integer generatedCustomerId = cs.createCustomer(customerRequest);
	        ApiResponse apiresponse = ApiResponse.builder()
	                .code(HttpStatus.OK.value())
	                .message("Customer created successfully")
	                .data(generatedCustomerId)
	                .build();
	        return new ResponseEntity<>(apiresponse, HttpStatus.OK);
	    }
		
}
