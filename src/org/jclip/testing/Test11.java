package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Tests whether ordering matters when dealing with superset of OptionalOptions (same as Test10 but order is reversed)
 * 
 * @author Max Rupplin
 *
 */
public class Test11 extends BaseTest 
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1=y", "--opt2=n"};
	private static String expectedResult = "Test11.Callback2";
	private static String actualResult = null;
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test11.og1");
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addOptionalOption(new OptionalOption("opt3"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test11.og2");
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addRequiredOption(new RequiredOption("outputdir"));
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));

			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);
				
			jclp.run();
			
			assertTrue(expectedResult.equals(actualResult));
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
			Test11.actualResult = "Test11.Callback1";
		}
	}

	private class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test11.actualResult = "Test11.Callback2";
		}
	}	
}
