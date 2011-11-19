package org.jclip;

import org.jclip.exceptions.NoMatchFoundException;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.validation.Validator;

public class JCLIP 
{
	private Matcher matcher=null;
	private Validator validator=null;
	
	public JCLIP(String...args)
	{
		try
		{
			matcher = new Matcher(args);
			validator = new Validator(args);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
		
	public void run() throws Exception
	{		
		OptionGroup matchingOptionGroup = matcher.match();
		
		if(matchingOptionGroup==null) 
			throw new NoMatchFoundException();
		
		validator.validate(matchingOptionGroup);
		
		matcher.doCallbacks();
	}
}
