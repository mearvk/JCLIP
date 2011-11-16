package org.jclip.testing;

import static org.junit.Assert.*;
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
public class Test4
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024"};
	static String expectedResult = "Test4.Callback2";
	static String actualResult = null;		
	
	@Test
	public void doTest() 
	{
		try
		{
			Matcher matcher = new Matcher();
			matcher.setArgs(args);
			matcher.setOptionGroups(new OptionGroups1());
			matcher.matchArgsToOptionGroup();
			matcher.passControlToCallbacks();
			fail("Test4 failed");
		}
		catch (Exception e)
		{			
			assertTrue(e instanceof SetEqualityException);
			e.printStackTrace();
		}		
	}
	
	class OptionGroups1 extends OptionGroups
	{
		public OptionGroups1() throws Exception
		{
			OptionGroup og1 = new OptionGroup();
			og1.addRequiredOption(new RequiredOption("keylength"));
			og1.addRequiredOption(new RequiredOption("cipher"));
			og1.addCallback(new Callback1());
			
			this.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup();
			og2.addRequiredOption(new RequiredOption("cipher"));
			og2.addRequiredOption(new RequiredOption("keylength"));
			og2.addCallback(new Callback2());
			
			this.addOptionGroup(og2);			
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

