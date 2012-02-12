package org.jclip.exceptions;

public class AmbiguousMatchException extends Exception
{
	private static final long serialVersionUID = 130697394617285572L;

	public AmbiguousMatchException()
	{
		super("One or more OptionGroups have same quality of match; unable to proceed.");
	}
}
