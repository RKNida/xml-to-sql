package com.zensar.FetchingData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
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
public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "abc123");

			Statement statement = connect.createStatement();
			//ResultSet resultSet = statement.executeQuery("select * from emp_tbl");
			DatabaseMetaData md = connect.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while (rs.next()) {
				System.out.println("\n=== TABLE: " + rs.getString(3));
				
			}String columnNamePattern = null, catalog = null, schemaPattern = null, tableName = null;
			ResultSet rsColumns = md.getColumns(catalog, schemaPattern, tableName, columnNamePattern);
				
			String columnName;

			while (rsColumns.next()) {
				 columnName = rsColumns.getString("COLUMN_NAME");
				  
				
				// String columnType = rsColumns.getString("TYPE_NAME");
				// int columnSize = rsColumns.getInt("COLUMN_SIZE");
				System.out.println("\t" + columnName);
				

			} // column
			
			
			
			
			
	
			
			
			
			try (OutputStream fileOut = new FileOutputStream("Javatpoint.xls")) {
				System.out.println("creating employee db sheet");

				Workbook wb = new HSSFWorkbook();
				Sheet sheet = wb.createSheet("employee_db");

				Row row = sheet.createRow(1);
				Cell cell;

				cell = row.createCell(1);
				cell.setCellValue("EMP ID");
				cell = row.createCell(2);
				cell.setCellValue("EMP NAME");
				cell = row.createCell(3);
				cell.setCellValue("DEG");
				cell = row.createCell(4);
				cell.setCellValue("SALARY");
				cell = row.createCell(5);
				cell.setCellValue("DEPT");
				int i = 2;

				
				System.out.println("exceldatabase.xlsx written successfully");

				wb.write(fileOut);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} catch (SQLException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}// main
}// app
