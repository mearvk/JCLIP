package org.jclip.options;

import java.util.ArrayList;

import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;

public class OptionGroup
{
	public OptionGroupValidator validator;
	public Callback callback;
	public ArrayList<Option> options = new ArrayList<Option>();
	
	public OptionGroup()
	{
		
	}
	
	public void addOption(Option option)
	{
		this.options.add(option);
	}

	public void addCallback(Callback callback)
	{
		this.callback = callback;		
	}
	
	public Boolean allOptionsPresent()
	{			
		for(Option option : options)
		{
			if(!option.isPresent) return false;
		}
		
		return true;
	}
}
