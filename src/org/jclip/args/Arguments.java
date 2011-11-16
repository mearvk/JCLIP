package org.jclip.args;

import java.util.ArrayList;

public class Arguments
{
	public ArrayList<String> prefixList = new ArrayList<String>();
	public ArrayList<String> bridgeList = new ArrayList<String>();
	public ArrayList<String> keyList = new ArrayList<String>();
	public ArrayList<String> valueList = new ArrayList<String>();
	
	public Arguments(String...args)
	{
		new ArgParser(this,args).processArgs();
	}
	
	public String getOptionValueFromOptionKey(String s) throws Exception
	{
		for(String key : keyList)
		{
			if(key.equalsIgnoreCase(s))
			{
				return valueList.get(keyList.indexOf(key));
			}
		}
		
		throw new Exception("No key-value pair found for key '"+s+"'");
	}
}
