package org.jclip.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jclip.interfaces.Callback;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.RequiredOption;
import org.junit.Test;

public class Test0 
{
	
	@Test
	public void doTest()
	{
		ArrayList<String> keyset1 = new ArrayList<String>();
		ArrayList<String> keyset2 = new ArrayList<String>();
		
		String one = "herp";
		String two = "derp";
		String thr = "herp";
		String fur = "derp";
		
		keyset1.add(one);
		keyset1.add(two);
		
		keyset2.add(fur);
		keyset2.add(thr);
		
		boolean b1 = keyset1.equals(keyset2);
		boolean b2 = keyset2.equals(keyset1);
		boolean b3 = keyset1.containsAll(keyset2);
		boolean b4 = keyset2.containsAll(keyset1);
		
		return;
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
