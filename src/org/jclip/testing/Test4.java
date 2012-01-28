package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.exceptions.SetEqualityException;
import org.jclip.interfaces.Callback;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;
import org.junit.Test;


/**
 * Tests whether two identical sets are rejected while trying to create OptionGroup instances
 * 
 * @author Max Rupplin
 *
 */
public class Test4 extends BaseTest
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024"};
	private static String expectedResult = "Test4.Callback2";
	private static String actualResult = null;		
	
	@Override
	@Test
	public void run() 
	{	
		try
		{
			JCLIP runner = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test4.og1");
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test4.og2");
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);	
			
			Matcher matcher = new Matcher();
			matcher.match();
			matcher.doCallbacks();
			
			runner.run();
			
			fail("Test4 failed");
		}
		catch (Exception e)
		{			
			assertTrue("Something unexpected in Test4 occurred.",e instanceof SetEqualityException);
			System.out.println("Test 4 succeeded in detecting set equality in OptionGroup instances");
			//e.printStackTrace();
		}
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test4.actualResult = "Test4.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test4.actualResult = "Test4.Callback2";
		}
	}
}

