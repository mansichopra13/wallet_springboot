package com.emp.exception;

public class ResourceNotFoundException  extends RuntimeException{
	
	public ResourceNotFoundException()
	{
		
	}
	public ResourceNotFoundException(String msg)
	{
		super(msg + "  resource not found exception fired ");
	}
	

}
