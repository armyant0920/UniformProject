package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBTest {

	public static void main(String[] args) {
		
		Long start=System.currentTimeMillis();

		String server = "localhost";
		String port = "1433";
		String user = "sa";
		String password = "manager";
		String database = "ExampleDB";
		String table = "uniform_code";
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setMaxTotal(50);
		ds.setMaxIdle(20);

	
		String createTable="CREATE TABLE "+table+" (rowid INTEGER IDENTITY(1,1) PRIMARY KEY ,";
		String sql = "insert into " + table + " (";

		String values = (" values(");
		String path="D:\\專案資料夾\\全國營業(稅籍)登記資料集\\BGMOPEN1\\BGMOPEN1.csv";
		
		File file = new File(path);
		
		List<Integer>errorIndex=new ArrayList<>();
		
		System.out.println("file info:"+file.exists()+" "+file.getAbsolutePath()+" "+file.length());

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//
				
			
		) {
			
			String columnNames[] = br.readLine().split(",");
			for (int i = 0; i < columnNames.length; i++) {
				sql += columnNames[i];
				values += "?";
				createTable+=columnNames[i]+" NVARCHAR(MAX)";
			

				if (i < columnNames.length - 1) {
					sql += ",";
					createTable+=",";
					values += ",";
				} else {
					sql += ")";
					createTable+=")";
					values += ")";
				}
			}

			sql += values;
			System.out.println("insertSQL="+sql);
			System.out.println("createSQL="+createTable);
			System.out.println("end");
			int columnLen = columnNames.length;
			System.out.println("columnLen=" + columnLen);
			

			try (
					Connection conn = ds.getConnection();
					Statement st=conn.createStatement();
					PreparedStatement ps = conn.prepareStatement(sql);)
			{
				System.out.println("create table:"+st.executeUpdate(createTable));
				
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
						try {
							int[] result = ps.executeBatch();
							ps.clearBatch();
							count = 0;
							System.out.println("executed:" + count2);
							
						} catch (Exception e) {
							errorIndex.add(count2);
							
							e.printStackTrace();
						
							count=0;
							
							
							
						}
						

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
		
		System.out.println("ERROR OCCUR:"+errorIndex);
		System.out.println("spent time:"+(System.currentTimeMillis()-start)+"mills");
		System.out.println("END ");

	}
	
	
	public void tempMethod() {
		
		
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
