package org.jclip.matcher;

import java.util.ArrayList;
import java.util.Iterator;

import org.jclip.args.CommandLineArguments;
import org.jclip.exceptions.NoOptionGroupsException;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroup matchingGroup = null;
	public ArrayList<OptionGroup> matchedRequiredArgs = new ArrayList<OptionGroup>();
	public ArrayList<OptionGroup> matchedOptionalArgs = new ArrayList<OptionGroup>();
	
	public Matcher()
	{
		
	}	

	public OptionGroup match() throws Exception
	{
		//find the option groups that have at least the required options
		matchOptionGroupsOnRequiredOptions();
		
		//from the set of matching option groups find the option group that has the correct optional options
		matchOptionGroupsOnOptionalOptions();
		
		//try and find a perfect match
		finalizeMatchingOptionGroups();
		
		//print errors/notes
		MatchingData.printErrors();
		MatchingData.printNotes();

		//found a match so return
		if (matchingGroup != null) return this.matchingGroup;

		//inform the user of a non-match
		MatchingData.addNote("No match found");
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void finalizeMatchingOptionGroups() throws Exception
	{
		ArrayList<OptionGroup> reqMatches = this.matchedRequiredArgs;
		ArrayList<OptionGroup> optMatches = this.matchedOptionalArgs;
		ArrayList<OptionGroup> perfectMatches = (ArrayList<OptionGroup>) reqMatches.clone();
		
		//keep only the OptionGroup instances that match both required and optional Options
		perfectMatches.retainAll(optMatches);
		
		//remove imperfect matches			
		for	(Iterator<OptionGroup> i=perfectMatches.iterator(); i.hasNext();)
		{
			//next group in the list
			OptionGroup group = i.next();
			
			//if group has unknown args remove it from list of perfect matches
			if(getUnknownArgCount(group)!=0)
			{
				MatchingData.addNote("Removing OptionGroup "+group+" from the list of matches b/c it had "+getUnknownArgCount(group)+" unknown argument(s).");
				i.remove();
				
			}
		}
		
		if(perfectMatches.size()==0) MatchingData.addNote("No matches found!");
		
		if(perfectMatches.size()>1) MatchingData.addError("More than one match found!");
		
		if(perfectMatches.size()==1)
		{
			this.matchingGroup = perfectMatches.get(0);
			MatchingData.addNote("Matched on "+perfectMatches.get(0));
		}
	}
	
	@SuppressWarnings("unchecked")
	private int getUnknownArgCount(OptionGroup group)
	{
		ArrayList<String> requiredKeys = group.requiredKeys;
		ArrayList<String> optionalKeys = group.optionalKeys;
		ArrayList<String> clonedArgs = (ArrayList<String>) CommandLineArguments.keyList.clone();
		
		//remove all the requiredKeys from the arg list
		clonedArgs.removeAll(requiredKeys);
		
		//remove all the optionalKeys from the arg list
		clonedArgs.removeAll(optionalKeys);
		
		//return the number of remaining options
		return clonedArgs.size();
	}

	private void matchOptionGroupsOnRequiredOptions() throws Exception
	{		 	
		if(OptionGroups.groups == null) throw new NoOptionGroupsException();
		
		//for each of the original option groups check if the required keys set contains all of the argument keys
		for (OptionGroup optionGroup : OptionGroups.groups)
		{			
			ArrayList<String> requiredOptionKeys = optionGroup.requiredKeys;
			ArrayList<String> argKeys = CommandLineArguments.keyList;			 
			
			if (argKeys.containsAll(requiredOptionKeys)) //if there's a match put the OptionGroup into the set of matches
			{
				this.matchedRequiredArgs.add(optionGroup);	
				MatchingData.addNote("Matched OptionGroup "+optionGroup+" on all its REQUIRED options [TRUE]");
			}			
			else MatchingData.addNote("Matched OptionGroup "+optionGroup+" on all its REQUIRED options [FALSE]");
		}	
	}

	private void matchOptionGroupsOnOptionalOptions() throws Exception
	{			
		//for each of the matched, required option groups check if the optional keys set contains all of the argument keys
		for (OptionGroup optionGroup : OptionGroups.groups)
		{
			ArrayList<String> optionalOptionKeys = optionGroup.optionalKeys;
			ArrayList<String> argKeys = CommandLineArguments.keyList;
									
			if (argKeys.containsAll(optionalOptionKeys)) //if there's a match put the OptionGroup into the set of matches
			{
				this.matchedOptionalArgs.add(optionGroup);	
				MatchingData.addNote("Matched OptionGroup "+optionGroup+" on all its OPTIONAL options [TRUE]");
			}
			else MatchingData.addNote("Matched OptionGroup "+optionGroup+" on all its OPTIONAL options [FALSE]");
		}

	}

	public void doCallbacks()
	{
		for(Callback callback : this.matchingGroup.callbacks)
			callback.execute();
	}
}
