package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.ArgParser;
import org.jclip.args.Argument;
import org.jclip.options.Option;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups groups;
	public OptionGroup matchingGroup;
	public ArrayList<Argument> arguments;
	
	public Matcher()
	{
		
	}
	
	public Matcher(OptionGroups groups, String...args)
	{
		this.groups = groups;
		this.arguments = new ArgParser().parseArgs(args);
	}
	
	public void setArgs(String...args)
	{
		this.arguments = new ArgParser().parseArgs(args);
	}
	
	public void setOptionGroups(OptionGroups groups)
	{
		this.groups = groups;
	}
	
	public void matchArgsToOptionGroup() throws Exception
	{		
		for(OptionGroup group : groups.groups)
		{			
			for(Option option : group.options)
			{				
				for(Argument argument : arguments)
				{
					String optionKey = option.key;
					String argumentKey = argument.key;
					
					//
					if(optionKey.equalsIgnoreCase(argumentKey))
					{
						option.isPresent = true;
						
						if(option.validator!=null && !option.validator.validate(argument.value))
						{
							throw new Exception(argument.value+" didn't pass its validation test");
						}
						else option.isValid = true;
						 
						break;
					}					
				}
			}
						
			if(group.allOptionsPresent())
			{
				this.matchingGroup = group;
				return;
			}				
		}
		
		throw new Exception("No match found");
	}

	public void passControlToCallback()
	{		
		matchingGroup.callback.execute();
	}
}
