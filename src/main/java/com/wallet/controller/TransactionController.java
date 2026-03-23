package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.common.ApiResponse;
import com.wallet.dto.request.TransactionDepositRequestDTO;
import com.wallet.exception.IcsdException;
import com.wallet.model.Transaction;
import com.wallet.services.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/transaction")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	@PostMapping(value="/depositAmountInAccount")
	public ResponseEntity<ApiResponse> depoistAmountInAccount(@RequestBody @Valid  TransactionDepositRequestDTO tdReq) throws IcsdException
	{
		Transaction trans=transactionService.depositAmountInAccount(tdReq);
		ApiResponse apiresponse = ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("transaction  completed successfully")
				.data(trans)
				.build();
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);	
	}
	
	@PostMapping(value="/withDrawAmountInAccount")
	public ResponseEntity<ApiResponse> withDrawAmountInAccount(@RequestBody @Valid  TransactionDepositRequestDTO tdReq)
	{
		Transaction trans=transactionService.WithdrawAmountInAccount(tdReq);
		ApiResponse apiresponse = ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("transaction  completed successfully")
				.data(trans.getTransactionid())
				.build();
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
	}
	
	@PostMapping(value="/fundTransfer")
	public ResponseEntity<ApiResponse> fundTransfer(@RequestBody @Valid  TransactionDepositRequestDTO tdReq)
	{
		Transaction trans=transactionService.fundTransferFunction(tdReq);
		ApiResponse apiresponse = ApiResponse.builder()
				.code(HttpStatus.OK.value())
				.message("transaction  completed successfully")
				.data(trans.getTransactionid())
				.build();
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);	
	}
}
