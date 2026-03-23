package com.wallet.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.AdminDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.BulkUploadResultDTO;
import com.wallet.enums.PlanType;
import com.wallet.exception.EntityAlreadyExistException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.model.Address;
import com.wallet.model.Admin;
import com.wallet.model.Customer;
import com.wallet.repo.AddressRepo;
import com.wallet.repo.AdminRepo;
import com.wallet.repo.CustomerRepo;
//import com.wallet.serviceimpl.CustomerImplements.ValidatedRow;
import com.wallet.services.AdminService;
import com.wallet.services.EmailService;

import javax.validation.Valid;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminImplements implements AdminService {
	
	@Autowired
	AdminRepo admr;
	
	@Autowired
	CustomerRepo cr;
	
	@Autowired
	AddressRepo ar;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private EmailService emailService;
	
	
	@Value("${app.plan.basic.expiry-days:15}")
	private int basicPlanExpiryDays;
	
	@Override
	public boolean isValidAdminByEmailidAndPwd(AdminDto adminlogin) {
		boolean res = true;
		Optional<Admin> optCust = admr.findByAdminemailidAndAdminpassword(adminlogin.getAdminemailid(),adminlogin.getAdminpassword());
		if(optCust.isEmpty()) {
			res=false;
			throw new ResourceNotFoundException("admin is not existing for email id "+ adminlogin.getAdminemailid() + " and pwd= "+ adminlogin.getAdminpassword());
		}
		return res;
	}

	@Override
	public Integer createCustomer(@Valid CustomerRequestDto customerRequest) {
		log.info("Inside CustomerService Implementation with request id "+customerRequest);
		
		if (cr.countByEmailidNative(customerRequest.getEmailid()) > 0) {
		    throw new EntityAlreadyExistException("email id is already exists");
		}

		if (cr.countByContactNative(customerRequest.getContact()) > 0) {
		    throw new EntityAlreadyExistException("Contact already exists");
		}
		Address addr = Address.builder()
				.addressline1(customerRequest.getAddressline1())
				.addressline2(customerRequest.getAddressline2())
				.city(customerRequest.getCity())
				.state(customerRequest.getState())
				.pincode(customerRequest.getPincode())
				.build(); 
		Address addCreated=ar.save(addr);
		
		LocalDate startDate = LocalDate.now();
		LocalDate expiryDate=startDate.plusDays(basicPlanExpiryDays);
		//LocalDate expiryDate=startDate.plusDays(15);
		
		Customer c1= Customer.builder()
				.firstname(customerRequest.getFirstname())
				.lastname(customerRequest.getLastname())
				.emailid(customerRequest.getEmailid())
				.contact(customerRequest.getContact())
				.address(addCreated)
				.gendervalue(customerRequest.getGendervalue())
				.password(customerRequest.getPassword())
				.registerationdate(LocalDate.now())
				.planType(PlanType.BASIC)
				.planStartDate(startDate)
				.planExpiryDate(expiryDate)
				.build();
		
		log.info("Cust enttity saved",c1);
		Customer custCreated=cr.save(c1);
		
		emailService.sendWelcomeEmail(custCreated.getEmailid(),
				custCreated.getFirstname(),
				PlanType.BASIC, startDate, expiryDate);
		
		return c1.getCustomerid();
		
	}

//	@Override
//	public BulkUploadResultDTO bulkCreateCustomers(MultipartFile file) {
//		List<BulkUploadResultDTO.RowErrorDTO> errorList = new ArrayList<>();
//		int successCount =0;
//		
//		try(Workbook workbook = new XSSFWorkbook(file.getInputStream())){
//			Sheet sheet = workbook.getSheetAt(0);
//			int totalRows = sheet.getLastRowNum();
//			
//			//basic validation
//			List<ValidatedRow> validRows = parseAndValidateRows(sheet,totalRows,errorList);
//			
//			Set<String> existingEmails = fetchExistingEmailsFromDB(validRows);
//			Set<String> existingContacts = fetchExistingContactsFromDB(validRows);
//			
//			for(ValidatedRow entry : validRows) {
//				List<String> conflicts = findDBConflicts(entry.dto(),existingEmails,existingContacts);
//				
//				if(!conflicts.isEmpty()) {
//					errorList.add(buildError(entry.rowNum(),
//							entry.dto().getEmailid() , conflicts ));
//					continue;
//				}
//				
//				try {
//					createCustomer(entry.dto());
//					successCount++;
//				}catch(Exception e) {
//					errorList.add(buildError(entry.rowNum(), entry.dto().getEmailid(),List.of("Unexpected error :"+ e.getMessage()) ));
//					
//				}
//				
//			}
//			
//			return BulkUploadResultDTO.builder()
//					.totalRows(totalRows)
//					.successCount(successCount)
//					.failureCount(errorList.size())
//					.errors(errorList)
//					.build();			
//		}catch(Exception e) {
//			throw new RuntimeException("Fail to parse Excel file : " +e.getMessage());
//		}
//	}

	@Override
	public Page<Customer> getAllCustomers(String search, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
