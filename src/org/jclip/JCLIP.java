package org.jclip;

import org.jclip.exceptions.NoMatchFoundException;
import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.validation.Validator;

public class JCLIP 
{
	public Matcher matcher=null;
	public Validator validator=null;
	public OptionGroup matchingGroup = null;
	
	public JCLIP(String...args) throws Exception
	{
		matcher = new Matcher(args);
		validator = new Validator(args);	
	}
		
	public void run() throws Exception
	{		
		parseArgs();
		
		validateValues();
		
		doCallbacks();
	}
	
	public void setOptionGroups(OptionGroups groups)
	{
		this.matcher.optionGroups=groups;
	}
	
	public void parseArgs() throws Exception
	{
		if(this.matcher==null) throw new NullPointerException("Matcher cannot be null!");
		
		this.matchingGroup = matcher.match();
	}
	
	public void validateValues() throws Exception
	{
		if(this.validator==null) throw new NullPointerException("Validator cannot be null!");
		if(this.matchingGroup==null) throw new NullPointerException("Must parse command line arguments before validating the associated values!");
		
		this.validator.validate(this.matchingGroup);
	}
	
	public void doCallbacks()
	{
		this.matcher.doCallbacks();
	}
}
