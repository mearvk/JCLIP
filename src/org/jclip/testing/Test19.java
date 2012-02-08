package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.exceptions.DuplicateOptionException;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Tests what should happen when a single OptionGroup has identical RequiredOption and OptionalOption options defined
 * 
 * @author Max Rupplin
 *
 */
public class Test19 extends BaseTest 
{
	private String[] args = new String[]{"--duplicate1"};
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test19.og1");
			og1.addRequiredOption(new RequiredOption("duplicate1"));
			og1.addOptionalOption(new OptionalOption("duplicate1"));
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
		}
		catch (Exception e)
		{
			assertTrue("Test19 caught an unexpected exception.", e instanceof DuplicateOptionException);
		}
	}
}
