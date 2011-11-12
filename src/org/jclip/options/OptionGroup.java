package org.jclip.options;

import java.util.ArrayList;

import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;

public class OptionGroup
{
	public OptionGroupValidator validator;
	public Callback callback;
	public ArrayList<RequiredOption> requiredOptions = new ArrayList<RequiredOption>();
	public ArrayList<OptionalOption> optionalOptions = new ArrayList<OptionalOption>();
	public ArrayList<String> requiredKeys = new ArrayList<String>();
	public ArrayList<String> optionalKeys = new ArrayList<String>();

	public OptionGroup()
	{

	}

	public void addRequiredOption(RequiredOption option)
	{
		requiredOptions.add(option);
		requiredKeys.add(option.key);
	}

	public void addOptionalOption(OptionalOption option)
	{
		optionalOptions.add(option);
		optionalKeys.add(option.key);
	}

	public void addCallback(Callback callback)
	{
		this.callback = callback;
	}

	/*
	 * public Boolean allOptionsPresent() { for(Option option : options) {
	 * if(!option.isPresent) return false; }
	 * 
	 * return true; }
	 */
}
