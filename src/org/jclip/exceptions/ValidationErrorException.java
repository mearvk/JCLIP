package org.jclip.exceptions;

public class ValidationErrorException extends Exception
{
	private static final long serialVersionUID = 5754212960886716796L;

	public ValidationErrorException()
	{
		
	}
	
	public ValidationErrorException(String msg)
	{
		super(msg);
	}
}
