package com.hygj.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

	/***
	 * 李听
	 * @return conn;
	 * @throws Exception
	 */
	public static Connection getConn() throws Exception
	{

		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		String url = "jdbc:microsoft:sqlserver://localhost:1433;Databasename=MyEmail"; 
//		 jdbc:microsoft:sqlserver://server_name:1433 
//		Connection conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:1433;Databasename=MyEmail","sa", "admin123");
		Connection conn = DriverManager.getConnection(url,"sa", "admin123");

		return conn;
	}
	public static void main(String[] args) throws Exception{
		 
		System.out.println(DbUtil.getConn());
	}
}
