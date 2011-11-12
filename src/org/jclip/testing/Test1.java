package org.jclip.testing;

import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionValidator;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;

public class Test1
{
	public static void main(String...args)
	{
		try
		{
			Matcher matcher = new Matcher();
			matcher.setArgs(args);
			matcher.setOptionGroups(new KeyGeneratorOptionGroups());
			matcher.matchArgsToOptionGroup();			
			matcher.passControlToCallback();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

class KeyGeneratorOptionGroups extends OptionGroups
{
	public KeyGeneratorOptionGroups()
	{
		OptionGroup createKeyGroup = new OptionGroup();
		createKeyGroup.addRequiredOption(new RequiredOption("cipher", new CipherValueValidator()));
		createKeyGroup.addRequiredOption(new RequiredOption("keylength"));
		createKeyGroup.addRequiredOption(new RequiredOption("outputdir"));
		createKeyGroup.addCallback(new KeyCreatorCallback());
		this.groups.add(createKeyGroup);
	}	
}

class KeyCreatorCallback implements Callback
{
	@Override
	public void execute()
	{
		System.out.println("Let's create some keys!");
	}	
}

class CipherValueValidator implements OptionValidator
{
	public Boolean validate(String value)
	{
		return value!=null && value.equalsIgnoreCase("rsa") || value.equalsIgnoreCase("aes");
	}
}