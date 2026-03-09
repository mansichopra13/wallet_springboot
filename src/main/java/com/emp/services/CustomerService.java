package com.emp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.emp.dto.response.BulkUploadResultDTO;
import com.emp.dto.response.CustomerFnmLnmGenderDTO;
import com.emp.dto.request.CustomerLoginDTO;
import com.emp.dto.request.CustomerRequestDto;
import com.emp.model.Customer;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {
	
	public boolean isValidCustByEmailidAndPwd(CustomerLoginDTO customerlogin); 
	public Customer saveCustomer(Customer cust);
	public Customer getCustomerByEmailid(String strEmailId);
	public Customer getCustomerByCustId(String strCustId);
	public Integer createCustomer(@Validated CustomerRequestDto customerRequest);
	public List<Customer> findByFirstNameLike(String fn);
	List<Customer> findByFirstNameContaining(String fnm);
	List<Customer> findByfirstNameContains(String fnm);
	List<Customer> findByfirstNameIsContaining(String fnm);
	List<Customer> findByFirstNameIgnoreCase(String fn);
	List<CustomerFnmLnmGenderDTO> findByLastname(String lnm);
	Boolean existsByEmailIdAndPassword(String email, String password);
	
	Customer registerCustomer(Customer customer);

    Customer getCustomerByEmailAndPassword(String email, String password);

//    Customer createCustomer(@Valid CustomerRequestDto customerRequest) throws SchedulerException;

    Boolean isCustomerExistsByID(int customerID);

    Customer getCustomerByEmailId(String strEmailId);

    Customer getCustomerByCustomerId(int strCustomerId);

    List<Customer> getCustomerExpiringTodayOrTomorrow();
	
    BulkUploadResultDTO bulkCreateCustomers(MultipartFile file);
//    InputStreamResource CreateSampleSheet() throws IOException;
//
//
//    ArrayList<String> sendBulkData(MultipartFile multipartFile) throws IOException, InvalidFormatException, SchedulerException;
//
//    Document CreateCustomerPDF(int customerId, HttpServletResponse response) throws IOException;
//
//    ArrayList<String> sheetCalc(XSSFSheet sheet) throws SchedulerException;
	
}
