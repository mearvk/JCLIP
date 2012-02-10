package org.jclip.options;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jclip.exceptions.DuplicateOptionException;
import org.jclip.exceptions.SetEqualityException;

public class OptionGroupSanityChecker
{
	public static void checkNewOptionGroup(OptionGroup newGroup) throws Exception
	{
		checkForSetEquality(newGroup);
		
		checkForDuplicateOptionalOptions(newGroup);
		
		checkForDuplicateRequiredOptions(newGroup);
		
		checkForDuplicateHeterogeneousOptions(newGroup);
	}
	
	
	private static void checkForDuplicateHeterogeneousOptions(OptionGroup newGroup) throws Exception
	{
		Set<String> noDuplicates = new HashSet<String>();
		
		//add the RequiredOptions to the set
		noDuplicates.addAll(newGroup.requiredKeys);
		
		//num of unique req'd keys
		int uniqueRequiredKeyCount = noDuplicates.size();
		
		//reset the set
		noDuplicates.clear();
		
		//add the OptionalOptions to the set
		noDuplicates.addAll(newGroup.optionalKeys);
		
		//num of unique opt'l keys
		int uniqueOptionalKeyCount = noDuplicates.size();
		
		//add back the req'd keys
		noDuplicates.addAll(newGroup.requiredKeys);
		
		//we know that if the sum of the two unique sets is greater than when the sets are in the same set
		//that there was a duplicate option between the required and optional options
		if( uniqueRequiredKeyCount+uniqueOptionalKeyCount > noDuplicates.size() )
		{
			throw new DuplicateOptionException("Duplicate Required/Optional Options detected.");
		}
	}
	
	private static void checkForDuplicateRequiredOptions(OptionGroup newGroup) throws Exception
	{
		//optional options for this group
		ArrayList<String> newGroupRequiredKeys = newGroup.requiredKeys;
		
		//keep track of the unique keys
		Set<String> noDuplicates = new HashSet<String>();
		
		//sets do not all duplicates, thus noDuplicates will have no duplicates
		noDuplicates.addAll(newGroupRequiredKeys);
		
		//see if the noDuplicates has fewer entries, implying there were duplicates in the original
		int noDuplicatesSize = noDuplicates.size();
		
		//for the comparison below
		int originalListSize = newGroupRequiredKeys.size();
		
		//if original is larger the implication is there were duplicates
		if(noDuplicatesSize < originalListSize) 
			throw new DuplicateOptionException("Duplicate RequiredOptions detected.");		
	}
	
	private static void checkForDuplicateOptionalOptions(OptionGroup newGroup) throws Exception
	{
		//optional options for this group
		ArrayList<String> newGroupOptionalKeys = newGroup.optionalKeys;
		
		//keep track of the unique keys
		Set<String> noDuplicates = new HashSet<String>();
		
		//sets do not all duplicates, thus noDuplicates will have no duplicates
		noDuplicates.addAll(newGroupOptionalKeys);
		
		//see if the noDuplicates has fewer entries, implying there were duplicates in the original
		int noDuplicatesSize = noDuplicates.size();
		
		//for the comparison below
		int originalListSize = newGroupOptionalKeys.size();
		
		//if original is larger the implication is there were duplicates
		if(noDuplicatesSize < originalListSize) 
			throw new DuplicateOptionException("Duplicate OptionalOptions detected.");
	}
	
	private static void checkForSetEquality(OptionGroup newGroup) throws Exception
	{
		ArrayList<String> newGroupRequiredKeys = newGroup.requiredKeys;
		ArrayList<String> newGroupOptionalKeys = newGroup.optionalKeys;
		
		for(OptionGroup existingGroup : OptionGroups.groups)
		{
			ArrayList<String> existingGroupRequiredKeys = existingGroup.requiredKeys;
			ArrayList<String> existingGroupOptionalKeys = existingGroup.optionalKeys;
			
			//since list.equals doesn't work worth a damn...
			if(newGroupRequiredKeys.containsAll(existingGroupRequiredKeys) && existingGroupRequiredKeys.containsAll(newGroupRequiredKeys))
			if(newGroupOptionalKeys.containsAll(existingGroupOptionalKeys) && existingGroupOptionalKeys.containsAll(newGroupOptionalKeys))
				throw new SetEqualityException();
		}
	}
}
