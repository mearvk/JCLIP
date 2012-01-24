package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.interfaces.Callback;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests if a superset OptionGroup (has extra keys) is ignored in favor of a perfect match
 * 
 * @author Max Rupplin
 *
 */
public class Test3
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024"};
	static String expectedResult = "Test3.Callback2";
	static String actualResult = null;		
	
	@Test
	public void doTest() 
	{
		try
		{
			Matcher matcher = new Matcher();
			matcher.setArgs(args);
			//matcher.setOptionGroups(new OptionGroups1());
			matcher.match();
			matcher.doCallbacks();
			assertTrue("Test3 failed", expectedResult.equals(actualResult));				
		}
		catch (Exception e)
		{
			Assert.fail(e.getMessage());
		}		
	}
	
	class OptionGroups1 extends OptionGroups
	{
		public OptionGroups1() throws Exception
		{
			OptionGroup og1 = new OptionGroup();
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addCallback(new Callback1());
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup();
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addCallback(new Callback2());
			
			OptionGroups.addOptionGroup(og2);			
		}
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test3.actualResult = "Test3.Callback1";
		}
	}

	class Callback2 implements Callback
	{
		@Override
		public void execute()
		{
			Test3.actualResult = "Test3.Callback2";
		}
	}
}

