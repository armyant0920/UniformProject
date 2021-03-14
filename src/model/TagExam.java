package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.html.parser.TagElement;

import org.apache.commons.dbcp2.BasicDataSource;

public class TagExam {

	public static void main(String[] args) {

		String server = "localhost";
		String port = "1433";
		String user = "sa";
		String password = "manager";
		String database = "ExampleDB";
		String table = "uniform";
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setMaxTotal(50);
		ds.setMaxIdle(20);

		//
		// �Ѽ�1.table name
		// �Ѽ�2.�h�����W��,
		// �Ѽ�3.�h�ӭ�
		// ��h�W2�P3���t�X�U,�u�n��ƥ��T,�Y��CSV�ɸ�ƶ��ǻP��Ʈw���@�P�]�i�H�פJ

		String sql = "insert into " + table + " (";

		String values = (" values(");

		File file = new File("D:\\�M�׸�Ƨ�\\������~(�|�y)�n�O��ƶ�\\BGMOPEN1\\BGMOPEN1.csv");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));// new
																												// FileReader(csv)

		) {
			// �B�zCSV�����D,��������SQL�y�y

			// ŪCSV���Ĥ@��,�w�]�Υb��","�Ϲj�A�`�N�I�줣���P�z�X�P���榡���������i��
			String columnNames[] = br.readLine().split(",");
			for (int i = 0; i < columnNames.length; i++) {
				sql += columnNames[i];
				values += "?";

				if (i < columnNames.length - 1) {
					sql += ",";
					values += ",";
				} else {
					sql += ")";
					values += ")";
				}
			}

			sql += values;
			System.out.println(sql);
			System.out.println("end");
			int columnLen = columnNames.length;
			System.out.println("columnLen=" + columnLen);

			try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {

				String line = null;
				int count = 0;
				int count2 = 0;
				while ((line = br.readLine()) != null) {
					System.out.println("line=" + line);
					List<String> data = new ArrayList();
					data.addAll(Arrays.asList(line.split(",")));

					if (data.size() < columnLen) {

						System.out.println("data current size:" + data.size());

						while (data.size() < 16) {
							data.add("");
						}
						System.out.println("after size:" + data.size());

					}

					for (int i = 0; i < data.size(); i++) {
						System.out.print(data.get(i) + "\t");
					}
					System.out.println();
					for (int i = 1; i <= columnLen; i++) {
						ps.setString(i, data.get(i - 1));
					}
					ps.addBatch();
					ps.clearParameters();
					count++;
					count2++;

					if (count >= 100) {
						int[] result = ps.executeBatch();
						ps.clearBatch();
						count = 0;
						System.out.println("����" + count2);

					}
				}
//                int[] result = ps.executeBatch();
//                ps.clearBatch();
//                for (int i = 0; i < result.length; i++) {
//                    System.out.printf("index %d result=%s\n", i, result[i]);
//                }

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		//
//		try (Statement st=ds.getConnection().createStatement();
//				PreparedStatement ps=st.getConnection().prepareStatement("insert into category values(?)")){
//			
//			ps.setString(1, "Food");
//			ps.addBatch();
//			ps.clearParameters();
//		
//			int success[]=ps.executeBatch();
//			System.out.println("success count:"+success.length);
//			ps.clearBatch();
//			Connection connection=DriverManager.getConnection(
//					"jdbc:sqlserver://localhost:1433;databaseName=ExampleDB",
//                    "sa", "manager");

//			System.out.println("getConnect");

//			connection.close();
//			if(connection.isClosed()) {
//				System.out.println("close success");
//			}else {
//				System.out.println("close fail");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

}
