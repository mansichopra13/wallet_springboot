package com.emp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.dto.common.ApiResponse;
import com.emp.dto.request.AccountRequestDTO;
import com.emp.enums.AccountType;
import com.emp.model.Account;
import com.emp.services.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/acc")
public class AccountController {
	@Autowired
	private AccountService as;
	
	@PostMapping(value="/create")
	public ResponseEntity<ApiResponse> saveAccount(@RequestBody @Valid AccountRequestDTO accRequest){
		Integer generatedAccNum = as.saveAccount(accRequest);
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account created successfully").data(generatedAccNum).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);		
	}
	
	@GetMapping(value="/get/accBycust/{cid}")
	public ResponseEntity<ApiResponse> getAccByCustid(@PathVariable String cid){
		List<Account> ac = as.getAccountsByCustId(Integer.parseInt(cid)); 
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account created successfully").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
////////////	
	@GetMapping(value="/get/accByemid/{emid}")
	public ResponseEntity<ApiResponse> getAccByEmailid(@PathVariable String emid){
		List<Account> ac = as.getAccountsByCustEmailId(emid); 
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account created successfully").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	///////////repeat json
	@GetMapping(value="/get/accLessThanOB/{ob}")
	public ResponseEntity<ApiResponse> getAccountsLessThanOpBal(@PathVariable double ob){
		List<Account> ac = as.getAccountsLessThanOpBal(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/accLessThanEqualOB/{ob}")
	public ResponseEntity<ApiResponse> getAccountsLessThanEqualOpBal(@PathVariable double ob){
		List<Account> ac = as.getAccountsLessThanEqualOpBal(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/accnum/{ob}")
	public ResponseEntity<ApiResponse> getAccountByAccNumber(@PathVariable int ob){
		Account ac = as.getAccountByAccNumber(ob);
		if(ac==null)
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	///////////////////////////////////////////////////checkkkkkkkkkkkkkkkkkk
	
	@GetMapping(value="/get/distinctAccTypeOb/{accType}/{openingBalance}")
	public ResponseEntity<ApiResponse> findDistinctByAccountTypeAndOpeningBalance(@PathVariable AccountType accType,@PathVariable double openingBalance){
		List<Account> ac = as.findDistinctByAccountTypeAndOpeningBalance(accType, openingBalance);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	///////////////////////////////////////////////////checkkkkkkkkkkkkkkkkkk	
	@GetMapping(value="/get/distinctOb/{ob}")
	public ResponseEntity<ApiResponse> findDistinctByOpeningBalance(@PathVariable double ob){
		List<Account> ac = as.findDistinctByOpeningBalance(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	///////////////////////////////////////////////////checkkkkkkkkkkkkkkkkkk	
	@GetMapping(value="/get/distinctAccType/{ob}")
	public ResponseEntity<ApiResponse> getDistinctAccType(@PathVariable int ob){
		List<String> ac = as.getDistinctAccType();
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/ObGreaterThan/{ob}")
	public ResponseEntity<ApiResponse> findByOpeningBalanceGreaterThan(@PathVariable double ob){
		List<Account> ac = as.findByOpeningBalanceGreaterThan(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/OpenDateBtw/{startDate}/{endDate}")
	public ResponseEntity<ApiResponse> findByOpeningDateBetween(@PathVariable LocalDate startDate,@PathVariable LocalDate endDate){
		List<Account> ac = as.findByOpeningDateBetween(startDate, endDate);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/opendateAfter/{ob}")
	public ResponseEntity<ApiResponse> findByOpeningDateAfter(@PathVariable LocalDate ob){
		List<Account> ac = as.findByOpeningDateAfter(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
	@GetMapping(value="/get/orderbyOb")
	public ResponseEntity<ApiResponse> findByOrderByOpeningBalanceAsc(){
		List<Account> ac = as.findByOrderByOpeningBalanceAsc();
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	//////////////////checkkkkkkkkkkkkkkk
	@GetMapping(value="/get/ObNot/{ob}")
	public ResponseEntity<ApiResponse> findByOpeningBalanceNot(@PathVariable double ob){
		List<Account> ac = as.findByOpeningBalanceNot(ob);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	///////////////////checkkkkkkkkkkkkkkkkkkkkkkkk
	@GetMapping(value="/get/accIn/{accTypes}")
	public ResponseEntity<ApiResponse> findByAccountTypeIn(@PathVariable List<AccountType> accTypes){
		List<Account> ac = as.findByAccountTypeIn(accTypes);
		if(ac.isEmpty())
		{	
			log.info("no record found");
			ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("not found").build();
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse = ApiResponse.builder().code(HttpStatus.OK.value()).message("Account founded").data(ac).build();
		return new ResponseEntity<ApiResponse> (apiresponse,HttpStatus.OK);	
	}
	
}
