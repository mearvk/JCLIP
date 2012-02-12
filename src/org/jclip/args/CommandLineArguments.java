package org.jclip.args;

import java.util.ArrayList;

public class CommandLineArguments
{
	public static ArrayList<String> prefixList = null;
	public static ArrayList<String> bridgeList = null;
	public static ArrayList<String> keyList = null;
	public static ArrayList<String> valueList = null;
	
	public static void resetState()
	{
		prefixList = new ArrayList<String>();
		bridgeList = new ArrayList<String>();
		keyList = new ArrayList<String>();
		valueList = new ArrayList<String>();
	}
	
	public static void processAndStoreRawArgs(String...args) throws Exception
	{	
		ArgParser.processArgs(args);
	}
}
