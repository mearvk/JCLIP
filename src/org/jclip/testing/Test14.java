package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.junit.Test;

/**
 * Two OptionGroups of only OptionalOptions where one has extra OptionalOption (should match on closest fit)
 * 
 * @author Max Rupplin
 *
 */
public class Test14 extends BaseTest 
{
	String[] args = new String[]{"--opt1=y", "--opt2=n"};
	private static String expectedResult = "Test14.Callback2";
	private static String actualResult = null;
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test14.og1");
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addOptionalOption(new OptionalOption("opt3"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test14.og2");
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));

			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);
				
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
			Test14.actualResult = "Test14.Callback1";
		}
	}

	private class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test14.actualResult = "Test14.Callback2";
		}
	}	
}
