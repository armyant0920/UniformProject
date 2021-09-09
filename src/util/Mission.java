package util;

import java.util.ArrayList;
import java.util.List;

public class Mission implements Runnable{
	
	Thread thread;
	int start;
	int end;
	int maxlength;
	int count=0;
	int temp;
	String pwd;
	List<Integer> nums ;
	String name;
	StringBuilder sb;
	String result;
	List<Thread> missions;
	boolean find=false;
	long now;
	public Mission(int start, int end,int maxlength,String pwd,String name,Thread thread) {//, int maxlength
		this.start = start;
		this.end = end;
		this.name=name;
		this.pwd=pwd;
		this.maxlength=maxlength;
		this.sb=new StringBuilder();
		this.nums = new ArrayList<Integer>();
		nums.add(start);
		now=System.currentTimeMillis();
		missions=new ArrayList<Thread>();

	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void addMission(Thread t) {
		if(t!=this) {
			missions.add(m);
			
		}
	}
	
	public void endThis() {
		
		System.out.println(this.getName()+"執行結束,共"+(System.currentTimeMillis()-now) +"秒");
		
		
	}
	
	

	
	
	@Override
	public void run() {
		
		
		while(nums.size()<=maxlength && find==false) {
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
			result=sb.toString();
			System.out.println(name+" : " +result);
			if(pwd.equals(result)) {
				System.out.println("find pwd:"+result);
				find=true;
				
				
				for(Mission m:missions) {
					m.endThis();
				}
				
				
				break;
			}else {
			
			}
		
			
		
		}
		endThis();
		
		
		
		
		
		
		
		
		
	}
	
	
} 
