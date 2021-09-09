package util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.StaticBucketMap;
import org.junit.jupiter.api.Test;

class LooperTest {

	@Test
	void test() {

		String pwd = "abc";

		String pwds[] = { "we", "aids", "!#$@" };// "ccc", "GASW", "we", "aids","!#$@","

		int start = 33;
		int end = 126;
		List<Integer> checkCode = new ArrayList<Integer>();
		for (int i = 1; i <= 2; i++) {

			checkCode.add(start);

		}
		System.out.println("size=" + checkCode.size());
		System.out.println(checkCode);

		for (String s : pwds) {

			System.out.println("pwd=" + findCode(s, s.length(), start, end));

		}

	}
	
	
	@Test 
	public void test1() {
		
		int start = 0;//33
		int end = 33333333;//126
		int maxlength=4;		

		System.out.println("begin");
		Long nowLong=System.currentTimeMillis();
			
		nowLong=System.currentTimeMillis();
		
		long count2=0;
		for(long i=0;i<99999999;i++) {
			count2++;
			if(count2%333==0) {
				System.out.println("count2="+count2);
			}
			
			
		}
		System.out.println(count2);
		
		
		long time1=System.currentTimeMillis()-nowLong;
	
		
		System.out.println("end 1");
		
		
		List<Thread>threads=new ArrayList<>();
//		MissionThread m1=new MissionThread(start, end, maxlength);
		Thread m1=new Thread(new Mission(start,end));
		m1.setName("m1");
		Thread m2=new Thread(new Mission(start,end));
		m1.setName("m2");
		Thread m3=new Thread(new Mission(start,end));
		m1.setName("m3");
//		m1.setName("m1");
		threads.add(m1);	
//		MissionThread m2=new MissionThread(start, end, maxlength);
//		m1.setName("m2");
		threads.add(m2);
//		MissionThread m3=new MissionThread(start, end, maxlength);
//		m1.setName("m3");
		threads.add(m3);
		
		nowLong=System.currentTimeMillis();
		for(Thread m :threads) {
			m.start();
			try {
				m.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		long time2=System.currentTimeMillis()-nowLong;
	
		System.out.println("end 2");
		System.out.println("time1= "+time1);
		System.out.println("time2= "+time2);
	}
	
	public class TestThread extends Thread {
		
		int count=0;
		boolean pause=false;
		
		public void setPause() {
			if(this.pause==true) {
			this.pause=false;
			}else {
				this.pause=true;
				
			}
		}
		public boolean getPause() {
			
			return this.pause;
		}
		public void run() {
			
				while(count<10000) {
				count++;
				
//				System.out.println(count);
				}
				try {
					System.out.println("wait...");
					pause=true;
					
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
		
		public int getCount() {
			return this.count;
		}
	}
	
	@Test 
	public void test2() {
		int index=0;
		
		
//		Thread t1=new Thread(new Mission(0,3333));
//		
//		Thread t2=new Thread(new Mission(0,3333));
		
		TestThread t1=new TestThread();
		TestThread t2=new TestThread();
		TestThread t3=new TestThread();
		List<TestThread>threads=new ArrayList<>();
		threads.add(t1);
		threads.add(t2);
		threads.add(t3);
		List<Boolean>statue=new ArrayList<Boolean>();
		statue.add(false);
		
		for(TestThread t:threads) {
			
			t.start();
			
		}
	
		while(true) {
			
			if(t1.getPause()==true && t2.getPause()==true  && t3.getPause()==true) {
				System.out.println("reset");
				for(TestThread t:threads) {
					t.setPause();
				}
			}
			
			
			
			
			
		
			
		}
		
		
		
		
		
//		t1.start();
//		while(index<1000) {
//			
//			if(t1.getCount()%10==0) {
//				try {
//				
//					t1.wait(1000);
//					System.out.println("pause");
////					t1.notify();
//					
////					t1.resume();
////					t1.notify();
//					System.out.println("resume");
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		}
		

//		System.out.println("test end");	
	
	}
	
	
	
	
	public class MissionThread extends Thread {

		int start;
		int end;
		int maxlength;
		int count=0;

		public MissionThread(int start, int end, int maxlength) {
			this.start = start;
			this.end = end;	

		}

		StringBuilder sb = new StringBuilder();
		List<Integer> nums = new ArrayList<Integer>();

		@SuppressWarnings("rawtypes")
		List getList() {
			return nums;
		}

		
		public void run() {
			
			for(long i=0;i<1000000000;i++) {
				
//				System.out.println(this.getName()+"  :index="+i);
				
				count++;
			}
			
			System.out.println(this.getName()+" complete");
				

//			nums.add(start);
//			int temp;
//			while (nums.size() <= maxlength) {
//				sb.delete(0, nums.size());
//				for (int i = 0; i < nums.size(); i++) {
//					temp = nums.get(i);
//					if (i == 0) {// 如果是第一位數
//						nums.set(i, temp + 1);// 數字+1
//												
//					}
//					if (temp > end) {
//						// 如果達到目前位數上限,則下一位數+1,此位數歸零
//						nums.set(i, start);
//						temp = nums.get(i);
//						// 如果已經到達目前最大長度,長度+1
//						if (i == nums.size() - 1) {
//							nums.add(start);
//
//						} else {
//							nums.set(i + 1, nums.get(i + 1) + 1);
//						}
//
//					}
//					sb.append(Character.toString((char) temp));
//					
//
//				}
//				String result = sb.toString();
				/**/
//				System.out.println(result+"test....");
//				if(pwd.equals(result)) {
//					System.out.println("find pwd:"+result);
//					
//					System.out.println("spend time:"+(System.currentTimeMillis()-now)+"millis");
//					return result;
//					
//					
//				}

			}

		}		
		


	public static String findCode(String pwd, int maxlength, int start, int end) {

		Long now = System.currentTimeMillis();

		StringBuilder sb = new StringBuilder();

		List<Integer> nums = new ArrayList<Integer>();

		nums.add(start);
		int temp;
		while (nums.size() <= maxlength) {
			sb.delete(0, nums.size());
			for (int i = 0; i < nums.size(); i++) {
				temp = nums.get(i);
				if (i == 0) {// 如果是第一位數

					nums.set(i, temp + 1);// 數字+1
				}
				if (temp > end) {
					// 如果達到目前位數上限,則下一位數+1,此位數歸零
					nums.set(i, start);
					temp = nums.get(i);
					// 如果已經到達目前最大長度,長度+1
					if (i == nums.size() - 1) {
						nums.add(start);

					} else {
						nums.set(i + 1, nums.get(i + 1) + 1);
					}

				}
				sb.append(Character.toString((char) temp));

			}
			String result = sb.toString();
//			System.out.println(result);
			if (pwd.equals(result)) {
				System.out.println("find pwd:" + result);

				System.out.println("spend time:" + (System.currentTimeMillis() - now) + "millis");
				return result;

			}

		}

		System.out.println("spend time:" + (System.currentTimeMillis() - now) + "millis");
		return null;
	}

	

}
