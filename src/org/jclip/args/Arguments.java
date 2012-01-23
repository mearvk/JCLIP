package org.jclip.args;

import java.util.ArrayList;

public class Arguments
{
	public static ArrayList<String> prefixList = new ArrayList<String>();
	public static ArrayList<String> bridgeList = new ArrayList<String>();
	public static ArrayList<String> keyList = new ArrayList<String>();
	public static ArrayList<String> valueList = new ArrayList<String>();
	
	public Arguments(String[] args)
	{
		new ArgParser(this,args).processArgs();
	}	
}
