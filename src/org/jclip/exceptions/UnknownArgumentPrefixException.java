package org.jclip.exceptions;

public class UnknownArgumentPrefixException extends Exception
{
	private static final long serialVersionUID = -6174782271215872148L;

	public UnknownArgumentPrefixException(String arg)
	{
		super("Unsupported prefix found for argument '"+arg+"', please check your syntax.");
	}
}
