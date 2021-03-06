package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.CommandLineArguments;
import org.jclip.exceptions.AmbiguousMatchException;
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
		ArrayList<OptionGroup> bestMatches = (ArrayList<OptionGroup>) reqMatches.clone();
		
		//use MAX_VALUE so it will necessarily be replaced first iteration
		int bestMatchUnknownArgCount = Integer.MAX_VALUE;
		
		//remove imperfect matches			
		for	(OptionGroup group : (ArrayList<OptionGroup>)bestMatches.clone())
		{	
			//number of unknown arguments (optional, required)
			int currentMatchUnknownArgCount = getUnknownArgCount(group);
			
			//if it's worse than our best match in terms of # of unknown args
			if( currentMatchUnknownArgCount > bestMatchUnknownArgCount )
			{
				//store an explanation of what we're doing
				MatchingData.addNote("Removing OptionGroup "+group+" from the list of matches b/c it had "+currentMatchUnknownArgCount+" unknown argument(s).");
				
				//remove the OptionGroup from the set of best matches
				bestMatches.remove(group);	
			}
			//this match is at least as good as our current best so let's keep it
			else 
			{
				//store the new best match in terms of number of unknown arguments
				bestMatchUnknownArgCount = currentMatchUnknownArgCount;
			}
		}
		
		//remove any old best matches from the set of best matches
		for( OptionGroup match : (ArrayList<OptionGroup>)bestMatches.clone() )
		{					
			int unknownArgCount = getUnknownArgCount(match);
			
			if(unknownArgCount > bestMatchUnknownArgCount)
			{
				bestMatches.remove(match);					
			}
		}
		
		if(bestMatches.size()==0) MatchingData.addNote("No matches found!");
		
		if(bestMatches.size()>1) 
		{
			MatchingData.addError("More than one match found!");
			throw new AmbiguousMatchException();
		}
		
		if(bestMatches.size()==1)
		{
			this.matchingGroup = bestMatches.get(0);
			
			int argCount = getUnknownArgCount(this.matchingGroup);
			
			if(argCount==0)
				MatchingData.addNote("Perfect match found on OptionGroup "+bestMatches.get(0)+".");
			else
				MatchingData.addNote("Imperfect match found on OptionGroup "+this.matchingGroup+" w/ "+argCount+" extra arg(s).");
		}
	}
	
	@SuppressWarnings("unchecked")
	private int getUnknownArgCount(OptionGroup group)
	{
		ArrayList<String> requiredKeys = (ArrayList<String>) group.requiredKeys.clone();
		ArrayList<String> optionalKeys = (ArrayList<String>) group.optionalKeys.clone();
		ArrayList<String> cmdLineArgs = (ArrayList<String>) CommandLineArguments.keyList.clone();
		
		//remove all the requiredKeys from the arg list
		cmdLineArgs.removeAll(requiredKeys);
		
		//return the number of remaining options
		return Math.abs(cmdLineArgs.size()-optionalKeys.size());
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

	public void doCallbacks()
	{
		if(this.matchingGroup==null)
		{
			System.err.println("No matching OptionGroups were found; no callbacks will be called.");
			return;
		}
		
		for(Callback callback : this.matchingGroup.callbacks)
			callback.execute();
	}
}
