package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.Arguments;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups matchingOptionGroups = new OptionGroups();
	public OptionGroups optionGroups;
	public Arguments arguments;		
	
	public Matcher(String... args)
	{
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

	public void matchArgsToOptionGroup() throws Exception
	{
		//find the option groups that have at least the required options
		matchOptionGroupsOnRequiredOptions();
		
		//from the set of matching option groups find the option group that has the correct optional options
		matchOptionGroupsOnOptionalOptions();

		//do validation at the OptionGroup level
		doValidationOnOptionGroups(matchingOptionGroups);
		
		//do validation at the Option level
		doValidationOnOptions(matchingOptionGroups);

		//found a match so return
		if (matchingOptionGroups.groups.size() > 0) return;

		//inform the user of a non-match
		throw new Exception("No match found");
	}

	private boolean doValidationOnOptionGroups(OptionGroups groups)
	{
		return false;
	}

	private boolean doValidationOnOptions(OptionGroups groups)
	{
		return false;
	}

	private void matchOptionGroupsOnRequiredOptions()
	{					
		//for each of the original option groups check if the required keys set contains all of the argument keys
		for (OptionGroup optionGroup : optionGroups.groups)
		{			
			ArrayList<String> requiredKeys = optionGroup.requiredKeys;
			ArrayList<String> argKeys = arguments.keyList;

			//if there's a match put the OptionGroup into the set of matches
			if (requiredKeys.containsAll(argKeys))
			{
				matchingOptionGroups.groups.add(optionGroup);
			}
		}		
	}

	private void matchOptionGroupsOnOptionalOptions()
	{			
		//for each of the matched, required option groups check if the optional keys set contains all of the argument keys
		for (OptionGroup optionGroup : matchingOptionGroups.groups)
		{
			ArrayList<String> optionalKeys = optionGroup.optionalKeys;
			ArrayList<String> argKeys = arguments.keyList;
			
			//if there's no match then remove the OptionGroup from the set of possible matches
			if (optionalKeys.size()>0 && !optionalKeys.containsAll(argKeys))
			{
				matchingOptionGroups.groups.remove(optionGroup);
			}
		}
	}

	public void passControlToCallbacks()
	{
		//shouldn't be more than one match but for now...
		for(OptionGroup group : matchingOptionGroups.groups)
		{
			group.callback.execute();
		}		
	}
}
