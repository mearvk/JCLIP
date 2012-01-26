package org.jclip.exceptions;

@SuppressWarnings("serial")
public class NoMatchFoundException extends Exception {
	public NoMatchFoundException() {
		super("No match found!");
	}
}
