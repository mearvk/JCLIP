package org.jclip.options;

import org.jclip.interfaces.OptionValidator;

public abstract class Option
{
	public OptionValidator validator;
	public String key = null;
	public String val = null;
	public Boolean isPresent = false;
	
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
