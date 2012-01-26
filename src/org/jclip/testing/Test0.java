package org.jclip.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Checks if ordering of items in ArrayList instances matter when checking for set equality. 
 * 
 * @author Max Rupplin
 *
 */
public class Test0 extends BaseTest
{	
	@Test
	public void run()
	{	
		ArrayList<String> keyset1 = new ArrayList<String>();
		ArrayList<String> keyset2 = new ArrayList<String>();
				
		String one = "herp";
		String two = "derp";
		String thr = "herp";
		String fur = "derp";
			
		//NOTE ORDERING
		keyset1.add(one);
		keyset1.add(two);
				
		//NOTE ORDERING
		keyset2.add(fur);
		keyset2.add(thr);
				
		boolean b1 = keyset1.equals(keyset2);
		boolean b2 = keyset2.equals(keyset1);
		boolean b3 = keyset1.containsAll(keyset2);
		boolean b4 = keyset2.containsAll(keyset1);
		
		assertTrue("Keysets should NOT be equal; note ordering of ArrayList additions.", !b1);
		assertTrue("Keysets should NOT be equal; note ordering of ArrayList additions.", !b2);
		assertTrue("Keyset should containAll of its twin list.", b3);
		assertTrue("Keyset should containAll of its twin list.", b4);
	}
}
