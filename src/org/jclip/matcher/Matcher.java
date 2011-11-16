package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.Arguments;
import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;
import org.jclip.interfaces.OptionValidator;
import org.jclip.options.Option;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups matchingOptionGroups = new OptionGroups();
	public OptionGroup matchingGroup = null;
	public OptionGroups optionGroups;
	public Arguments arguments;		
	public Boolean failOnFindingUndefinedRequiredArgs = true;
	//public Boolean failOnFindingUndefinedOptionalArgs = false;
	
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
		doValidationOnOptionGroup();
		
		//do validation at the Option level
		doValidationOnOptions();

		//found a match so return
		if (matchingOptionGroups.groups.size() > 0) return;

		//inform the user of a non-match
		throw new Exception("No match found");
	}

	private boolean doValidationOnOptionGroup() throws Exception
	{
		OptionGroupValidator validator = (OptionGroupValidator) this.matchingGroup.validator;
		
		if(validator != null)
		{
			return validator.validateOptionGroup(this.matchingGroup);
		}		
		else return true;
	}

	private void doValidationOnOptions() throws Exception
	{
		for(Option option : matchingGroup.requiredOptions)
		{
			OptionValidator validator = option.validator;
			String value = this.arguments.getOptionValueFromOptionKey(option.key);
			
			if(validator!=null)
			{				
				if(validator.validateOption(value)==false) 
					throw new Exception("Option with key of '"+option.key+"' and value of '"+value+"' failed its validation routine!");				
			}
		}
	}

	private void matchOptionGroupsOnRequiredOptions()
	{					
		//for each of the original option groups check if the required keys set contains all of the argument keys
		for (OptionGroup optionGroup : optionGroups.groups)
		{			
			ArrayList<String> requiredOptionKeys = optionGroup.requiredKeys;
			ArrayList<String> argKeys = arguments.keyList;
			
			
			if(this.failOnFindingUndefinedRequiredArgs)
			if (argKeys.equals(requiredOptionKeys)) //if there's a match put the OptionGroup into the set of matches
			{
				matchingOptionGroups.groups.add(optionGroup);
			}
			
			if(!this.failOnFindingUndefinedRequiredArgs)
			if (argKeys.containsAll(requiredOptionKeys))
			{
				matchingOptionGroups.groups.add(optionGroup);
			}
		}		
	}

	private void matchOptionGroupsOnOptionalOptions() throws Exception
	{			
		//for each of the matched, required option groups check if the optional keys set contains all of the argument keys
		for (OptionGroup optionGroup : matchingOptionGroups.groups)
		{
			ArrayList<String> optionalOptionKeys = optionGroup.optionalKeys;
			ArrayList<String> argKeys = arguments.keyList;
									
			if (optionalOptionKeys.size()>0 && !optionalOptionKeys.containsAll(argKeys)) //if there's no match then remove the OptionGroup from the set of possible matches
			{
				matchingOptionGroups.groups.remove(optionGroup);
			}
		}
		
		//the one and only, let's use it
		if(matchingOptionGroups.groups.size()==1) this.matchingGroup = matchingOptionGroups.groups.get(0);
		
		//ended up matching on more than one OptionGroup; this is bad
		if(matchingOptionGroups.groups.size()>1) throw new Exception("Oops, more than one match was found!");
		
		//no match found, let's jump back
		if(matchingOptionGroups.groups.size()==0) throw new Exception("No match found!");
	}

	public void passControlToCallbacks()
	{
		//shouldn't be more than one match but for now...
		for(OptionGroup group : matchingOptionGroups.groups)
		{
			//an option group may have more than one callback
			for(Callback callback : group.callbacks)
			{
				callback.execute();
			}
		}		
	}
}
