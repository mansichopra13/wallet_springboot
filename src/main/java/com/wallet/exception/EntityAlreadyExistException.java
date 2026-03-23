package com.wallet.exception;

public class EntityAlreadyExistException extends RuntimeException{
	public EntityAlreadyExistException()
	{
		
	}
	public EntityAlreadyExistException(String msg)
	{
		super(msg+ " "+ "Entity Already Exists exception fired ");
	}
}
