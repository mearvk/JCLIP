package org.jclip.utils;

import org.jclip.args.Arguments;

public class OptionProcessingUtils 
{
	public static String getOptionValueFromOptionKey(String s, Arguments args) throws Exception
	{
		for(String key : args.keyList)
		{
			if(key.equalsIgnoreCase(s))
			{
				return args.valueList.get(args.keyList.indexOf(key));
			}
		}
		
		throw new Exception("No key-value pair found for key '"+s+"'");
	}
}
