package org.jclip.testing;

import java.util.ArrayList;

import org.junit.Test;

public class Test0 
{
	
	@SuppressWarnings("unused")
	@Test
	public void doTest()
	{
		Thread t = new Thread()
		{
			public void run()
			{
				
				ArrayList<String> keyset1 = new ArrayList<String>();
				ArrayList<String> keyset2 = new ArrayList<String>();
				
				String one = "herp";
				String two = "derp";
				String thr = "herp";
				String fur = "derp";
				
				keyset1.add(one);
				keyset1.add(two);
				
				keyset2.add(fur);
				keyset2.add(thr);
				
				boolean b1 = keyset1.equals(keyset2);
				boolean b2 = keyset2.equals(keyset1);
				boolean b3 = keyset1.containsAll(keyset2);
				boolean b4 = keyset2.containsAll(keyset1);
				
				System.out.println("Derp");
				
				TestHarness.lock.notify();
			}
		};
		
		t.start();
	}
}
