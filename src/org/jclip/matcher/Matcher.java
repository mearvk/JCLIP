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
	
	public void setArgs(String...args)
	{
		this.arguments = new ArgParser().parseArgs(args);		
	}
	
	public void setOptionGroups(OptionGroups groups)
	{
		this.groups = groups;
	}
	
	public void findMatchingOptionGroup() throws Exception
	{
		//for each option group
		for(OptionGroup group : groups.groups)
		{
			//for each option
			for(Option option : group.options)
			{
				//check to see if option is present in args
				for(Argument argument : arguments)
				{
					String optionKey = option.key;
					String argumentKey = argument.key;
					
					if(optionKey.equalsIgnoreCase(argumentKey))
					{
						option.isPresent = true; 
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
