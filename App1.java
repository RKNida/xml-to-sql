package com.zensar.FetchingData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Hello world!
 *
 */
public class App1 {

	public static void main(String[] args) throws SQLException {
	      Connection conn = null;
	      try {
	         try {
	            Class.forName("com.mysql.jdbc.Driver");
	         } catch (Exception e) {
	            System.out.println(e);
	         }
	         conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "abc123");
				
	         System.out.println("Connection is created succcessfully:");
	      } catch (Exception e) {
	         System.out.println(e);
	      }
	      ResultSet rs=null,rs1 = null;
	      DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
	      rs = meta.getTables(null, null, null, new String[] {
	         "TABLE"
	      });
	      int count = 0;
	      System.out.println("All table names are in test database:");
	      
	      
	      
	      while (rs.next()) {
	       String   tblName = rs.getString("TABLE_NAME");
	       
	        // PreparedStatement pStatement=conn.prepareStatement(sql);
	         System.out.println("Table Name:-"+tblName);
	         count++;
      
	             
	      String columnNamePattern = null, catalog = null, schemaPattern = null, tableName = null;
	      ResultSet rsColumns = meta.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
			String columnName;
		while (rsColumns.next()) {
			 columnName = rsColumns.getString("COLUMN_NAME");
			 
			
			  


	//String query =sql.replace("$tblName",tblName);
			 String sql="select * from $tblName ";
				System.out.println( "col :-"+columnName);

				PreparedStatement statement = conn.prepareStatement(sql);
				String query =sql.replace("$tblName",tblName);
				//statement.setString(1, tblName);
				ResultSet resultSet=statement.executeQuery(query);
				  while (resultSet.next()) {
		                System.out.println("\t" +resultSet.getString(columnName) 
		                                  );
		                    
		            }
			
			// String columnType = rsColumns.getString("TYPE_NAME");
			// int columnSize = rsColumns.getInt("COLUMN_SIZE");
			

		} // column
		
	      }
		
		
		
		
	      System.out.println(count + " Rows in set ");
	   }
}// app
