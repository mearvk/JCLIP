package org.jclip.args;

import java.util.ArrayList;

public class Arguments
{
	public ArrayList<String> prefixList = new ArrayList<String>();
	public ArrayList<String> bridgeList = new ArrayList<String>();
	public ArrayList<String> keyList = new ArrayList<String>();
	public ArrayList<String> valueList = new ArrayList<String>();
	
	public Arguments(String[] args)
	{
		new ArgParser(this,args).processArgs();
	}	
}
