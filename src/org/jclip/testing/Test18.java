package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.exceptions.DuplicateOptionException;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Tests what should happen when a single OptionGroup has two identical RequiredOptions
 * 
 * @author Max Rupplin
 *
 */
public class Test18 extends BaseTest 
{
String[] args = new String[]{"--duplicate1"};
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test18.og1");
			og1.addRequiredOption(new RequiredOption("duplicate1"));
			og1.addRequiredOption(new RequiredOption("duplicate1"));
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
			
			//shouldn't get here
			fail();
		}
		catch (Exception e)
		{						
			assertTrue("Test18 caught an unexpected exception.", e instanceof DuplicateOptionException);
		}
	}
}
