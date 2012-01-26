package org.jclip.exceptions;

@SuppressWarnings("serial")
public class NoOptionGroupsException extends Exception {
	public NoOptionGroupsException() {
		super(
				"Matcher requires at least one OptionGroup in its OptionGroups structure.");
	}
}
