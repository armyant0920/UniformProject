package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(String.format("%08d", 123));
		reallyTest();
		
		
	}
	
	public static void reallyTest() {
		
		Long start=System.currentTimeMillis();
//		int count=0;
		List<String>list=new ArrayList<>();
		
		String temp=null;
		DecimalFormat df=new DecimalFormat();
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(2);
		
		
		for(int i=0;i<Math.pow(10, 8);i++) {
			temp=String.format("%08d", i);
			System.out.println("temp="+temp);
//			temp=df.format(i);
//			
//			temp=temp.replaceAll(",","");
//			
//			
//			System.out.println("temp2:"+temp);
			
			if(Verification.checkUniform(temp))
				{list.add(temp);}
			if(i%100==0) {
				
				System.out.println("目前檢查"+i+"筆");
				
				
			}
			
			
			
			
		}
		
		System.out.println("共花費"+(System.currentTimeMillis()-start)+"millis" );
		System.out.println("總共有"+list.size()+"個合法值");
	}

}
