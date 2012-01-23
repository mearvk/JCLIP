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
 * Tests the simplest case where the CL args are correctly specified as defined in the OptionGroups subclass
 * 
 * @author Max Rupplin
 *
 */
public class Test1
{	
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=\"C:\\Users\\Max Rupplin\\Desktop\""};
	static String expectedResult = "Test1.Callback1";
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
			assertTrue("Test1 failed", expectedResult.equals(actualResult));			
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
			OptionGroup createKeyGroup = new OptionGroup();
			createKeyGroup.addRequiredOption(new RequiredOption("cipher"));
			createKeyGroup.addRequiredOption(new RequiredOption("keylength"));
			createKeyGroup.addRequiredOption(new RequiredOption("outputdir"));
			createKeyGroup.addCallback(new Callback1());
			
			this.addOptionGroup(createKeyGroup);
		}
	}

	class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test1.actualResult = "Test1.Callback1";
		}
	}
}

