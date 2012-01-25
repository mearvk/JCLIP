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
public class Test7
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1", "--opt2"};
	static String expectedResult = "Test7.Callback1";
	static String actualResult = null;		
	
	@Test
	public void doTest() 
	{
		try
		{
			OptionGroups.resetState();
			
			CommandLineArguments.processAndStoreRawArgs(args);
			
			OptionGroup og0 = new OptionGroup("Test7.og0");
			og0.addRequiredOption(new RequiredOption("keylength"));
			og0.addRequiredOption(new RequiredOption("cipher"));
			og0.addRequiredOption(new RequiredOption("outputdir"));
			og0.addRequiredOption(new RequiredOption("req1"));
			og0.addOptionalOption(new OptionalOption("opt1"));
			og0.addOptionalOption(new OptionalOption("opt2"));
			og0.addCallback(new Callback0());
			
			//super set
			OptionGroups.addOptionGroup(og0);		
			
			OptionGroup og1 = new OptionGroup("Test7.og1");
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addCallback(new Callback1());
			
			//proper set
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test7.og2");
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));
			og2.addCallback(new Callback2());
			
			//subset
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

	class Callback0 implements Callback
	{
		@Override
		public void execute()
		{
			Test7.actualResult = "Test7.Callback0";
		}
	}	
	
	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test7.actualResult = "Test7.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test7.actualResult = "Test7.Callback2";
		}
	}
}