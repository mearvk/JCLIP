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
 * Tests whether proper set of optional options are matched correctly when coupled with proper set of RequiredOptions
 * 
 * @author Max Rupplin
 *
 */
public class Test5 extends BaseTest
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1=y", "--opt2=n"};
	private static String expectedResult = "Test5.Callback1";
	private static String actualResult = null;		
	
	@Override
	@Test
	public void run() 
	{	
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
	}

	private class Callback1 implements Callback
	{
		@Override
		public void execute()
		{
			Test5.actualResult = "Test5.Callback1";
		}
	}
}

