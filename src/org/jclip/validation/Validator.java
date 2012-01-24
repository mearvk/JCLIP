package org.jclip.validation;

import org.jclip.args.Arguments;
import org.jclip.exceptions.NoOptionGroupException;
import org.jclip.exceptions.NoOptionGroupValidatorException;
import org.jclip.options.Option;
import org.jclip.options.OptionGroup;
import org.jclip.utils.OptionProcessingUtils;

public class Validator 
{	
	private Arguments args=null;
	
	public Validator(String...args)
	{	
		this.args=new Arguments(args);
	}
	
	public void validate(OptionGroup group) throws Exception
	{
		validateOptionGroup(group);
		validateIndividualOptions(group);
	}
	
	public void validateOptionGroup(OptionGroup group) throws Exception
	{
		if(group==null) throw new NoOptionGroupException();
		
		if(group.validator==null) return;

		
		try 
		{
			group.validator.validateOptionGroup(group);
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
	}
	
	public void validateIndividualOptions(OptionGroup group)
	{
		if(group==null) throw new NullPointerException();
		
		for(Option option : group.allOptions)
		{
			if(option.validator==null) continue;
			
			try 
			{
				option.validator.validateOption(OptionProcessingUtils.getOptionValueFromOptionKey(option.key,args));
			} 
			catch (Exception e) 
			{			
				e.printStackTrace();
			}
		}
	}
}
