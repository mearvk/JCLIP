package org.jclip.matcher;

import java.util.ArrayList;

import org.jclip.args.ArgParser;
import org.jclip.args.Argument;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;

public class Matcher
{
	public OptionGroups optionGroups;
	public OptionGroup matchingGroup;
	public ArrayList<Argument> arguments;
	public ArrayList<String> argumentKeys;
	public OptionGroups matches = null;

	public Matcher()
	{

	}

	public Matcher(OptionGroups groups, String... args)
	{
		this.optionGroups = groups;
		this.arguments = ArgParser.parseArgs(args);
		this.argumentKeys = ArgParser.getKeys(arguments);
	}

	public void setArgs(String... args)
	{
		this.arguments = ArgParser.parseArgs(args);
		this.argumentKeys = ArgParser.getKeys(arguments);
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
			ArrayList<String> argKeys = argumentKeys;

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
			ArrayList<String> argKeys = argumentKeys;

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
