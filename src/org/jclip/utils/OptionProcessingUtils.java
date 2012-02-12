package org.jclip.utils;

import org.jclip.args.CommandLineArguments;

public class OptionProcessingUtils 
{
	public static String getOptionValueFromOptionKey(String s) throws Exception
	{
		for(String key : CommandLineArguments.keyList)
		{
			if(key.equalsIgnoreCase(s))
			{
				return CommandLineArguments.valueList.get(CommandLineArguments.keyList.indexOf(key));
			}
		}
		
		throw new Exception("No key-value pair found for key '"+s+"'");
	}
}
