package org.jclip.options;

import java.util.ArrayList;

import org.jclip.exceptions.SetEqualityException;

public class OptionGroups
{
	public ArrayList<OptionGroup> groups = new ArrayList<OptionGroup>();
	
	public void addOptionGroup(OptionGroup newGroup) throws Exception
	{
		ArrayList<String> newGroupRequiredKeys = newGroup.requiredKeys;
		ArrayList<String> newGroupOptionalKeys = newGroup.optionalKeys;
		
		for(OptionGroup existingGroup : groups)
		{
			ArrayList<String> existingGroupRequiredKeys = existingGroup.requiredKeys;
			ArrayList<String> existingGroupOptionalKeys = existingGroup.optionalKeys;
			
			if(newGroupRequiredKeys.containsAll(existingGroupRequiredKeys) && existingGroupRequiredKeys.containsAll(newGroupRequiredKeys))
			if(newGroupOptionalKeys.containsAll(existingGroupOptionalKeys) && existingGroupOptionalKeys.containsAll(newGroupOptionalKeys))
				throw new SetEqualityException();
		}
		
		groups.add(newGroup);
	}		
}
