package org.jclip.options;

import org.jclip.interfaces.OptionValidator;

public abstract class Option
{
	public OptionValidator validator = null;
	public String key = null;
	public Boolean isPresent = false;
	public Boolean isValid = false;

	public Option(String key)
	{
		this.key = key;
	}

	public Option(String key, OptionValidator validator)
	{
		this.key = key;
		this.validator = validator;
	}
}
