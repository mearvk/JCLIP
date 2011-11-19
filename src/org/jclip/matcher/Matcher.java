package org.jclip.matcher;

import java.util.ArrayList;
import java.util.Iterator;
import org.jclip.args.Arguments;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups matchingOptionGroups = new OptionGroups();
	public OptionGroup matchingGroup = null;
	public OptionGroups optionGroups;
	public Arguments arguments;		
	//public Boolean failOnFindingUndefinedArgs = true;
	public ArrayList<OptionGroup> matchedRequiredArgs = new ArrayList<OptionGroup>();
	public ArrayList<OptionGroup> matchedOptionalArgs = new ArrayList<OptionGroup>();
	
	public Matcher()
	{
		
	}
	
	public Matcher(String... args) throws Exception
	{
		if(args==null || args.length==0) throw new Exception("Matcher requires non-null and non-empty arguments parameter.");
		
		this.arguments = new Arguments(args);
	}	

	public Matcher(OptionGroups groups, String... args)
	{
		this.optionGroups = groups;
		this.arguments = new Arguments(args);
	}	

	public void setArgs(String... args)
	{
		this.arguments = new Arguments(args);
	}

	public void setOptionGroups(OptionGroups groups)
	{
		this.optionGroups = groups;
	}

	public OptionGroup match() throws Exception
	{
		//find the option groups that have at least the required options
		matchOptionGroupsOnRequiredOptions();
		
		//from the set of matching option groups find the option group that has the correct optional options
		matchOptionGroupsOnOptionalOptions();
		
		//try and find a perfect match
		finalizeMatchingOptionGroups();

		//found a match so return
		if (matchingGroup != null) return this.matchingGroup;

		//inform the user of a non-match
		throw new Exception("No match found");
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
				i.remove();
		}
		
		if(perfectMatches.size()==0) throw new Exception("No matches found!");
		
		if(perfectMatches.size()>1) throw new Exception("More than one match found!");
		
		if(perfectMatches.size()==1) this.matchingGroup = perfectMatches.get(0);
	}
	
	@SuppressWarnings("unchecked")
	private int getUnknownArgCount(OptionGroup group)
	{
		ArrayList<String> requiredKeys = group.requiredKeys;
		ArrayList<String> optionalKeys = group.optionalKeys;
		ArrayList<String> clonedArgs = (ArrayList<String>) this.arguments.keyList.clone();
		
		//remove all the requiredKeys from the arg list
		clonedArgs.removeAll(requiredKeys);
		
		//remove all the optionalKeys from the arg list
		clonedArgs.removeAll(optionalKeys);
		
		//return the number of remaining options
		return clonedArgs.size();
	}

	private void matchOptionGroupsOnRequiredOptions() throws Exception
	{		 	
		//for each of the original option groups check if the required keys set contains all of the argument keys
		for (OptionGroup optionGroup : optionGroups.groups)
		{			
			ArrayList<String> requiredOptionKeys = optionGroup.requiredKeys;
			ArrayList<String> argKeys = arguments.keyList;			 
			
			if (argKeys.containsAll(requiredOptionKeys)) //if there's a match put the OptionGroup into the set of matches
			{
				this.matchedRequiredArgs.add(optionGroup);				
			}			
			//else throw new Exception("Command line args are missing one or more required Option");
		}	
	}

	private void matchOptionGroupsOnOptionalOptions() throws Exception
	{			
		//for each of the matched, required option groups check if the optional keys set contains all of the argument keys
		for (OptionGroup optionGroup : optionGroups.groups)
		{
			ArrayList<String> optionalOptionKeys = optionGroup.optionalKeys;
			ArrayList<String> argKeys = arguments.keyList;
									
			if (argKeys.containsAll(optionalOptionKeys)) //if there's a match put the OptionGroup into the set of matches
			{
				this.matchedOptionalArgs.add(optionGroup);				
			}
			else throw new Exception("Command line args are missing one or more optional Option");
		}

	}

	public void doCallbacks()
	{
		for(Callback callback : this.matchingGroup.callbacks)
			callback.execute();
	}
}
