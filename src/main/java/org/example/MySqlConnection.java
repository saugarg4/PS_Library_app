package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnection {

	public static void main(String[] args) {
		try {
			/************************************* Connection for MYSQL  ************************************************/
		Class.forName("com.mysql.cj.jdbc.Driver"); // Registering the JDBC Driver for mysql
		
		// DriverManager.getConnection is used for creating the connection with the Database
		// and for that we are passing Database Complete Path with UserName and Password
		
		// DriverManager.getConnection is when successfull in creating the connection with the Database
		// it will return the instance of that successfull connection which we store in the Connection Class
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjdbc","root", "root");
		/***********************************************************************************************************/
		
		/************************************* Connection for ORACLE  ************************************************/
		/* Class.forName("oracle.jdbc.driver.OracleDriver"); // Registering the JDBC Driver for oracle
		
		// DriverManager.getConnection is used for creating the connection with the Database
		// and for that we are passing Database Complete Path with UserName and Password
		
		// DriverManager.getConnection is when successfull in creating the connection with the Database
		// it will return the instance of that successfull connection which we store in the Connection Class
		Connection con = DriverManager.getConnection("jdbc:oracle:thin@localhost:1521:xe","system", "oracle"); */
		/***********************************************************************************************************/
		
		Statement stmt = con.createStatement(); // It is used for Creating the SQL Statement Object to be used for writing SQL Queries
		// Write INSERT Query using Statement Object
//		stmt.executeUpdate("insert into emp values(1, 'Mohit Sharma', 35)");
		
		// INSERTION OF DATA USING PREPARE STATEMENT
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your ID");
		int id = Integer.parseInt(buff.readLine());
		System.out.println("Enter your Name");
		String name = buff.readLine();
		System.out.println("Enter your Age");
		int age = Integer.parseInt(buff.readLine());
//		Customer mCustomer = new Customer(id, name, age);
//		// PreparedStatement is used for Creating the SQL Statement Object to be used for writing SQL Queries with Dynamic Values
//		PreparedStatement pStmt = con.prepareStatement("insert into emp values(?,?,?)");
//		pStmt.setInt(1, mCustomer.getId());
//		pStmt.setString(2,  mCustomer.getName());
//		pStmt.setInt(3, mCustomer.getAge());
//		// METHOD FOR EXECUTING THE SQL QUERY
//		pStmt.executeUpdate();
//
//		// Writing Update Query
////		stmt.executeUpdate("update emp set name = 'rishabh pant' where id=101");
//
//		// Writing Delete Query
//		stmt.executeUpdate("delete from emp where id = 101");
//
//		// executeQuery is the method used for Executing GET Query from the Database
//		ResultSet rs = stmt.executeQuery("select * from emp"); // ResultSet Object is used for Storing the result of SQL Query Executed
//		while(rs.next()) {
//			Customer mCustomer1 = new Customer();
//			mCustomer1.setId(rs.getInt(1));
//			mCustomer1.setName(rs.getString(2));
//			mCustomer1.setAge(rs.getInt(3));
//			System.out.println( mCustomer1.getId() + " , " + mCustomer1.getName() + " , " + mCustomer1.getAge() );
//		}
			
		con.close();
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
