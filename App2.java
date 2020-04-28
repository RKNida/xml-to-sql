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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class App2 {
	public static void main(String[] args) throws FileNotFoundException, IOException {  
	         try {
		    	 Class.forName("com.mysql.jdbc.Driver");

				Connection conn = DriverManager.getConnection( 
				    "jdbc:mysql://localhost:3306/test" , 
				    "root" , 
				    "abc123"
				 );
				OutputStream fileOut = new FileOutputStream("exceldatabase.xls");
				Workbook wb = new HSSFWorkbook();  
	            Sheet sheet = wb.createSheet("employee_db");  
		      
	            Row row     = sheet.createRow(1);  
	            Cell cell;
				ResultSet rs=null;
			      DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
						      rs = meta.getTables(null, null, null, new String[] {
			         "TABLE"
			      });
			      int count = 0;
			      String columnNamePattern = null, catalog = null, schemaPattern = null, tableName = null;
			      ResultSet rsColumns = meta.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
					String columnName;int l=1;
				while (rsColumns.next()) {
					
					 columnName = rsColumns.getString("COLUMN_NAME");
			        	cell = row.createCell(l++);
					      cell.setCellValue(columnName);

				}
			      System.out.println("All table names are in test database:");

			      
			      while (rs.next()) {
			       String   tblName = rs.getString("TABLE_NAME");
			       
			        // PreparedStatement pStatement=conn.prepareStatement(sql);
			         System.out.println("Table Name:-"+tblName);
			         count++;

			         
			         
			         String sql="select * from $tblName";//$tblName

						PreparedStatement statement = conn.prepareStatement(sql);
						String query =sql.replace("$tblName",tblName);
						//statement.setString(1, tblName);
						ResultSet resultSet=statement.executeQuery(query);
					      ResultSetMetaData rsMetaData = resultSet.getMetaData();

					      int n = rsMetaData.getColumnCount();
					     /* for(int j=1;j<=n;j++) {
				            	String columnName=rsMetaData.getColumnName(j);			     
				            	cell = row.createCell(j);
					      cell.setCellValue(columnName);

				            }
							*/
			
						
						
			     
		        		            System.out.println("creating employee db sheet");
		        /*    while (resultSet.next()) {
						  for(int j=1;j<=n;j++) {
						  System.out.print("\t" +resultSet.getString(j) 
			                                  );
			                    
			            }
					  }
					System.out.println("working");
		*/
		      
			    	

		          		            int i = 2;
			      
			      while(resultSet.next()) {

			    	  row = sheet.createRow(i);

			    	  for(int j=1;j<=n;j++) {

			         cell = row.createCell(j);
			         cell.setCellValue(resultSet.getString(j));
			         
			    	  }

			    	  i++;
			      
			      }
			      System.out.println("exceldatabase.xlsx written successfully");

			      
	    	
			      }
		        	wb.write(fileOut);  

	         }catch(Exception e) {  
	            System.out.println(e.getMessage());  
	        }
			   

	    
	}//main  
}