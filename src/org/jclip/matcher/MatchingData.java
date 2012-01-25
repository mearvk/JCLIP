package org.jclip.matcher;

import java.util.ArrayList;

public class MatchingData 
{
	private static ArrayList<String> notes = new ArrayList<String>();
	private static ArrayList<String> errors = new ArrayList<String>();
	
	public static void resetState()
	{
		notes = new ArrayList<String>();
		errors = new ArrayList<String>();
	}
	
	public static void addNote(String msg)
	{
		notes.add(msg);
	}
	
	public static void addError(String msg)
	{
		errors.add(msg);
	}
	
	public static void printNotes()
	{
		for(String msg : notes)
		{
			System.err.println(msg);
		}
	}
	
	public static void printErrors()
	{
		for(String msg : errors)
		{
			System.err.println(msg);
		}
	}
}
