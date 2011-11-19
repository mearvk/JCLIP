package org.jclip.testing;

import static org.junit.Assert.*;
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
			Matcher matcher = new Matcher(args);
			matcher.setOptionGroups(new OptionGroups1());
			matcher.matchArgsToOptionGroup();
			matcher.passControlToCallbacks();
			assertTrue("Expected result was '"+expectedResult+"', actual result was '"+actualResult+"'", expectedResult.equals(actualResult));
		}
		catch (Exception e)
		{						
			e.printStackTrace();
		}		
	}
	
	class OptionGroups1 extends OptionGroups
	{
		public OptionGroups1() throws Exception
		{
			OptionGroup og0 = new OptionGroup();
			og0.addRequiredOption(new RequiredOption("keylength"));
			og0.addRequiredOption(new RequiredOption("cipher"));
			og0.addRequiredOption(new RequiredOption("outputdir"));
			og0.addRequiredOption(new RequiredOption("req1"));
			og0.addOptionalOption(new OptionalOption("opt1"));
			og0.addOptionalOption(new OptionalOption("opt2"));
			og0.addCallback(new Callback0());
			
			//super set
			this.addOptionGroup(og0);		
			
			OptionGroup og1 = new OptionGroup();
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addRequiredOption(new RequiredOption("outputdir"));
			og1.addOptionalOption(new OptionalOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addCallback(new Callback1());
			
			//proper set
			this.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup();
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));
			og2.addCallback(new Callback2());
			
			//subset
			this.addOptionGroup(og2);			
		}
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