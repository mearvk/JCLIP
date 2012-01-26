package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Tests whether a formal subset is ignored in favor of a perfect match 
 * 
 * @author Max Rupplin
 *
 */
public class Test2 extends Thread
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=derp"};	
	static String expectedResult = "Test2.Callback1";
	static String actualResult = null;	
	
	@Test
	public void run() 
	{
		System.err.println("Test2 START");
		
		try
		{
			JCLIP runner = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test2.og1");
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test2.og2");
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);				
			
			runner.run();
			
			assertTrue("Test2 failed", expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		
		System.err.println("Test2 STOP\n");
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test2.actualResult = "Test2.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test2.actualResult = "Test2.Callback2";
		}
	}
}

