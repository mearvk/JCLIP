package org.jclip;

import org.jclip.args.CommandLineArguments;
import org.jclip.exceptions.NoMatchesToValidateException;
import org.jclip.matcher.Matcher;
import org.jclip.matcher.MatchingData;
import org.jclip.options.OptionGroup;
import org.jclip.options.OptionGroups;
import org.jclip.validation.ValidationData;
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
		//reset state (JUnit doesn't reset the JVM after each test)
		OptionGroups.resetState();
		MatchingData.resetState();
		ValidationData.resetState();
		CommandLineArguments.resetState();
		
		//store the CLArgs 
		CommandLineArguments.processAndStoreRawArgs(args);
		
		//create and initialize a Matcher in order to perform CLI matching
		matcher = new Matcher();
		
		//create and initialize a Validator in order to perform validation on parameter values
		validator = new Validator();	
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
		if(this.matcher==null) 
			throw new NullPointerException("Matcher cannot be null!");
		
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
		if(this.validator==null) 
			throw new NullPointerException("Validator cannot be null!");
		
		if(this.matchingGroup==null)
		{
			//note that there were no matches to validate
			ValidationData.addNote(new NoMatchesToValidateException().getMessage());
			
			//return quietly
			return;
		}
		
		//validate the matched OptionGroup at the Option and OptionGroup level
		this.validator.validate(this.matchingGroup);
		
		//print any notes that were stored during validation
		if(ValidationData.hasNotes())
			ValidationData.printNotes();
		
		//print any errors that were stored during validation
		if(ValidationData.hasErrors())
			ValidationData.printErrors();
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
