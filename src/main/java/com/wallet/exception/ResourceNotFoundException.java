package com.wallet.exception;

public class ResourceNotFoundException  extends RuntimeException{
	
	public ResourceNotFoundException()
	{
		
	}
	public ResourceNotFoundException(String msg)
	{
		super(msg + "  resource not found exception fired ");
	}
	

}
