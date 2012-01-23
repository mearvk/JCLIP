package org.jclip.testing;

import java.util.ArrayList;

import org.jclip.JCLIP;
import org.jclip.args.Arguments;
import org.jclip.exceptions.DerpException;
import org.jclip.interfaces.Callback;
import org.jclip.interfaces.OptionGroupValidator;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.jclip.validation.ValidationData;
import org.junit.Test;


/**
 * Tests whether correct OG is picked when optional options are defined
 * 
 * @author Max Rupplin
 *
 */
public class Test8
{
	String[] args = new String[]{"--cipher=rsa", "--keylength=1024", "--outputdir=herp", "--opt1", "--opt2"};	
	
	@Test
	public void doTest() 
	{
		try 
		{
			JCLIP runner = new JCLIP(new OptionGroups1(), args);
			runner.run();
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
			og0.addOptionalOption(new OptionalOption("opt1"));
			og0.addOptionalOption(new OptionalOption("opt2"));
			og0.addCallback(new Callback0());
			og0.addValidator(new Validator0());
		
			this.addOptionGroup(og0);
		}
	}
	
	class Validator0 implements OptionGroupValidator
	{
		@Override
		public void validateOptionGroup(OptionGroup group) throws Exception 
		{
			ArrayList<String> keys = Arguments.keyList;
			ArrayList<String> vals = Arguments.valueList;
			
			int cipherIndex = keys.indexOf("cipher");
			
			if(cipherIndex==-1) throw new DerpException();
			
			if(vals.get(cipherIndex)==null) throw new DerpException();
			
			String cipherValue = vals.get(cipherIndex);
			
			if(cipherValue.equals("rsad")) return;
			
			ValidationData.addOptionGroupError("Was expecting 'cipher=rsad' but found instead 'cipher="+cipherValue+"'");
		}
	}

	class Callback0 implements Callback
	{
		@Override
		public void execute()
		{
			System.out.println("Test8 callback called...");
		}
	}	
}