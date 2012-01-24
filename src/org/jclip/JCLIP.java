package org.jclip;

import org.jclip.matcher.Matcher;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.validation.ValidationData;
import org.jclip.validation.ValidationErrorException;
import org.jclip.validation.Validator;

/**
 * Kind of a wrapper class that keeps things simple for users interested in the most public of interfaces
 * 
 * @version 1.0
 * @author Max Rupplin
 */
public class JCLIP 
{
	public Matcher matcher=null;
	public Validator validator=null;
	public OptionGroup matchingGroup = null;
	
	/**
	 * Create a parsing/validating object that handles command line inputs
	 * 
	 * @param args The command line arguments
	 * @throws Exception Kick it back up a level so you can handle exceptions in your own code if you like
	 * @author Max Rupplin
	 */
	public JCLIP(String...args) throws Exception
	{
		//create and initialize a Matcher in order to perform CLI matching
		matcher = new Matcher(args);
		
		//create and initialize a Validator in order to perform validation on parameter values
		validator = new Validator(args);	
	}
		
	/**
	 * The method that calls the necessary Matcher and Validator functions in the correct order
	 * 
	 * @throws Exception
	 * @author Max Rupplin
	 */
	public void run() throws Exception
	{		
		//parse command line arguments 
		parseArgs();
		
		//validate the values
		validateValues();
		
		//move execution to user defined callbacks 
		doCallbacks();
	}
	
	/**
	 * Parses the command line arguments, then attempts to match them a single OptionGroup
	 * 
	 * @throws Exception Typically this will be thrown if no match is found or perhaps more than one match
	 * @author Max Rupplin
	 */
	public void parseArgs() throws Exception
	{
		if(this.matcher==null) throw new NullPointerException("Matcher cannot be null!");
		
		this.matchingGroup = matcher.match();
	}
	
	/**
	 * Validates the command line values (e.g. name=dave so name can't be numeric)
	 * 
	 * @throws Exception Some of kind error while trying to run the validation code
	 * @author Max Rupplin
	 */
	public void validateValues() throws Exception
	{
		if(this.validator==null) throw new NullPointerException("Validator cannot be null!");
		
		if(this.matchingGroup==null) throw new NullPointerException("Must parse command line arguments before validating the associated values!");
		
		this.validator.validate(this.matchingGroup);
		
		if(ValidationData.hasNotes())
			ValidationData.printNotes();
		
		if(ValidationData.hasErrors())
			throw new ValidationErrorException();
	}
	
	/**
	 * Pass off execution to user defined callbacks
	 * @author Max Rupplin
	 */
	public void doCallbacks()
	{
		this.matcher.doCallbacks();
	}
}
