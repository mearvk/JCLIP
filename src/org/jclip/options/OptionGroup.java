package org.jclip.options;

import java.util.ArrayList;

import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;
import org.jclip.interfaces.OptionValidator;

public class OptionGroup
{	
	public ArrayList<RequiredOption> requiredOptions = new ArrayList<RequiredOption>();
	public ArrayList<OptionalOption> optionalOptions = new ArrayList<OptionalOption>();
	public ArrayList<Option> allOptions = new ArrayList<Option>();
	public ArrayList<Callback> callbacks = new ArrayList<Callback>();	
	public ArrayList<String> requiredKeys = new ArrayList<String>();
	public ArrayList<String> optionalKeys = new ArrayList<String>();
	public OptionGroupValidator validator = null;

	public OptionGroup()
	{

	}

	public void addRequiredOption(RequiredOption option)
	{
		//add to required options list
		requiredOptions.add(option);
		
		//add to all options list
		allOptions.add(option);
		
		//add the key (i.e. cipher=rsa) to the key list
		requiredKeys.add(option.key);
	}

	public void addOptionalOption(OptionalOption option)
	{
		//add option to optional list
		optionalOptions.add(option);
		
		//add option to all options list
		allOptions.add(option);
		
		//add the key value to the list of optional keys
		optionalKeys.add(option.key);
	}
	
	public void addValidator(OptionGroupValidator validator)
	{
		this.validator = validator;
	}

	public void addCallback(Callback callback)
	{
		this.callbacks.add(callback);
	}
}
