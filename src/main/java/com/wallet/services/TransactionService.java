package com.wallet.services;

import java.util.List;

import com.wallet.dto.request.TransactionDepositRequestDTO;
import com.wallet.exception.IcsdException;
import com.wallet.model.Account;
import com.wallet.model.Transaction;

import javax.validation.Valid;

public interface TransactionService {
	public Transaction saveTransaction( Transaction tra);
	public Transaction WithdrawAmountInAccount(TransactionDepositRequestDTO tdd);
	public Transaction fundTransferFunction(TransactionDepositRequestDTO tdd);
	public Transaction depositAmountInAccount(TransactionDepositRequestDTO tdd);
//	public Transaction depositAmountInAccount(TransactionDepositDTO tdd)
//	public int updateOpeningBalanceByAccountNumber(int accountNumber,double newOpeningBalance);
	public void updateOpeningBalanceByAccountNumber(Account acc,double newOpeningBalance);
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber);
	int WithdrawAndDeposit(TransactionDepositRequestDTO trans) throws IcsdException;
}
