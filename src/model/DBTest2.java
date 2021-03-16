package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import javax.swing.text.TabStop;

import com.sun.jna.FromNativeContext;

public class DBTest2 {

	static String table = "uniform_code";
	static int[] checkNum = { 1, 2, 1, 2, 1, 2, 4, 1 };

	public static void main(String[] args) {

//		String code[]=  {"25081232","86893514","53317533"};

//		for(int i=0;i<code.length;i++) {
//			System.out.println("Verification:"+code[i]+" :"+Verification.checkUniform(code[i]));
//			
//		}

//		selectRandom();

		int index = 0;
		while (index < 10) {
			createUniform();
			index++;
		}

	}

	public static void selectRandom() {
		System.out.println("test begin");
		String sql = "select top 10 * from " + table + " order by  NEWID()";

		try (Statement st = DBConnect.getStatement();

		) {

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String code = rs.getString("統一編號");
				System.out.println("code=" + code + " Verification: " + Verification.checkUniform(code));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("test end");

	}

	public static String createUniform() {
//		String sql="select * from uniform_table where [統一編號] = ?";

		Random rnd = new Random();

		boolean pass = false;
		String result = null;
		int sum;
		int current;

		while (pass == false) {

			System.out.println("begin: pass now=" + pass);
			StringBuilder sb = new StringBuilder();
			result = "";

			sum = 0;
			boolean isSeven = false;
			for (int i = 1; i <= 7; i++) {

				current = rnd.nextInt(10);

				if (i == 7 && current == 7) { // 特殊狀況:第7個數字=7時

					isSeven = true;

				} else {

					int temp = current * checkNum[i - 1];

					if (temp >= 10) {

						temp = (temp / 10) + (temp % 10);
//													
					}

					sum += temp;

				}

				sb.append(current);
				System.out.println("長度=" + sb.length());

			}
//		System.out.println("sum=" + sum);
			int lastCode = 0;

			if (sum % 10 != 0) {
				// 如果不能整除
				if (isSeven && rnd.nextBoolean()) {
					sum += 1;

				}
				lastCode = (10 - sum % 10) % 10;
			}

			sb.append(lastCode);
			System.out.println("長度2=" + sb.length());
			result = sb.toString();

			System.out.println("result:" + result);
			System.out.println("Verification:" + Verification.checkUniform(result));

			String sql = "select * from uniform_code where 統一編號 = '" + result + "'";

			try (Statement st = DBConnect.getStatement(); ResultSet rs = st.executeQuery(sql);) {

				if (rs.next()) {

					System.out.println("統編重複:" + rs.getString("統一編號"));

				} else {
					pass = true;

				}

			} catch (Exception e) {
				e.printStackTrace();
				pass = false;
			}

		}

		System.out.println("統編產生成功:" + result);
		System.out.println("end");

		return result;
	}

}
