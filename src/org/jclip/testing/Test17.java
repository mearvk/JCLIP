package org.jclip.testing;

import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.exceptions.DuplicateOptionException;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.junit.Test;

/**
 * Tests what should happen when two OptionalOptions are identical in a given OptionGroup
 * 
 * @author Max Rupplin
 *
 */
public class Test17 extends BaseTest 
{
	String[] args = new String[]{"--duplicate1", "--duplicate2"};
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test17.og1");
			og1.addOptionalOption(new OptionalOption("duplicate1"));
			og1.addOptionalOption(new OptionalOption("duplicate1"));
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
			
			//shouldn't get here
			fail();
		}
		catch (Exception e)
		{						
			if(e instanceof DuplicateOptionException)
				System.err.println(e.getMessage());
			else 
				fail("Test17 failed.");
		}
	}
}
