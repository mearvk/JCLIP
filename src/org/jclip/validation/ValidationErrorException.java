package org.jclip.validation;

@SuppressWarnings("serial")
public class ValidationErrorException extends Exception 
{
	public ValidationErrorException() 
	{
		super("Validator unable to validate all arguments and/or values.");
		
		ValidationData.printErrors();
	}
}
