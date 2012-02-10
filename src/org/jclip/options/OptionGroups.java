package org.jclip.options;

import java.util.ArrayList;

public class OptionGroups
{
	public static ArrayList<OptionGroup> groups = new ArrayList<OptionGroup>();
	
	public static void resetState()
	{
		groups = null;
		groups = new ArrayList<OptionGroup>();
	}
	
	public static void addOptionGroup(OptionGroup newGroup) throws Exception
	{
		OptionGroupSanityChecker.checkNewOptionGroup(newGroup);
		
		groups.add(newGroup);
	}	
}
