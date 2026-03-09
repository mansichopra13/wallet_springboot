package com.emp.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.emp.dto.request.AccountRequestDTO;
import com.emp.enums.AccountType;
import com.emp.enums.TransactionType;
import com.emp.exception.ResourceNotFoundException;
import com.emp.model.Account;
import com.emp.model.Customer;
import com.emp.model.Transaction;
import com.emp.repo.AccountRepo;
import com.emp.repo.CustomerRepo;
import com.emp.services.AccountService;
import com.emp.services.CustomerService;
import com.emp.services.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountImplements implements AccountService {
	
	@Autowired
	AccountRepo accr;
	
	@Autowired
	CustomerRepo cr;
	
	@Autowired
	CustomerService cs;
	@Lazy
	@Autowired
	TransactionService ts;
	
	@Override
	public int saveAccount(AccountRequestDTO accReq) {
		log.info("Create a New Account");
		Optional<Customer> optCust = cr.findById(accReq.getCustomer().getCustomerid());
		if(optCust.isEmpty()) {
			throw new ResourceNotFoundException("Customer is not present in database");
		}
		Customer cust = optCust.get();
		cust.setCustomerid(accReq.getCustomer().getCustomerid());
		
		Account account = Account.builder()
						  .typeofaccount(accReq.getTypeofaccount())
						  .customer(cust)
						  .openingbalance(accReq.getOpeningbalance())
						  .description(accReq.getDescription())
						  .openingdate(LocalDate.now())
						  .build();
		
		
		
		int accNumberCreated=accr.save(account).getAccountnumber();
		
		Transaction tra = Transaction.builder()
	            .transactionType(TransactionType.CREDIT)
	            .transactiondate(LocalDate.now())
	            .amount(accReq.getOpeningbalance())
	            .description(TransactionType.CREDIT +" from " +accNumberCreated )
	            .build();
		
		ts.saveTransaction(tra);

		return accNumberCreated;
	}

	@Override
	public List<Account> getAccountsByCustId(int intCustId) {
		
		List<Account> ac= accr.findByCustomerCustomerid(intCustId);
		if(ac.isEmpty()) {
			throw new ResourceNotFoundException("No Account found for customer id "+ intCustId);
		}else {
			return ac;
		}
	}

	@Override
	public List<Account> getAccountsByCustEmailId(String emid) {
		// TODO Auto-generated method stub
		List<Account> a1 = accr.findByCustomerEmailid(emid);
		if(a1.isEmpty()
				) {
				throw new ResourceNotFoundException("Account is not existing for custEmailId "+ emid);
			}else {
				return a1;
			}
//		OR
//		Customer cust= customerService.getCustomerByEmailid(custEmailId);
//		List<Account> lst=getAccountsByCustId(cust.getCustomerId());
//		return lst;
	}

	@Override
	public List<Account> getAccountsLessThanOpBal(double openingBalance) {
		List<Account> a1= accr.findByOpeningbalanceLessThan(openingBalance);
		if(a1.isEmpty())
		{
			throw new ResourceNotFoundException("Account is not existing "+ openingBalance);
		}else {
			return a1;
		}
	}

	@Override
	public List<Account> getAccountsLessThanEqualOpBal(double openingBalance) {
		log.info("inside  getAccountsLessThanOpBalEqual");
		List<Account> lst=accr.findByOpeningbalanceLessThanEqual(openingBalance);
		return lst;
	}

	@Override
	public Account getAccountByAccNumber(int accountNumber) {
//		Account a1 = accr.findByAccountnumber(accountNumber);
//		if(a1==null)
//		{
//			throw new ResourceNotFoundException("Account is not existing for account number "+ accountNumber);
//		}else {
//			return a1;
//		}
// orrrrrrrrr - return accr.findById(accountNumber).orElseThrow(()-> new ResourceNotFoundException("Account is not existing for account number "+ accountNumber));		
		
		Optional<Account> optAcc=accr.findById(accountNumber);
		if(optAcc.isEmpty())
		{
			throw new ResourceNotFoundException("Account is not existing for account number "+ accountNumber);
		}
		return optAcc.get();
	}

	@Override
	public List<Account> findDistinctByAccountTypeAndOpeningBalance(AccountType accType, double openingBalance) {
		log.info("inside find distinct By accctype and op balance - accType "+ accType+" op bal= "+ openingBalance);		
		List<Account> lst=	accr.findDistinctByTypeofaccountAndOpeningbalance(accType, openingBalance);
		return lst;
	}

	@Override
	public List<Account> findDistinctByOpeningBalance(double openingBalance) {
		return accr.findDistinctByOpeningbalance(openingBalance);
	}
////////////check if works or not
	@Override
	public List<String> getDistinctAccType() {
		return accr.findDistinctByTypeofaccount();
	}

	@Override
	public List<Account> findByOpeningBalanceGreaterThan(double openingBalance) {
		log.info("inside findByOpeningBalanceGreaterThan - "+ openingBalance);		
		List<Account> lst=	accr.findByOpeningbalanceGreaterThan(openingBalance);
		return lst;
	}

	@Override
	public List<Account> findByOpeningDateBetween(LocalDate startDate, LocalDate endDate) {
		log.info(startDate.toString());
		log.info(endDate.toString());
		return accr.findByOpeningdateBetween(startDate, endDate);
	}

	@Override
	public List<Account> findByOpeningDateAfter(LocalDate dt) {
		return accr.findByOpeningdateAfter(dt);
	}

	@Override
	public List<Account> findByOrderByOpeningBalanceAsc() {
		return accr.findByOrderByOpeningbalanceAsc();
	}

	@Override
	public List<Account> findByOpeningBalanceNot(double ob) {
		return accr.findByOpeningbalanceNot(ob);
	}

	@Override
	public List<Account> findByAccountTypeIn(List<AccountType> accTypes) {
		return accr.findByTypeofaccountIn(accTypes);
	}

}
