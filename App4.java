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
	public class App4 {
		static int cellCount=1;

		public static void main(String[] args) throws FileNotFoundException, IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "abc123");
				OutputStream fileOut = new FileOutputStream("exceldatabase.xls");
				Workbook wb = new HSSFWorkbook();
				Sheet sheet = wb.createSheet("employee_db");
				Row row = sheet.createRow(1);
				Cell cell;

				// Fetching table names

				DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
				ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
				String tableName = null, columnName;
				int rowCount = 2;
				ResultSet rsColumns = meta.getColumns(null, null, tableName, null);

				// Fetch Column Names ...Row=1
				for (int i = 1; rsColumns.next(); i++) {

					columnName = rsColumns.getString("COLUMN_NAME");
					cell = row.createCell(i);
					cell.setCellValue(columnName);

				}

				// Passing table names dynamically to query
				while (rs.next()) {
					String tblName = rs.getString("TABLE_NAME");
					System.out.println("Table Name:-" + tblName);
					String sql = "select * from $tblName";
					PreparedStatement statement = conn.prepareStatement(sql);
					String query = sql.replace("$tblName", tblName);
					ResultSet resultSet = statement.executeQuery(query);
					ResultSetMetaData rsMetaData = resultSet.getMetaData();
					int columnCount = rsMetaData.getColumnCount();
					
					for (rowCount=2;resultSet.next();rowCount++) {
						row = sheet.createRow(rowCount);
						for (int j = 1; j <= columnCount; j++) {
							cell = row.createCell(j);
							cell.setCellValue(resultSet.getString(j));
						}
					}
					System.out.println("exceldatabase.xlsx written successfully");
				}
				wb.write(fileOut);
				int noOfColumns = sheet.getRow(2).getPhysicalNumberOfCells();
				System.err.println("no"+noOfColumns);
			

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}// main
	}

	/*
	 * 
	 * public static void writeXLSFile() throws IOException {
	 * 
	 * String excelFileName = "C:/Test.xls";//name of excel file
	 * 
	 * String sheetName = "Sheet1";//name of sheet
	 * 
	 * HSSFWorkbook wb = new HSSFWorkbook(); HSSFSheet sheet =
	 * wb.createSheet(sheetName) ;
	 * 
	 * //iterating r number of rows for (int r=0;r < 5; r++ ) { HSSFRow row =
	 * sheet.createRow(r);
	 * 
	 * //iterating c number of columns for (int c=0;c < 5; c++ ) { HSSFCell cell =
	 * row.createCell(c);
	 * 
	 * cell.setCellValue("Cell "+r+" "+c); } }
	 * 
	 * FileOutputStream fileOut = new FileOutputStream(excelFileName);
	 * 
	 * //write this workbook to an Outputstream. wb.write(fileOut); fileOut.flush();
	 * fileOut.close(); }
	 */

