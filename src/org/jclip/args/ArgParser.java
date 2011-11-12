package org.jclip.args;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class ArgParser
{
	public Arguments arguments = null;
	public String[] CLargs = null;
	
	public String[] acceptedPrefixTokens = new String[]{ "--","-" };
	public String[] acceptedBridgeTokens = new String[]{ "=" };	
	
	public ArgParser(Arguments arguments, String...CLargs)
	{
		this.arguments = arguments;
		this.CLargs = CLargs;
	}
	
	public void processArgs()
	{
		for (String arg : CLargs)
		{
			String result = null;
						
			try
			{
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
			}
			catch (Exception e)
			{				
				e.printStackTrace();
			}
		}
	}
	
	private String stripAndSavePrefix(String arg) throws Exception
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
				arguments.prefixList.add(prefix);
				
				//return the string minus the prefix
				return StringUtils.removeStart(arg, prefix);
			}
		}
		
		throw new Exception("No valid prefix found for argument '"+arg+"'");
	}
	
	private String stripAndSaveKey(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();
		
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
				arguments.keyList.add(key);
				
				//return the string minus the key
				return StringUtils.removeStart(arg, key);
			}
		}
		
		throw new Exception("No valid key found for argument '"+arg+"'");
	}	
	
	private String stripAndSaveBridge(String arg) throws Exception
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
				arguments.bridgeList.add(bridge);
				
				//return the string minus the bridge
				return StringUtils.removeStart(arg, bridge);
			}
		}		
		
		throw new Exception("No valid bridge found for argument '"+arg+"'");
	}
	
	private String stripAndSaveValue(String arg) throws Exception
	{
		if(arg==null) throw new NullPointerException();
		
		//knock off any leading/trailing whitespace(s)
		arg = arg.trim();		
				
		//add arg value (even if "") to value list
		arguments.valueList.add(arg);		
		
		//return the string (now it should be empty)
		return StringUtils.removeStart(arg,arg);	
	}		
}
