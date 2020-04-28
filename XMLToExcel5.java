package com.zensar.FetchingData;

import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
public class XMLToExcel5 {
	
	
public void generateExcel(File xmlDocument) {
	try {// Creating a Workbook
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlDocument);
		document.getDocumentElement().normalize();
		System.out.println("Root element: " + document.getDocumentElement().getNodeName());
		OutputStream fileOut = new FileOutputStream("IncomeStatements.xls");
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("spreadSheet");
		Row row = sheet.createRow(0);
		Cell cell;
		
		NodeList nodeList = document.getDocumentElement().getChildNodes();  
		extractTextChildren(document.getDocumentElement());
		// nodeList is not iterable, so we are using for loop  ll
		if(document.hasChildNodes()){
		printList(document)	;
		}
		
		 
		
	}catch(Exception e){
	
	}
}
public static String extractTextChildren(Element parentNode) {
    NodeList childNodes = parentNode.getChildNodes();
    String result = new String();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node node = childNodes.item(i);
      if (node.getNodeType() == Node.TEXT_NODE) {
        result += node.getNodeValue();
      }
    }
    return result;
  }

private static void printList(Node node1) {
	NodeList nodeList=node1.getChildNodes();
	for(int i=0;i<nodeList.getLength();i++){
		Node node=nodeList.item(i);
		if(node.hasChildNodes())
		System.out.println("hi   "+node.getNodeName()
		+"   node name   "+node.getNodeValue());
	}
	

	
	for (int itr = 0; itr < nodeList.getLength(); itr++)   
	{  
	Node node = nodeList.item(itr);  
	System.out.println("\nNode Name :" + node.getNodeName()+node.getNodeType());  
	if (node.getNodeType() == Node.ELEMENT_NODE )   
	{  
	Element eElement = (Element) node.getChildNodes();  
	System.out.println("Student info "+ eElement.getNodeValue()+"Node"+eElement.getNodeName());  
	}  
	}  
}



	public static void main(String[] argv) {

		File xmlDocument = new File("IncomeStatements.xml");

		XMLToExcel5 excel = new XMLToExcel5();
		excel.generateExcel(xmlDocument);
	}
}
