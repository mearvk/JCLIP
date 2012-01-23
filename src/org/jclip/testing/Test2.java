package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.interfaces.Callback;
import org.jclip.matcher.Matcher;
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
public class Test2
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=derp"};	
	static String expectedResult = "Test2.Callback1";
	static String actualResult = null;	
	
	@Test
	public void doTest() 
	{
		try
		{
			Matcher matcher = new Matcher();
			matcher.setArgs(args);
			matcher.setOptionGroups(new OptionGroups1());
			matcher.match();
			matcher.doCallbacks();
			assertTrue("Test2 failed", expectedResult.equals(actualResult));	
		}
		catch (Exception e)
		{
			fail(e.getMessage());
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

