package com.emp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emp.dto.common.ApiResponse;
import com.emp.dto.request.CustomerLoginDTO;
import com.emp.dto.request.CustomerRequestDto;
import com.emp.dto.response.BulkUploadResultDTO;
import com.emp.dto.response.CustomerFnmLnmGenderDTO;
import com.emp.enums.Gender;
import com.emp.model.Customer;
import com.emp.model.Address;
import com.emp.services.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/cust")
public class CustomerController {
	@Autowired
	private CustomerService cs;
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@PostMapping(value="/create")
	public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequest){
		log.info("Created methoed inside customer controller");
		Integer generatedCustomerId = cs.createCustomer(customerRequest);
//		ApiResponse apiresponse = new ApiResponse(HttpStatus.OK.value(),"Customer created successfully",generatedCustomerId);
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Customer created successfully").data(generatedCustomerId).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	@PostMapping(value="/isValidUser")
	public ResponseEntity<ApiResponse> isValidUser(@RequestBody @Valid CustomerLoginDTO customerLogin){
		log.info("Authenticating user - valid or not");
		boolean res = cs.isValidCustByEmailidAndPwd(customerLogin);
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("User is Validated").data(res).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByLastname/{lnm}")
	public  ResponseEntity<ApiResponse> findByLastnameDTOResponse(@PathVariable String lnm)
	{
		log.info("inside controller findByLastname lnm="+ lnm);
		List<CustomerFnmLnmGenderDTO> lst=cs.findByLastname(lnm);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("User is Validated").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}	
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Customer Found").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByFirstNameIgnoreCase/{fn}")
	public ResponseEntity<ApiResponse> findByFirstNameIgnoreCase(@PathVariable String fn)
	{
		log.info("inside controller findByFirstNameIgnoreCase fn="+ fn);
		List<Customer> lst=cs.findByFirstNameIgnoreCase(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Customer Not Found").build();
			return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.NOT_FOUND);
		}	
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Customer Found").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/firstNameLike/{fnm}")
	public ResponseEntity<ApiResponse> findByFirstNameLike(@PathVariable String fnm){
		log.info("inside controller findByFirstNameLike fn="+fnm);
		List<Customer> lst = cs.findByFirstNameLike(fnm);
		if(lst.isEmpty()) {
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Customer Not Found").build();
			return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.NOT_FOUND);
		}
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.FOUND.value()).message("Founded Customer").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByFirstNameContaining/{fn}")
	public  ResponseEntity<ApiResponse> findByFirstNameContaining(@PathVariable String fn)
	{
		log.info("inside controller findByFirstNameContaining fn="+ fn);
		List<Customer> lst=cs.findByFirstNameContaining(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Customer Not Found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.FOUND.value()).message("Founded Customer").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	@GetMapping(value="/get/findByfirstNameContains/{fn}")
	public  ResponseEntity<ApiResponse> findByfirstNameContains(@PathVariable String fn)
	{
		log.info("inside controller findByfirstNameContains fn="+ fn);
		List<Customer> lst=cs.findByfirstNameContains(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Customer Not Found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.FOUND.value()).message("Founded Customer").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByfirstNameIsContaining/{fn}")
	public  ResponseEntity<ApiResponse> findByfirstNameIsContaining(@PathVariable String fn)
	{
		log.info("inside controller findByfirstNameIsContaining fn="+ fn);
		List<Customer> lst=cs.findByfirstNameIsContaining(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Customer Not Found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
			ApiResponse apiresponse=ApiResponse.builder().code(HttpStatus.FOUND.value()).message("Founded Customer").data(lst).build();
			return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	@GetMapping(value="/create")
	public Customer createcustomer() {
	//	Customer c1 = new Customer("mansi","chopra","mansi@gmail.com","9050090500",new Address(0,"pnp","haryana","pnp","haryana","132103"),Gender.FEMALE,"icsd",LocalDate.now());
		
		Customer c1= Customer.builder()
				.firstname("maya")
				.lastname("narang")
				.emailid("maya@gmail.com")
				.contact("1234567891")
				.address(Address.builder().addressline1("d").addressline2("k").city("p").state("j").pincode("123").build())
				.gendervalue(Gender.FEMALE)
				.password("sim")
				.registerationdate(LocalDate.now())
				.build();
		
		cs.saveCustomer(c1);
		return c1;
	}
	
	
	@GetMapping(value="/findmail/{email}")
	public Customer findmail(@PathVariable String email) {
		Customer c1 = cs.getCustomerByEmailid(email);
		return c1;
	}
	
	@GetMapping(value="/findcid/{cid}")
	public Customer findcid(@PathVariable String cid) {
		Customer c1 = cs.getCustomerByCustId(cid);
		return c1;
	}
	
	@GetMapping(value="/like/{fn}")
	public List<Customer> nameLike(@PathVariable String fn){
		return cs.findByFirstNameLike(fn);
	}
	
	@GetMapping(value="/containing/{fn}")
	public List<Customer> containing(@PathVariable String fn){
		return cs.findByFirstNameContaining(fn);
	}
	
	@GetMapping(value="/ignore/{fn}")
	public List<Customer> ignore(@PathVariable String fn){
		return cs.findByFirstNameIgnoreCase(fn);
	}
	
	@GetMapping(value="/exists/{fn}")
	public boolean existsByEmailAndPassword(@PathVariable String email,@PathVariable String password){
		return cs.existsByEmailIdAndPassword(email,password);
	}
	
	@GetMapping(value="/register")
	public Customer registercustomer() {
	//	Customer c1 = new Customer("mansi","chopra","mansi@gmail.com","9050090500",new Address(0,"pnp","haryana","pnp","haryana","132103"),Gender.FEMALE,"icsd",LocalDate.now());
		
		Customer c1= Customer.builder()
				.firstname("maya")
				.lastname("narang")
				.emailid("maya@gmail.com")
				.contact("1234567891")
				.address(Address.builder().addressline1("d").addressline2("k").city("p").state("j").pincode("123").build())
				.gendervalue(Gender.FEMALE)
				.password("sim")
				.registerationdate(LocalDate.now())
				.build();
		cs.registerCustomer(c1);
		return c1;
	}
	
	@GetMapping(value="/getcust/{fn}")
	public Customer getCustomer(@PathVariable String email,@PathVariable String password){
		return cs.getCustomerByEmailAndPassword(email,password);
	}
	
	@GetMapping(value="/existsbycid/{fn}")
	public boolean existsBycid(@PathVariable int customerid){
		return cs.isCustomerExistsByID(customerid);
	}
	
	@GetMapping(value="/getcustbyemail/{fn}")
	public Customer getCustomerByEmail(@PathVariable String email){
		return cs.getCustomerByEmailId(email);
	}
	
	@GetMapping(value="/getcustbycid/{fn}")
	public Customer getCustomerByCid(@PathVariable int customerid){
		return cs.getCustomerByCustomerId(customerid);
	}
	
	@PostMapping(value="/bulk-upload", consumes="multipart/form-data")
	public ResponseEntity<ApiResponse> bulkUpload(@RequestParam("file") MultipartFile file){
		log.info("Bulk upload triggered");
		
		if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".xlsx")) { 
			ApiResponse apiresponse = ApiResponse.builder()
		            .code(HttpStatus.BAD_REQUEST.value())
		            .message("Please upload a valid .xlsx file")
		            .build();
		        return new ResponseEntity<>(apiresponse, HttpStatus.BAD_REQUEST);
		}
		
		BulkUploadResultDTO result = cs.bulkCreateCustomers(file);
		
		String message = String.format("Upload complete. success : %d, Failed: %d",result.getSuccessCount(),result.getFailureCount() );
		 ApiResponse apiresponse = ApiResponse.builder()
			        .code(HttpStatus.OK.value())
			        .message(message)
			        .data(result)
			        .build();
			    return new ResponseEntity<>(apiresponse, HttpStatus.OK);
	}
}
