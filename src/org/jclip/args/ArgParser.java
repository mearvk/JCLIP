package org.jclip.args;

import java.util.ArrayList;


public class ArgParser
{
	public String[] prefixes = new String[]{"-","--"};
	public String[] bridges = new String[]{"="};
			
	public ArrayList<Argument> parseArgs(String...args)
	{
		ArrayList<Argument> arguments = new ArrayList<Argument>();
		
		for(String arg : args)
		{
			Argument argument = new Argument(arg);
			
			//save flag prefixes 
			for(String prefix : prefixes)
			{							
				if(arg.startsWith(prefix)) argument.setPrefix(prefix);				
			}
					
			//save flag's key/value pair
			for(String bridge : bridges)
			{		
				int bridgeIndex = arg.indexOf(bridge);
				
				if(bridgeIndex!=0) 
				{
					argument.setBridge(bridge);
					argument.setKey(arg.substring(argument.prefix.length(),bridgeIndex));
					argument.setValue(arg.substring(bridgeIndex+1));
				}
			}
			
			//add result to the persistent list of args
			arguments.add(argument);
		}	
		
		//printArgs(arguments);
		
		return arguments;
	}
	
	public void printArgs(ArrayList<Argument> args)
	{
		for(Argument arg : args)
		{
			System.out.println("Prefix: "+arg.prefix);
			System.out.println("Key: "+arg.key);
			System.out.println("Bridge: "+arg.bridge);
			System.out.println("Value: "+arg.value);
		}
	}
}
