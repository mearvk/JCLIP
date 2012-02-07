package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.junit.Test;

/**
 * Tests whether Matcher will match when fed a superset of OptionalOptions
 * 
 * @author Max Rupplin
 *
 */
public class Test13 extends BaseTest 
{
	String[] args = new String[]{"--opt1=y", "--opt2=n"};
	private static String expectedResult = "Test13.Callback1";
	private static String actualResult = null;
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test13.og1");
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addOptionalOption(new OptionalOption("opt3"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
			
			assertTrue("Test13 failed...", expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{						
			e.printStackTrace();
		}
	}
	
	private class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test13.actualResult = "Test13.Callback1";
		}
	}	
}
