package org.jclip.testing;

import static org.junit.Assert.*;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.junit.Test;

/**
 * Tests whether Matcher will match when fed only proper set of OptionalOptions
 * 
 * @author Max Rupplin
 *
 */
public class Test12 extends BaseTest 
{
	String[] args = new String[]{"--opt1=y", "--opt2=n"};
	private static String expectedResult = "Test12.Callback1";
	private static String actualResult = null;
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test12.og1");
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
				
			jclp.run();
			
			assertTrue(expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{						
			System.err.println(e);
			fail();
		}
	}
	
	private class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test12.actualResult = "Test12.Callback1";
		}
	}	
}
