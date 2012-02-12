package org.jclip.testing;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.jclip.JCLIP;
import org.jclip.args.CommandLineArguments;
import org.jclip.exceptions.DerpException;
import org.jclip.exceptions.ValidationErrorException;
import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;


/**
 * Tests whether validation works at OptionGroup level
 * 
 * @author Max Rupplin
 *
 */
public class Test8 extends BaseTest
{
	String[] args = new String[]{"--cipher=rsad", "--keylength=1024", "--outputdir=herp", "--opt1", "--opt2"};	
	
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
			if(e instanceof ValidationErrorException)
			{
				System.err.println(e);
			}
			else
			{
				System.err.println("Test8 caught an unknown exception of "+e.getClass());
				fail();
			}
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
			
			//cipher doesn't even exist
			if(cipherIndex==-1) 
				throw new DerpException();
			
			//somehow 'cipher' wasn't found in the List
			if(vals.get(cipherIndex)==null) 
				throw new DerpException();
			
			String cipherValue = vals.get(cipherIndex);
			
			if(cipherValue.equals("rsa")) return;
						
			throw new ValidationErrorException("Expected 'cipher=rsa' but found instead 'cipher="+cipherValue+"'.");
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