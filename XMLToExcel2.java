package com.zensar.FetchingData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLToExcel2 {
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
			
		/*	NodeList nodelist=document.getDocumentElement().getChildNodes();
		//	System.out.println("The last "+nodelist.item(nodelist.getLength()-2));
			for(int i=0;i<nodelist.getLength();i++){
				Node elemNode = nodelist.item(i);

				//if (elemNode.hasChildNodes()) {
					NodeList nodes=elemNode.getChildNodes();
				for(int j=0;j<nodes.getLength();j++){
					Node elemNode1 = nodelist.item(j);
					System.out.println("Node "+elemNode1.getNodeValue());
				}*/
				
				
				
				
				//********************************
				System.out.println("Lets do it working    ");
				int flag=0;//setHeaders
				NodeList nodelist=document.getDocumentElement().getChildNodes();
				for(int i=0,m=0;i<nodelist.getLength();i++,m++){  
					Node node = nodelist.item(i);
					System.out.println("node name--"+node.getNodeName()+" node content"+node.getTextContent());
					if(node.hasChildNodes()){
						row=sheet.createRow(m--);
						System.out.println("row"+m);

						NodeList childNodeList=node.getChildNodes();
						 for(int k=0,j=1;k<childNodeList.getLength();k++)
						 {					
							
							 Node childNode=childNodeList.item(k);
							 if(childNode.getNodeType()==Node.ELEMENT_NODE){
							 System.out.println(childNode.getNodeName()+" --node val--"+childNode.getTextContent()+" ------cell number"+j);
								cell = row.createCell(j++);
							
								cell.setCellValue(childNode.getTextContent());
								
							 }
							 
							 
						 }
						
						

						
					
					}
				}
				
				System.out.println("done with data move on to headers");
				row=sheet.createRow(0);

				NodeList headList=document.getDocumentElement().getChildNodes();
				for(int i=0;i<=1;i++){
					Node head=headList.item(i);
					NodeList headChildNodeList=head.getChildNodes();
					System.out.println("Head  node=="+head.getNodeName());
					 for(int k=0,j=1;k<headChildNodeList.getLength();k++){
						 Node headchildNode=headChildNodeList.item(k);
						 if(headchildNode.getNodeType()==Node.ELEMENT_NODE){
								System.out.println("Head child node=="+headchildNode.getNodeName());
								cell=row.createCell(j++);
								cell.setCellValue(headchildNode.getNodeName());
								

						 }
					 }

					
				}
				  
				  System.out.println("final");
				wb.write(fileOut);
				  
				  
				/*  System.out.println(node+"length"+nodelist.getLength());
				   
				  x = xmlDoc.getElementsByTagName("book")[0];
				    xlen = x.childNodes.length;
				    y = x.firstChild;
				    txt = "";
				    for (i = 0; i < xlen; i++) {
				        if (y.nodeType == 1) {
				            txt += i + " " + y.nodeName + "<br>";
				        }
				        y = y.nextSibling;
				    }
				    
				    
				    
				    
				    
				    
				    
				    
				     if(flag==0 && childNode.getNodeType()==Node.ELEMENT_NODE){
										row=sheet.createRow(0);
										for(int head=0;head<childNodeList.getLength();head++){
											cell=row.createCell(head);
											cell.setCellValue(childNode.getNodeName());
											System.out.println("doing only once"+childNode.getNodeName());
										}
										 flag=1;

								 }
				    
				  */
				
				
				
				
				
			//}else{
				
			//}
			
			//printNodeList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printNodeList(NodeList nodeList) {
		try {

			for (int count = 0; count < nodeList.getLength(); count++) {
				Node elemNode = nodeList.item(count);
				if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
					// get node name and value

					if (elemNode.hasChildNodes()) {

						// recursive call if the node has child nodes
						// system.out.println("ooooooooooooooo"+cell);
						printNodeList(elemNode.getChildNodes());
					}
					//cell = row.createCell(count);
					//cell.setCellValue(elemNode.getNodeName());

					System.out.println("Node Name =" + elemNode.getNodeName() + " [CLOSE]");
				}
			}
			//wb.write(fileOut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) {

		File xmlDocument = new File("IncomeStatements.xml");

		XMLToExcel2 excel = new XMLToExcel2();
		excel.generateExcel(xmlDocument);
	}
}
