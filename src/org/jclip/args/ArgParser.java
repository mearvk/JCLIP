package org.jclip.args;

import org.apache.commons.lang3.StringUtils;
import org.jclip.exceptions.UnknownArgumentPrefixException;

public class ArgParser
{
	public static String[] rawArgs = null;
	public static String[] acceptedPrefixTokens = new String[]{ "--","-" };
	public static String[] acceptedBridgeTokens = new String[]{ "=" };	
	
	public static void processArgs(String...rawArgs) throws Exception
	{
		//for every CLarg
		for (String arg : rawArgs)
		{
			String result = null;
						
			//try
			//{
				//strip the prefix
				result = stripAndSavePrefix(arg);
				
				//strip the key
				result = stripAndSaveKey(result);
				
				//strip the bridge 
				result = stripAndSaveBridge(result);
				
				//strip the value
				result = stripAndSaveValue(result);
				
				//should have an empty string here, exception for debugging purposes
				if(result.length()>0) throw new Exception("Oops, something happened while trying to parse the CL args");
			//}
			//catch (Exception e)
			//{				
			//	System.err.println(e);
			//}
		}
	}
	
	private static String stripAndSavePrefix(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();		
		
		//check arg against all acceptable prefixes
		for(String prefix : acceptedPrefixTokens)
		{
			//if acceptable prefix located
			if(arg.startsWith(prefix))
			{
				//add prefix to prefix list
				CommandLineArguments.prefixList.add(prefix);
				
				//return the string minus the prefix
				return StringUtils.removeStart(arg, prefix);
			}
		}
		
		throw new UnknownArgumentPrefixException(arg);
	}
	
	private static String stripAndSaveKey(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();
		
		if(!hasBridgeToken(arg))
		{
			CommandLineArguments.keyList.add(arg);
			return arg;
		}
			
		//check the arg for an acceptable bridge
		for(String bridge : acceptedBridgeTokens)
		{			
			//try to locate a bridge
			int bridgeLocation = arg.indexOf(bridge);
			
			//found an acceptable bridge
			if(bridgeLocation != -1)
			{
				//key is everything up to the bridge
				String key = arg.substring(0,bridgeLocation);
				
				//add key to key list
				CommandLineArguments.keyList.add(key);
				
				//return the string minus the key
				return StringUtils.removeStart(arg, key);
			}
		}
		
		throw new Exception("No valid key found for argument '"+arg+"'");
	}	
	
	private static String stripAndSaveBridge(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();
		
		//check the arg against all acceptable bridges
		for(String bridge : acceptedBridgeTokens)
		{
			//try to locate the bridge
			int bridgeLocation = arg.indexOf(bridge);
			
			//successfully located bridge
			if(bridgeLocation != -1)
			{
				//add bridge to the list of bridges
				CommandLineArguments.bridgeList.add(bridge);
				
				//return the string minus the bridge
				return StringUtils.removeStart(arg, bridge);
			}
		}
		
		return arg;
		
		//throw new Exception("No valid bridge found for argument '"+arg+"'");
	}
	
	private static String stripAndSaveValue(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();		
				
		//add arg value (even if "") to value list
		CommandLineArguments.valueList.add(arg);		
		
		//return the string (now it should be empty)
		return "";	
	}
	
	private static Boolean hasBridgeToken(String arg)
	{ 		
		for(String bridge : acceptedBridgeTokens)
		{
			if(arg.contains(bridge)) return true;
		}
		
		return false;
	}
}
