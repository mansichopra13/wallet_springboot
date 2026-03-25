package com.wallet.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.annotations.TraceAndLogs;
import com.wallet.dto.request.CustomerLoginDTO;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.BulkUploadResultDTO;
import com.wallet.dto.response.CustomerFnmLnmGenderDTO;
import com.wallet.enums.Gender;
import com.wallet.enums.PlanType;
import com.wallet.exception.EntityAlreadyExistException;
import com.wallet.exception.ResourceNotFoundException;
import com.wallet.model.Address;
import com.wallet.model.Customer;
import com.wallet.repo.AddressRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.scheduler.WelcomeEmailScheduler;
import com.wallet.services.CustomerService;
import com.wallet.services.EmailService;

import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
//import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerImplements implements CustomerService{
	
	@Autowired
	CustomerRepo cr;
	
	@Autowired
	AddressRepo ar;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Validator validator;
	
	@Value("${app.plan.basic.expiry-days:15}")
	private int basicPlanExpiryDays;
	
	@Autowired
	private WelcomeEmailScheduler welcomeEmailScheduler;
		
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean isValidCustByEmailidAndPwd(CustomerLoginDTO customerlogin) {
		boolean res = true;
		Optional<Customer> optCust = cr.findByEmailidAndPassword(customerlogin.getEmailid(),customerlogin.getPassword());
		if(optCust.isEmpty()) {
			res=false;
			throw new ResourceNotFoundException("customer is not existing for email id "+ customerlogin.getEmailid() + " and pwd= "+ customerlogin.getPassword());
		}
		return res;
	}

	@Override
	public Customer saveCustomer(Customer cust) {
		Customer c1 = cr.save(cust);
		return c1;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public Customer getCustomerByEmailid(String strEmailId) {
//		log.info("finding customer for email id "+ strEmailId);
//		Customer optCust=cr.findByEmailid(strEmailId);
//		if(optCust!=null)
//		{
//			//email id is already present
//			log.info("customer is not present for email id "+ strEmailId);
//			throw new  ResourceNotFoundException("customer do not exist for email id "+ strEmailId);
//		}
////		Customer cust=optCust.get();
//		log.info("custome is present for eid "+ strEmailId);
//		return optCust;
//	}
	
	public Customer getCustomerByEmailid(String strEmailId) {
		log.info("finding customer for email id "+ strEmailId);
		Optional<Customer> optCust=cr.findByEmailid(strEmailId);
		if(optCust.isEmpty())
		{
			//email id is already present
			log.info("customer is not present for email id "+ strEmailId);
			throw new  ResourceNotFoundException("customer do not exist for email id "+ strEmailId);
		}
		Customer cust=optCust.get();
		log.info("custome is present for eid "+ strEmailId);
		return cust;
	}
	
//	@Override
//	public Customer getCustomerByEmailid(String strEmailId) {
//		Customer c1 = cr.findByEmailid(strEmailId);
//		return c1;
//	}

	@Override
	public Customer getCustomerByCustId(String strCustId) {
		Customer c1 = cr.findByCustomerid(Integer.parseInt(strCustId));
		if(c1==null)
		{
			log.info("customer is not present  "+ strCustId);
			throw new  ResourceNotFoundException("customer do not exist  "+ strCustId);
		}
//		Customer cust=c1.get();
		return c1;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Integer createCustomer(@Valid CustomerRequestDto customerRequest) {
		log.info("Inside CustomerService Implementation with request id "+customerRequest);
		
		//validate
		// Change these two checks:
		if (cr.countByEmailidNative(customerRequest.getEmailid()) > 0) {
		    throw new EntityAlreadyExistException("email id is already exists");
		}

		if (cr.countByContactNative(customerRequest.getContact()) > 0) {
		    throw new EntityAlreadyExistException("Contact already exists");
		}
		
		
//		if(cr.existsByEmailid(customerRequest.getEmailid())) {
//			throw new EntityAlreadyExistException("email id is already exists");
//		}
//		
//		if(cr.existsByContact(customerRequest.getContact())) {
//			throw new EntityAlreadyExistException("Conatct already exists");
//		}
		
//		
//		Optional<Customer> oc = cr.findByEmailid(customerRequest.getEmailid());
//		if(oc.isPresent()) {
//			throw new EntityAlreadyExistException("Customer email id is already existing ");
//		}
		
		//convert dto to entity
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
		
//		emailService.sendWelcomeEmail(custCreated.getEmailid(),
//				custCreated.getFirstname(),
//				PlanType.BASIC, startDate, expiryDate);
		
		
		welcomeEmailScheduler.scheduledWelcomeEmail(
				custCreated.getEmailid(),
				custCreated.getFirstname(),
				custCreated.getPlanType(), 
				custCreated.getPlanStartDate(), 
				custCreated.getPlanExpiryDate());
		
		return c1.getCustomerid();
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Customer> findByFirstNameLike(String fn) {
		log.info("inside List<Customer> findByFirstNameLike(String fn)");
		List<Customer> list = cr.findByFirstnameLike("%"+fn+"%");
		return list;
	}

	@Override
	public List<Customer> findByFirstNameContaining(String fnm) {
		List<Customer> list = cr.findByFirstnameContaining(fnm);
		return list;
	}

	@Override
	public List<Customer> findByfirstNameContains(String fnm) {
		List <Customer> lst=cr.findByFirstnameContains(fnm);
		return lst;
	}

	@Override
	public List<Customer> findByfirstNameIsContaining(String fnm) {
		List <Customer> lst=cr.findByFirstnameIsContaining(fnm);
		return lst;
	}

	@Override
	public List<Customer> findByFirstNameIgnoreCase(String fn) {
		String lower = fn.toLowerCase();
		List<Customer> list = cr.findByFirstnameIgnorecase(lower);
		return list;
	}
////////////////////////////////////////////////////////
	@Override
	public List<CustomerFnmLnmGenderDTO> findByLastname(String lnm)
	{
		
		List <CustomerFnmLnmGenderDTO> lst=cr.findByLastname(lnm);
		return lst;
	}

	//////////////////////////////ASKKKKKKKKKKKKKKKKKKKKKK QUESSSSSSSSSSSSSSSSSSSSSSSSSSSSS/////////////////////////
	@Override
	public Boolean existsByEmailIdAndPassword(String email, String password) {
		Optional<Customer> c1 = cr.findByEmailidAndPassword(email,password);
		if(c1.isEmpty()) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public Customer registerCustomer(Customer customer) {
		Customer c1 = cr.save(customer);
		return c1;
	}

	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		Optional<Customer> c1 = cr.findByEmailidAndPassword(email,password);
		Customer c = c1.get();
		return c;
	}

	@Override
	public Boolean isCustomerExistsByID(int customerID) {
		Customer c1 = cr.findByCustomerid(customerID);
		if(c1!=null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Customer getCustomerByEmailId(String strEmailId) {
		Optional<Customer> c1 = cr.findByEmailid(strEmailId);
		Customer c = c1.get();
		return c;
	}
	@TraceAndLogs
	@Override
	public Customer getCustomerByCustomerId(int customerID) {
		Customer c1 = cr.findByCustomerid(customerID);
		return c1;
	}
///////////////////////////CHECKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
	@Override
	public List<Customer> getCustomerExpiringTodayOrTomorrow() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getCellValue(Row row, int colIndex) {
		return Optional.ofNullable(row.getCell(colIndex,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL))
				.map(cell-> switch(cell.getCellType()) {
				 case STRING ->cell.getStringCellValue().trim();
				 case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
	             case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
	                default      -> "";
				}).orElse("");
	}
	
//@Override
//public BulkUploadResultDTO bulkCreateCustomers(MultipartFile file) {
//	List<BulkUploadResultDTO.RowErrorDTO> errorList = new ArrayList<>();
//	int successCount =0;
//	
//	try(Workbook workbook= new XSSFWorkbook(file.getInputStream())){
//		Sheet sheet = workbook.getSheetAt(0);
//		int totalRows = sheet.getLastRowNum();
//		
//		for(int i =1; i<=totalRows ; i++) {
//			Row row = sheet.getRow(i);
//			if(row== null) continue;
//			
//			int rowNum =i+1;
//			List<String> rowErrors = new ArrayList<>();
//			
//			String genderRaw = getCellValue(row,5).toUpperCase().trim();
//			Gender gender = null;
//			if(genderRaw.isBlank()) {
//				rowErrors.add("Genders cannot be blank");
//			}else if(!genderRaw.equals("MALE") && !genderRaw.equals("FEMALE")) {
//				rowErrors.add("Gender must be MALE or FEMALE, found: " + genderRaw);
//			}else {
//				gender = Gender.valueOf(genderRaw);
//			}
//			
//			CustomerRequestDto dto = CustomerRequestDto.builder()
//					.firstname(getCellValue(row,0))
//					.lastname(getCellValue(row,1))
//					.emailid(getCellValue(row, 2))
//	                .contact(getCellValue(row, 3))
//	                .password(getCellValue(row, 4))
//	                .gendervalue(gender)
//	                .addressline1(getCellValue(row, 6))
//	                .addressline2(getCellValue(row, 7))
//	                .city(getCellValue(row, 8))
//	                .state(getCellValue(row, 9))
//	                .pincode(getCellValue(row, 10))
//	                .build();
//			
//			Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);
//			violations.forEach(v->rowErrors.add(v.getMessage()));
//			
//			if(!rowErrors.isEmpty()) {
//				
//				errorList.add(BulkUploadResultDTO.RowErrorDTO.builder()
//						.rowNumber(rowNum)
//						.emailid(dto.getEmailid())
//						.errors(rowErrors)
//						.build()
//						
//						);
//				continue;
//			}
//			
//			try {
//				createCustomer(dto);
//				successCount++;
//			}catch(EntityAlreadyExistException e) {
//				errorList.add(BulkUploadResultDTO.RowErrorDTO.builder()
//						.rowNumber(rowNum)
//						.emailid(dto.getEmailid())
//						.errors(List.of("Email already exists :"+dto.getEmailid()))
//						.build()
//						);
//			}
//			
//		}
//		 return BulkUploadResultDTO.builder()
//		            .totalRows(totalRows)
//		            .successCount(successCount)
//		            .failureCount(errorList.size())
//		            .errors(errorList)
//		            .build();
//
//		    } catch (Exception e) {
//		        throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
//		    }
//		
//		
//
//}
	
	////////////////////////////////////////all about bulk upload
	//1
	
	// including duplicate validation 
	
	@Override
	public BulkUploadResultDTO bulkCreateCustomers(MultipartFile file) {
		List<BulkUploadResultDTO.RowErrorDTO> errorList = new ArrayList<>();
		int successCount =0;
		
		try(Workbook workbook = new XSSFWorkbook(file.getInputStream())){
			Sheet sheet = workbook.getSheetAt(0);
			int totalRows = sheet.getLastRowNum();
			
			//basic validation
			List<ValidatedRow> validRows = parseAndValidateRows(sheet,totalRows,errorList);
			
			Set<String> existingEmails = fetchExistingEmailsFromDB(validRows);
			Set<String> existingContacts = fetchExistingContactsFromDB(validRows);
			
			for(ValidatedRow entry : validRows) {
				List<String> conflicts = findDBConflicts(entry.dto(),existingEmails,existingContacts);
				
				if(!conflicts.isEmpty()) {
					errorList.add(buildError(entry.rowNum(),
							entry.dto().getEmailid() , conflicts ));
					continue;
				}
				
				try {
					createCustomer(entry.dto());
					successCount++;
				}catch(Exception e) {
					errorList.add(buildError(entry.rowNum(), entry.dto().getEmailid(),List.of("Unexpected error :"+ e.getMessage()) ));
					
				}
				
			}
			
			return BulkUploadResultDTO.builder()
					.totalRows(totalRows)
					.successCount(successCount)
					.failureCount(errorList.size())
					.errors(errorList)
					.build();			
		}catch(Exception e) {
			throw new RuntimeException("Fail to parse Excel file : " +e.getMessage());
		}
	}
	
	
	private List<ValidatedRow> parseAndValidateRows(Sheet sheet, int totalRows,List<BulkUploadResultDTO.RowErrorDTO> errorList){
		List<ValidatedRow> validRows = new ArrayList<>();
		
		for(int i =1 ; i<=totalRows; i++) {
			Row row = sheet.getRow(i);
			if(row==null) continue;
			
			int rowNum =i+1;
			List<String> rowErrors = new ArrayList<>();
			
			Gender gender = parseGender(getCellValue(row,5),rowErrors);
			
			CustomerRequestDto dto = CustomerRequestDto.builder()
					.firstname(getCellValue(row,0))
					.lastname(getCellValue(row,1))
					.emailid(getCellValue(row,2))
					.contact(getCellValue(row,3))
					.password(getCellValue(row,4))
					.gendervalue(gender)
					.addressline1(getCellValue(row,6))
					.addressline2(getCellValue(row,7))
					.city(getCellValue(row,8))
					.state(getCellValue(row,9))
					.pincode(getCellValue(row,10))
					.build();
			
			validator.validate(dto).forEach(v->rowErrors.add(v.getMessage()));
			
			if(!rowErrors.isEmpty()) {
				errorList.add(buildError(rowNum,dto.getEmailid(),rowErrors));
			}else {
				validRows.add(new ValidatedRow(rowNum ,dto));
			}
			
		} return validRows;
	}
	
	//checking existing email
	private Set<String> fetchExistingEmailsFromDB(List<ValidatedRow> rows){
		Set<String> emails= rows.stream().map(r->r.dto().getEmailid().toLowerCase())
				.collect(Collectors.toSet());
		return cr.findEmailsByEmailidIn(emails);
	}
	
	//existing contact
	private Set<String> fetchExistingContactsFromDB(List<ValidatedRow> rows){
		Set<String> contacts= rows.stream()
				.map(r->r.dto().getContact())
				.collect(Collectors.toSet());
		return cr.findContactsByContactIn(contacts);
	}
	
	private List<String> findDBConflicts(CustomerRequestDto dto, Set<String> existingEmails,Set<String> existingContacts){
		List<String> conflicts = new ArrayList<>();
		if(existingEmails.contains(dto.getEmailid().toLowerCase()))
			conflicts.add("Email already registered :"+dto.getEmailid());
		if (existingContacts.contains(dto.getContact()))
	        conflicts.add("Contact already registered : " + dto.getContact());
	    return conflicts;
	}
	
	private Gender parseGender(String rawValue, List<String> rowErrors) {
		String value = rawValue.toUpperCase().trim();
		if(value.isBlank()) {
			rowErrors.add("gender cannot be blank");
			return null;
		}
		
		if(!value.equals("MALE") && !value.equals("FEMALE")) {
			 rowErrors.add("Gender must be MALE or FEMALE, found: " + value);
		        return null;
		}
		return Gender.valueOf(value);
	}
	
	//record is a special short way to create a simple data-holding class introduced in Java 16.
	private record ValidatedRow(int rowNum, CustomerRequestDto dto) {
		
	}
	
	private BulkUploadResultDTO.RowErrorDTO buildError(int rowNum,String email,List<String> errors){
		return BulkUploadResultDTO.RowErrorDTO.builder()
				.rowNumber(rowNum)
				.emailid(email)
				.errors(errors)
				.build();
	}

	@Override
	public Page<Customer> getAllCustomers(String search, Pageable pageable) {
		// TODO Auto-generated method stub
		return (search != null && !search.isBlank())
				? cr.findBySearchKeyword(search.trim(),pageable)
				: cr.findAll(pageable);
	}
	

	

}
