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

		selectRandom();

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
	
	public static void createUniform() {
		
		
		
		Random rnd=new Random();
		StringBuilder sb=new StringBuilder();
		int sum=0;
		boolean isSeven=false;
		for(int i=1;i<=8;i++) {
			
			int temp=rnd.nextInt(10)*checkNum[i];
			
			
			sb.append(rnd.nextInt(10));
			
			
			
			if (i == 6 && temp == 7) { // 特殊狀況:第7個數字=7時

				isSeven = true;

			} else {

				int temp = current * checkCode[i];

				if (temp >= 10) {

					temp = (temp / 10) + (temp % 10);
//					System.out.println("temp="+temp);								
				}

				sum += temp;

			}

		}
//		System.out.println("sum=" + sum);

		if (isSeven) {
			if (sum % 10 == 0 || (sum + 1) % 10 == 0) {
				return true;
			} else {
				return false;
			}
		} else {

			return (sum % 10 == 0);

		}
			
			
			
		}
		
		
		
		
		
		
	}

}
