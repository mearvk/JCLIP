package org.jclip.exceptions;

@SuppressWarnings("serial")
public class NoOptionGroupValidatorException extends Exception {
	public NoOptionGroupValidatorException() {
		super("The OptionGroup validator is null; validation cannot proceeed.");
	}
}
