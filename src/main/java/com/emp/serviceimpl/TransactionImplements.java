package com.emp.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.dto.request.TransactionDepositRequestDTO;
import com.emp.enums.TransactionType;
import com.emp.exception.IcsdException;
import com.emp.exception.ResourceNotFoundException;
import com.emp.model.Account;
import com.emp.model.Customer;
import com.emp.model.Transaction;
import com.emp.repo.AccountRepo;
import com.emp.repo.TransactionRepo;
import com.emp.services.AccountService;
import com.emp.services.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionImplements implements TransactionService{
	
	@Autowired
	AccountRepo ar;
	
	@Autowired
	AccountService as;
	
	@Autowired
	TransactionRepo tr;
	
	@Override
	public Transaction saveTransaction(Transaction trans) {

		tr.save(trans);
		return trans;
	}

	@Override
	public int WithdrawAndDeposit(TransactionDepositRequestDTO trans) throws IcsdException {
		int accountNumber = trans.getAccountnumber();
		
		Account fromacc=ar.findById(trans.getFromAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("From Account not found"));
		Account toacc=ar.findById(trans.getToAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("To Account not found"));
		Account acc = as.getAccountByAccNumber(accountNumber);
		
		Transaction tra = Transaction.builder()
							.transactionType(trans.getTransactionType())
				            .transactiondate(LocalDate.now())
				            .amount(trans.getAmount())
				            .description(trans.getTransactionType() +"from" +trans.getFromAccount().getAccountnumber() + "to"+trans.getToAccount().getAccountnumber())
				            .fromAccount(fromacc)
				            .toAccount(toacc)
				            .build();
		
		double newOpeningBalance;
		if(trans.getTransactionType()==TransactionType.DEBIT) {
			
			if(acc.getOpeningbalance()<trans.getAmount()) {
				throw new IcsdException("Balance insufficient");
			}else {
				 newOpeningBalance = acc.getOpeningbalance() - trans.getAmount();
				 updateOpeningBalanceByAccountNumber(acc, newOpeningBalance);
			}
			
		}else {
			 newOpeningBalance = acc.getOpeningbalance() + trans.getAmount();
			 updateOpeningBalanceByAccountNumber(acc, newOpeningBalance);
		}
		
		Transaction t = tr.save(tra);
		log.info("transaction saved with details " + tra);

		return t.getTransactionid();
	}

	@Override
	public Transaction fundTransferFunction(TransactionDepositRequestDTO trans) {
	
		Account fromAcc = as.getAccountByAccNumber(trans.getFromAccount().getAccountnumber());
		
		Account toAcc = as.getAccountByAccNumber(trans.getToAccount().getAccountnumber());
		
		double oldOBFromAcc = fromAcc.getOpeningbalance();
		double newOBFromAcc = oldOBFromAcc - trans.getAmount();
		
		updateOpeningBalanceByAccountNumber(fromAcc, newOBFromAcc);
		
		Transaction tra = Transaction.builder()
				.transactionType(TransactionType.DEBIT)
	            .transactiondate(LocalDate.now())
	            .amount(trans.getAmount())
	            .description(trans.getTransactionType() +"from" +trans.getFromAccount() + "to"+trans.getToAccount())
	            .fromAccount(trans.getFromAccount())
	            .toAccount(trans.getToAccount())
	            .build();
		tr.save(tra);

		double oldOBToAcc = toAcc.getOpeningbalance();
		double newOBToAcc = oldOBToAcc + trans.getAmount();
		updateOpeningBalanceByAccountNumber(toAcc, newOBToAcc);

		Transaction trato = Transaction.builder()
				.transactionType(TransactionType.CREDIT)
	            .transactiondate(LocalDate.now())
	            .amount(trans.getAmount())
	            .description(trans.getTransactionType() +"from" +trans.getFromAccount() + "to"+trans.getToAccount())
	            .fromAccount(trans.getFromAccount())
	            .toAccount(trans.getToAccount())
	            .build();
		tr.save(trato);
		return tra;
		
	}
	
	public Transaction WithdrawAmountInAccount(TransactionDepositRequestDTO trans) {

		Account fromacc=ar.findById(trans.getFromAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("From Account not found"));
		Account toacc=ar.findById(trans.getToAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("To Account not found"));
	
		Transaction tra = Transaction.builder()
							.transactionType(TransactionType.DEBIT)
				            .transactiondate(LocalDate.now())
				            .amount(trans.getAmount())
				            .description(trans.getTransactionType() +"from" +trans.getFromAccount().getAccountnumber())
				            .fromAccount(fromacc)
//				            .toAccount(toacc)
				            .build();
		
		double newOpeningBalance;
	
				 newOpeningBalance = fromacc.getOpeningbalance() - trans.getAmount();
				 updateOpeningBalanceByAccountNumber(fromacc, newOpeningBalance);
		
		Transaction t = tr.save(tra);
		log.info("transaction saved with details " + tra);

		return t;
	}
	
	@Override
	public Transaction depositAmountInAccount(TransactionDepositRequestDTO trans) {
	
		Account fromacc=ar.findById(trans.getFromAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("From Account not found"));
		Account toacc=ar.findById(trans.getToAccount().getAccountnumber()).orElseThrow(()->new  RuntimeException("To Account not found"));
	
		Transaction tra = Transaction.builder()
							.transactionType(TransactionType.CREDIT)
				            .transactiondate(LocalDate.now())
				            .amount(trans.getAmount())
				            .description(trans.getTransactionType() + "to"+trans.getToAccount().getAccountnumber())
//				            .fromAccount(fromacc)
				            .toAccount(toacc)
				            .build();
		
				double newOpeningBalance = toacc.getOpeningbalance() + trans.getAmount();
				updateOpeningBalanceByAccountNumber(toacc, newOpeningBalance);
				
				
		
		Transaction t = tr.save(tra);
		log.info("transaction saved with details " + tra);

		return t;
	}

	public void updateOpeningBalanceByAccountNumber(Account acc, double newOpeningBalance) {		
		acc.setOpeningbalance(newOpeningBalance);
		ar.save(acc);
		log.info("account is updated with new updatedbalance " + newOpeningBalance + " accno" + acc.getAccountnumber());	
	}
	@Override
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber) {
		// TODO Auto-generated method stub
		return tr.findByAccountnumber(accountNumber);
	}



}
