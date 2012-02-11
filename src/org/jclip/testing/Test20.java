package org.jclip.testing;

import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.exceptions.DuplicateOptionException;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Checks to see if improperly prefixed options are caught and flagged
 * 
 * @author Max Rupplin
 *
 */
public class Test20 extends BaseTest
{
	private String[] args = new String[]{"_opt", "__dir"};
	
	@Test
	@Override
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test20.og1");
			og1.addRequiredOption(new RequiredOption("opt"));
			og1.addOptionalOption(new OptionalOption("dir"));
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
		}
		catch (Exception e)
		{
			if(e instanceof DuplicateOptionException)
				System.err.println(e.getMessage());
			else 
				fail("Test19 failed.");
		}
	}
}
