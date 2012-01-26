package org.jclip.testing;

import org.junit.After;
import org.junit.Before;

public abstract class BaseTest extends Thread
{
	@Before
	public void before()
	{
		System.err.println("--- "+this.getClass().getSimpleName()+" STARTS ---");
	}
	
	@After
	public void after()
	{
		System.err.println("--- "+this.getClass().getSimpleName()+" STOPS ---\n");
	}
}
