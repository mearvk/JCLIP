package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.args.CommandLineArguments;
import org.jclip.interfaces.Callback;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;


/**
 * Tests whether correct OG is picked when optional options are defined
 * 
 * @author Max Rupplin
 *
 */
public class Test6
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1", "--opt2"};
	static String expectedResult = "Test6.Callback1";
	static String actualResult = null;		
	
	@Test
	public void doTest() 
	{
		try
		{
			OptionGroups.resetState();
			
			CommandLineArguments.processAndStoreRawArgs(args);
			
			OptionGroup og1 = new OptionGroup("Test6.og1");
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test6.og2");
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));
			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);	
			
			Matcher matcher = new Matcher();
			matcher.match();
			matcher.doCallbacks();
			assertTrue("Expected result was '"+expectedResult+"', actual result was '"+actualResult+"'", expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{						
			e.printStackTrace();
		}
		
		TestHarness.lock.notify();
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test6.actualResult = "Test6.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test6.actualResult = "Test6.Callback2";
		}
	}
}

