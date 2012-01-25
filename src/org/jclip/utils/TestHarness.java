package org.jclip.utils;

import org.junit.Test;

public class TestHarness 
{
	public static Object lock = new Object();
	
	@Test
	public void runAll()
	{
		try 
		{
			System.err.println("Test0 START");
			Test0 t0 = new Test0();
			t0.start();
			t0.join();
			
			System.err.println("Test1 START");
			Test1 t1 = new Test1();
			t1.start();
			t1.join();
			
			System.err.println("Test2 START");
			Test2 t2 = new Test2();
			t2.start();
			t2.join();
			
			System.err.println("Test3 START");
			Test3 t3 = new Test3();
			t3.start();
			t3.join();

		
		Test4 t4 = new Test4();
		t4.start();
		t4.join();
		
		Test5 t5 = new Test5();
		t5.start();
		t5.join();
		
		Test6 t6 = new Test6();
		t6.start();
		t6.join();
		
		Test7 t7 = new Test7();
		t7.start();
		t7.join();
			
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
