package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.swing.text.TabStop;

public class DBTest2 {
	
	static String table="uniform_code";
	static int[] checkNum= {1,2,1,2,1,2,4,1};

	public static void main(String[] args) {
		
//		String code[]=  {"25081232","86893514","53317533"};
		
//		for(int i=0;i<code.length;i++) {
//			System.out.println("Verification:"+code[i]+" :"+Verification.checkUniform(code[i]));
//			
//		}

//		selectRandom();
		createUniform();

	}
	
	public static void selectRandom() {
		System.out.println("test begin");
		String sql="select top 10 * from "+table+" order by  NEWID()";
		
		try(
				Statement st=DBConnect.getStatement();
				
				) {
			
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				String code=rs.getString("統一編號");
				System.out.println("code="+code+" Verification: "+Verification.checkUniform(code));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		System.out.println("test end");
		
	}
	
	public static String createUniform() {
		
		
		
		Random rnd=new Random();
		StringBuilder sb=new StringBuilder();
		int sum=0;
		int current;
		boolean isSeven=false;
		for(int i=1;i<=7;i++) {
			
			current=rnd.nextInt(10);
			
			
			
			
			
			
			if (i == 7 && current == 7) { // 特殊狀況:第7個數字=7時

				isSeven = true;

			} else {
				
				int temp = current * checkNum[i-1];

				if (temp >= 10) {

					temp = (temp / 10) + (temp % 10);
//													
				}

				sum += temp;

			}
			
			sb.append(current);

		}
//		System.out.println("sum=" + sum);
		int lastCode;	
		if (isSeven) {
			
			if(rnd.nextBoolean()) {
				
				lastCode=10-(sum+1)%10;
			}else {
				lastCode=10-sum%10;
			}
		
		}else {
			
			lastCode=10-sum%10;
		}
		
		sb.append(lastCode);
		String result=sb.toString();
		
		System.out.println("result:"+result);
		System.out.println("Verification:"+Verification.checkUniform(result));
		return result;
		
		
		
		
		
		
	}

}
