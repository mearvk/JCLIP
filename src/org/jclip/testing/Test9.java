package org.jclip.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jclip.JCLIP;
import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionValidator;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.jclip.validation.ValidationData;
import org.junit.Test;


/**
 * Tests whether validation works at individual Option level
 * 
 * @author Max Rupplin
 *
 */
public class Test9 extends BaseTest
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=512", "--outputdir=herp", "--opt1", "--opt2"};	
	
	@Override
	@Test
	public void run() 
	{
		try 
		{
			JCLIP runner = new JCLIP(args);
			
			OptionGroup og0 = new OptionGroup("Test9.og0");
			og0.addRequiredOption(new RequiredOption("keylength", new KeyLengthValidator()));
			og0.addRequiredOption(new RequiredOption("cipher"));
			og0.addRequiredOption(new RequiredOption("outputdir"));
			og0.addOptionalOption(new OptionalOption("opt1"));
			og0.addOptionalOption(new OptionalOption("opt2"));
			og0.addCallback(new Callback0());
			
			OptionGroups.addOptionGroup(og0);
			
			runner.run();
			
			assertTrue("Test9 failed", !ValidationData.hasErrors());
		} 
		catch (Exception e) 
		{
			System.err.println(e);
			fail();		}
	}
	
	private class KeyLengthValidator implements OptionValidator
	{
		@Override
		public void validateOption(String value) throws Exception
		{		
			Double doub = Double.parseDouble(value);
				
			Double base2log = Math.log(doub)/Math.log(2);
			
			Double round = (double) Math.round(base2log);
			
			Double distance = Math.abs(base2log-round);
			
			if(Math.abs(distance)!=0d) 
				ValidationData.addOptionError(value+" doesn't appear to be a valid value for the keylength parameter.");
			else 
				ValidationData.addNote(value+" looks to be a good value for the keylength parameter.");
		}
		
	}

	private class Callback0 implements Callback
	{
		@Override
		public void execute()
		{
			System.err.println("Test9 callback called as expected.");
		}
	}	
}