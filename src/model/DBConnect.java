package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnect {
	
	private static final String server = "localhost";
	private static final String port = "1433";
	private static final String user = "sa";
	private static final String password = "manager";
	private static final String database = "MYDB";
	private static final String table = "uniform_code";
	
	public static  Connection getConnection() throws SQLException {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://" + server + ":" + port + ";databaseName=" + database);
		ds.setUsername(user);
		ds.setPassword(password);
		ds.setMaxTotal(50);
		ds.setMaxIdle(20);
		return ds.getConnection();
		
	}
	
	public static Statement getStatement() throws SQLException {
		
		return getConnection().createStatement();
		
	}
	
	

}
