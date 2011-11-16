package org.jclip.exceptions;

public class SetEqualityException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage()
	{
		return "Cannot have set equality between two or more OptionGroup instances!";
	}
}
