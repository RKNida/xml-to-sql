package com.zensar.FetchingData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLToExcel6 {
	private static Workbook wb = new HSSFWorkbook();
	private static Sheet sheet = wb.createSheet("spreadSheet");
	private static Row row = sheet.createRow(1);
	private static Row row1 = sheet.createRow(0);

	private static Cell cell;
	private static int m=2;

	public static void printNode(NodeList nodeList) {
		System.out.println("row-->"+m);
		System.out.println(nodeList.getLength()+"len");
		for(int i=0,j=0;i<nodeList.getLength();i++){

			Node node=nodeList.item(i);
			
			if (node.hasChildNodes()){
				System.out.println("\n"+node.getNodeName());
				String nodeName=node.getNodeName();
				
				if (node.getChildNodes().getLength()==1 ) {
					Node last=nodeList.item(nodeList.getLength()-2);
					String lastNodeName=last.getNodeName();
					
					System.out.println("-->"+node.getTextContent()+"cell -->"+j);
					cell = row1.createCell(j);

					cell.setCellValue(node.getNodeName());
					
					cell = row.createCell(j++);

					cell.setCellValue(node.getTextContent());
					
					
					if(nodeName.equals(lastNodeName)){
						row=sheet.createRow(m);
						System.out.println("Before if"+node.getNodeName()+" length"+last.getNodeName()+m);
						m++;
						System.out.println(m);
					}
					}
				
				printNode(node.getChildNodes());
				
			}
			
		}
	}
public static void main(String[] args) {
	try {
		File xmlDocument = new File("IncomeStatements.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlDocument);
		document.getDocumentElement().normalize();
		OutputStream fileOut = new FileOutputStream("IncomeStatements.xls");
		
		
		printNode(document.getChildNodes());
		
		//headers
		
		
		
		wb.write(fileOut);

	} catch (ParserConfigurationException | SAXException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

}
}
