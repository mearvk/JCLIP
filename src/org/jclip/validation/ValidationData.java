package org.jclip.validation;

import java.util.ArrayList;

public class ValidationData 
{
	//eventually move this to ValidationError type
	private static ArrayList<String> optionGroupErrors = new ArrayList<String>();
	private static ArrayList<String> optionErrors = new ArrayList<String>();
	private static ArrayList<String> notes = new ArrayList<String>();
	
	public static boolean hasNotes()
	{
		return notes.size()>0;
	}
	
	public static boolean hasErrors()
	{
		return optionGroupErrors.size()>0 || optionErrors.size()>0;
	}
			
	public static void addOptionGroupError(String msg)
	{
		optionGroupErrors.add(msg);
	}
	
	public static void addOptionError(String msg)
	{
		optionErrors.add(msg);
	}
	
	public static void printErrors()
	{
		for(String msg : optionGroupErrors)
		{
			System.err.println(msg);
		}
		
		for(String msg : optionErrors)
		{
			System.err.println(msg);
		}
	}
	
	public static void printNotes()
	{
		for(String msg : notes)
		{
			System.out.println(msg);
		}
	}
}
