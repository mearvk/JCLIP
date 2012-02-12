package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.jclip.JCLIP;
import org.jclip.args.CommandLineArguments;
import org.jclip.exceptions.DerpException;
import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.jclip.validation.ValidationData;
import org.jclip.validation.ValidationErrorException;
import org.junit.Test;


/**
 * Tests whether validation works at OptionGroup level
 * 
 * @author Max Rupplin
 *
 */
public class Test8 extends BaseTest
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1", "--opt2"};	
	
	@Override
	@Test
	public void run() 
	{
		try 
		{
			JCLIP runner = new JCLIP(args);
			
			OptionGroup og0 = new OptionGroup("Test8.og0");
			og0.addRequiredOption(new RequiredOption("keylength"));
			og0.addRequiredOption(new RequiredOption("cipher"));
			og0.addRequiredOption(new RequiredOption("outputdir"));
			og0.addOptionalOption(new OptionalOption("opt1"));
			og0.addOptionalOption(new OptionalOption("opt2"));
			og0.addCallback(new Callback0());
			og0.addValidator(new Validator0());
			
			OptionGroups.addOptionGroup(og0);
			
			runner.run();
		} 
		catch (Exception e) 
		{
			boolean b = e instanceof ValidationErrorException;
			
			assertTrue("Test 8 failed", b);
		}
	}
	
	private class Validator0 implements OptionGroupValidator
	{
		@Override
		public void validateOptionGroup(OptionGroup group) throws Exception 
		{
			ArrayList<String> keys = CommandLineArguments.keyList;
			ArrayList<String> vals = CommandLineArguments.valueList;
			
			int cipherIndex = keys.indexOf("cipher");
			
			if(cipherIndex==-1) throw new DerpException();
			
			if(vals.get(cipherIndex)==null) throw new DerpException();
			
			String cipherValue = vals.get(cipherIndex);
			
			if(cipherValue.equals("rsad")) return;
			
			ValidationData.addOptionGroupError("Expected 'cipher=rsad' but found instead 'cipher="+cipherValue+"'.");
		}
	}

	private class Callback0 implements Callback
	{
		@Override
		public void execute()
		{
			System.err.println("Test8 callback called as expected.");
		}
	}	
}