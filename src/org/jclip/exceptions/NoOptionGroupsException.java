package org.jclip.exceptions;

public class NoOptionGroupsException extends Exception 
{
	public NoOptionGroupsException()
	{
		super("Matcher requires at least one OptionGroup in its OptionGroups structure.");
	}
}
