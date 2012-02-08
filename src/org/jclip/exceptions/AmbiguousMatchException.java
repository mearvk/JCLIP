package org.jclip.exceptions;

public class AmbiguousMatchException extends Exception
{
	private static final long serialVersionUID = 130697394617285572L;

	public AmbiguousMatchException(String arg0)
	{
		super("One or more OptionGroups have same quality of match.");
	}
}
