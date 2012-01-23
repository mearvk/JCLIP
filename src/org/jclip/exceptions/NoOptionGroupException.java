package org.jclip.exceptions;

public class NoOptionGroupException extends Exception 
{
	public NoOptionGroupException()
	{
		super("Validation requires at least one OptionGroup to validate.");
	}
}
