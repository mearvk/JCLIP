package org.jclip.testing;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.exceptions.AmbiguousMatchException;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.junit.Test;

/**
 * Tests whether two equally good but non-perfect matches kick up an AmbigousMatchException
 * 
 * @author Max Rupplin
 *
 */
public class Test16 extends BaseTest 
{
	String[] args = new String[]{"--opt1=y", "--opt2=n"};
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test15.og1");
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addOptionalOption(new OptionalOption("missing1"));
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test15.og2");
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));
			og2.addOptionalOption(new OptionalOption("missing2"));

			OptionGroups.addOptionGroup(og2);
				
			jclp.run();
			
			//shouldn't get here
			fail();
		}
		catch (Exception e)
		{						
			assertTrue("Test16 caught a weird exception.", e instanceof AmbiguousMatchException);
		}
	}
}
