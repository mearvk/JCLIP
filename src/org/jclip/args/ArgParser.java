package org.jclip.args;

import java.util.ArrayList;

public class ArgParser
{
	public static String[] prefixes = new String[]
	{ "-", "--" };
	public static String[] bridges = new String[]
	{ "=" };

	public static ArrayList<String> getKeys(ArrayList<Argument> arguments)
	{
		ArrayList<String> keys = new ArrayList<String>();

		for (Argument argument : arguments)
		{
			keys.add(argument.key);
		}

		return keys;
	}

	public static ArrayList<Argument> parseArgs(String... args)
	{
		ArrayList<Argument> arguments = new ArrayList<Argument>();

		for (String arg : args)
		{
			Argument argument = new Argument(arg);

			// save flag prefixes
			for (String prefix : prefixes)
			{
				if (arg.startsWith(prefix)) argument.setPrefix(prefix);
			}

			// save flag's key/value pair
			for (String bridge : bridges)
			{
				int bridgeIndex = arg.indexOf(bridge);

				if (bridgeIndex != 0)
				{
					argument.setBridge(bridge);
					argument.setKey(arg.substring(argument.prefix.length(),
							bridgeIndex));
					argument.setValue(arg.substring(bridgeIndex + 1));
				}
			}

			// add result to the persistent list of args
			arguments.add(argument);
		}

		// printArgs(arguments);

		return arguments;
	}

	public void printArgs(ArrayList<Argument> args)
	{
		for (Argument arg : args)
		{
			System.out.println("Prefix: " + arg.prefix);
			System.out.println("Key: " + arg.key);
			System.out.println("Bridge: " + arg.bridge);
			System.out.println("Value: " + arg.value);
		}
	}
}
