package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.iterators.LoopingIterator;
import org.jboss.vfs.TempDir;

public class DecryptTool {
	
	
	//定義檢查範圍
	static int start=97;
	static int end=122;
	
	
	
	static List<Integer>checkCode=new ArrayList<Integer>();
	
	static void Loop() {
		
		
	}
	
	
	public static String getPWD(String s) {
		int length=s.length();
		int current;
		
		
		for(int i=0;i<length;i++) {
			checkCode.add(start);//添加初始值
			
		}
		
		
		
		while(checkCode.get(0)<end) {//如果第一碼<最大值
			
			StringBuilder sb=new StringBuilder();
			
			//從最後一碼開始檢查,如果數字大於最大值,前一碼進位,目前位置回到初始值
						
			current=checkCode.size()-1;
			
			boolean add=true;
			while(current>=0) {//逐個檢查每個字
				
				int tempN=checkCode.get(current);
				
				
				if(tempN<end) {
					
					checkCode.set(current, tempN++);
					
					
				}else {
					
					tempN=checkCode.get(current);
					checkCode.set(current, start);
					checkCode.set(current-1, tempN++);			
					
				}
				
				sb.append(Character.toString((char)(int)checkCode.get(current)));
			}
				
			
	
			current=checkCode.get(0);
			
			for(int i=0;i<length;i++) {
						
				if(checkCode.get(i)>end) {
					checkCode.get(i);
					
					
				}
		
			}
		
			
		}
		StringBuilder sb=new StringBuilder();
		
		
		
//		for(int i=0;i<length;i++) {
//			
//		}
		

		
			return null;
		
		
		
		
	}
	

}
