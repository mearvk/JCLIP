package org.jclip.testing;

import org.jclip.interfaces.Callback;
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
			matcher.setOptionGroups(new MyOptionGroups());			
			matcher.matchArgsToOptionGroup();
			matcher.passControlToCallback();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}				
	}
}

class MyOptionGroups extends OptionGroups
{
	public MyOptionGroups()
	{
		OptionGroup one = new OptionGroup();
		one.addOption(new RequiredOption("cipher"));
		one.addOption(new RequiredOption("keylength"));
		one.addOption(new RequiredOption("outputdir"));
		one.addCallback(new KeyCreatorCallback());
		this.groups.add(one);
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

//class CipherValueValidator