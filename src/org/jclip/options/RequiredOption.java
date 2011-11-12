package org.jclip.options;

import org.jclip.interfaces.OptionValidator;

public class RequiredOption extends Option
{
	public RequiredOption(String key)
	{
		super(key);
	}

	public RequiredOption(String key, OptionValidator validator)
	{
		super(key, validator);
	}
}
