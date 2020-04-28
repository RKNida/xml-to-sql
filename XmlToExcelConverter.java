package com.zensar.FetchingData;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
	 * Created by Filipe Ares (filipe.ares@jbaysolutions.com) - http://blog.jbaysolutions.com
	 * Date: 17-08-2015.
	 */
	public class XmlToExcelConverter {
	    private static Workbook workbook;
	    private static int rowNum;

	    private final static int SUBSTANCE_NAME_COLUMN = 0;
	    private final static int SUBSTANCE_ENTRY_FORCE_COLUMN = 1;
	    private final static int SUBSTANCE_DIRECTIVE_COLUMN = 2;
	    private final static int PRODUCT_NAME_COLUMN = 3;
	    private final static int PRODUCT_CODE_COLUMN = 4;
	    private final static int PRODUCT_MRL_COLUMN = 5;
	    private final static int APPLICATION_DATE_COLUMN = 6;


	    public static void main(String[] args) throws Exception {
	        getAndReadXml();
	    }


	    /**
	     *
	     * Downloads a XML file, reads the substance and product values and then writes them to rows on an excel file.
	     *
	     * @throws Exception
	     */
	    private static void getAndReadXml() throws Exception {
	        System.out.println("getAndReadXml");

	        File xmlFile = new File("/home/lenovo/EclipseWorkspace/FetchingData/Sample.xml");
	        /* If you have the xml file locally, replace the above code by the following line:
	        */

	        initXls();

	        Sheet sheet = workbook.getSheetAt(0);

	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlFile);
	        System.out.println("jnefjks");

	        NodeList nList = doc.getElementsByTagName("Substances");
	        for (int i = 0; i < nList.getLength(); i++) {
	            System.out.println("Processing element " + (i+1) + "/" + nList.getLength());
	            Node node = nList.item(i);
	            if (node.getNodeType() == Node.ELEMENT_NODE) {
	                Element element = (Element) node;
	                String substanceName = element.getElementsByTagName("Name").item(0).getTextContent();
	                String entryForce = element.getElementsByTagName("entry_force").item(0).getTextContent();
	                String directive = element.getElementsByTagName("directive").item(0).getTextContent();

	                NodeList prods = element.getElementsByTagName("Product");
	                for (int j = 0; j < prods.getLength(); j++) {
	                    Node prod = prods.item(j);
	                    if (prod.getNodeType() == Node.ELEMENT_NODE) {
	                        Element product = (Element) prod;
	                        String prodName = product.getElementsByTagName("Product_name").item(0).getTextContent();
	                        String prodCode = product.getElementsByTagName("Product_code").item(0).getTextContent();
	                        String lmr = product.getElementsByTagName("MRL").item(0).getTextContent();
	                        String applicationDate = product.getElementsByTagName("ApplicationDate").item(0).getTextContent();

	                        Row row = sheet.createRow(rowNum++);
	                        Cell cell = row.createCell(SUBSTANCE_NAME_COLUMN);
	                        cell.setCellValue(substanceName);

	                        cell = row.createCell(SUBSTANCE_ENTRY_FORCE_COLUMN);
	                        cell.setCellValue(entryForce);

	                        cell = row.createCell(SUBSTANCE_DIRECTIVE_COLUMN);
	                        cell.setCellValue(directive);

	                        cell = row.createCell(PRODUCT_NAME_COLUMN);
	                        cell.setCellValue(prodName);

	                        cell = row.createCell(PRODUCT_CODE_COLUMN);
	                        cell.setCellValue(prodCode);

	                        cell = row.createCell(PRODUCT_MRL_COLUMN);
	                        cell.setCellValue(lmr);

	                        cell = row.createCell(APPLICATION_DATE_COLUMN);
	                        cell.setCellValue(applicationDate);
	                    }
	                }
	            }
	        }


	        FileOutputStream fileOut = new FileOutputStream("/home/lenovo/EclipseWorkspace/FetchingData/Javatpoint.xls");
	        workbook.write(fileOut);
	         
	        fileOut.close();

	      

	        System.out.println("getAndReadXml finished, processed " + nList.getLength() + " substances!");
	    }


	    /**
	     * Initializes the POI workbook and writes the header row
	     */
	    private static void initXls() {
	        workbook = new HSSFWorkbook();

	        CellStyle style = workbook.createCellStyle();
	        Font boldFont = workbook.createFont();
	      //  boldFont.setBoldweight(true);
	        style.setFont(boldFont);
	        style.setAlignment(CellStyle.ALIGN_CENTER);

	        Sheet sheet = workbook.createSheet();
	        rowNum = 0;
	        Row row = sheet.createRow(rowNum++);
	        Cell cell = row.createCell(SUBSTANCE_NAME_COLUMN);
	        cell.setCellValue("Substance name");
	        cell.setCellStyle(style);

	        cell = row.createCell(SUBSTANCE_ENTRY_FORCE_COLUMN);
	        cell.setCellValue("Substance entry_force");
	        cell.setCellStyle(style);

	        cell = row.createCell(SUBSTANCE_DIRECTIVE_COLUMN);
	        cell.setCellValue("Substance directive");
	        cell.setCellStyle(style);

	        cell = row.createCell(PRODUCT_NAME_COLUMN);
	        cell.setCellValue("Product name");
	        cell.setCellStyle(style);

	        cell = row.createCell(PRODUCT_CODE_COLUMN);
	        cell.setCellValue("Product code");
	        cell.setCellStyle(style);

	        cell = row.createCell(PRODUCT_MRL_COLUMN);
	        cell.setCellValue("MRL");
	        cell.setCellStyle(style);

	        cell = row.createCell(APPLICATION_DATE_COLUMN);
	        cell.setCellValue("Application Date");
	        cell.setCellStyle(style);

	    }
	}

