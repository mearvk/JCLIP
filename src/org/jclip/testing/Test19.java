package org.jclip.testing;

import static org.junit.Assert.assertTrue;

import org.jclip.JCLIP;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.options.OptionalOption;
import org.jclip.options.RequiredOption;
import org.junit.Test;

/**
 * Tests what should happen when a single OptionGroup has identical RequiredOption and OptionalOption options defined
 * 
 * @author Max Rupplin
 *
 */
public class Test19 extends BaseTest 
{
	private String[] args = new String[]{"--opt1", "--opt2", "--opt3"};
	
	@Override
	@Test
	public void run()
	{
		try
		{
			JCLIP jclp = new JCLIP(args);
			
			OptionGroup og1 = new OptionGroup("Test19.og1");
			og1.addRequiredOption(new RequiredOption("opt1"));
			og1.addOptionalOption(new OptionalOption("opt2"));
			og1.addOptionalOption(new OptionalOption("opt3"));
			
			OptionGroups.addOptionGroup(og1);
			
			OptionGroup og2 = new OptionGroup("Test19.og2");
			og2.addOptionalOption(new OptionalOption("opt1"));
			og2.addOptionalOption(new OptionalOption("opt2"));
			og2.addOptionalOption(new OptionalOption("opt3"));			
			
			OptionGroups.addOptionGroup(og2);
				
			jclp.run();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
