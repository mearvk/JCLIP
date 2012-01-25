package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.args.CommandLineArguments;
import org.jclip.interfaces.Callback;
import org.jclip.matcher.Matcher;
import org.jclip.matcher.MatchingData;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;


/**
 * Tests whether optional options are matched correctly
 * 
 * @author Max Rupplin
 *
 */
public class Test5 extends Thread
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1=y", "--opt2=n"};
	static String expectedResult = "Test5.Callback1";
	static String actualResult = null;		
	
	@Test
	public void run() 
	{
		System.err.println("Test5 START");
		
		try
		{
			JCLIP runner = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test5.og1");
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
				
			runner.run();
			
			assertTrue(expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{						
			e.printStackTrace();
		}	
		
		System.err.println("Test5 STOP\n");
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test5.actualResult = "Test5.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test5.actualResult = "Test5.Callback2";
		}
	}
}

