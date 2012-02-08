package org.jclip.exceptions;

public class NoMatchesToValidateException extends Exception 
{
	private static final long serialVersionUID = -2122507579948541670L;

	public NoMatchesToValidateException() 
	{
		super("Validator cannot validate because Matcher found no matches.");
	}
}
