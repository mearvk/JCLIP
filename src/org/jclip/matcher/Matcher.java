package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.ArgParser;
import org.jclip.args.Argument;
import org.jclip.args.Arguments;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups optionGroups;
	public OptionGroup matchingGroup;
	public Arguments arguments;	
	public OptionGroups matches = null;

	public Matcher()
	{

	}
	
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
		matches = matchOptionGroupsOnRequiredOptions();
		matches = matchOptionGroupsOnOptionalOptions();

		doValidationOnOptionGroups(matches);
		doValidationOnOptions(matches);

		if (matches.groups.size() > 0) return;

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

	private OptionGroups matchOptionGroupsOnRequiredOptions()
	{
		OptionGroups matches = new OptionGroups();

		for (OptionGroup group : optionGroups.groups)
		{
			ArrayList<String> requiredKeys = group.requiredKeys;
			ArrayList<String> argKeys = arguments.keyList;

			if (requiredKeys.containsAll(argKeys))
			{
				matches.groups.add(group);
			}
		}

		return matches;
	}

	private OptionGroups matchOptionGroupsOnOptionalOptions()
	{
		OptionGroups matches = new OptionGroups();

		for (OptionGroup group : optionGroups.groups)
		{
			ArrayList<String> optionalKeys = group.optionalKeys;
			ArrayList<String> argKeys = arguments.keyList;

			if (optionalKeys.containsAll(argKeys))
			{
				matches.groups.add(group);
			}
		}

		return matches;
	}

	public void passControlToCallback()
	{
		matches.groups.get(0).callback.execute();
	}
}
