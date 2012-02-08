package org.jclip.exceptions;

public class DuplicateOptionException extends Exception
{
	private static final long serialVersionUID = -7002761363611566571L;

	public DuplicateOptionException(String message)
	{
		super("One or more of your Options is defined multiple times");
	}
}
