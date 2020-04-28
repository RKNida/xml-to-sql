package com.zensar.FetchingData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlToTable {
private static	List finalNodelist=null;

	
	 public static void readChildNodes(Node node)
	    {

	        NodeList nl = node.getChildNodes();


	        for (int i = 0; i < nl.getLength(); i++) {
	            Node innernode = nl.item(i);
	            //System.out.println("mediam stage  node::"+innernode.getNodeName());
	            if (innernode.hasChildNodes()) {
	                System.out.println("inner node::"+innernode.getNodeName());

	                readChildNodes(innernode);
	            }
	            else{

	                System.out.println("node dont have childs node::"+innernode.getNodeName());
	                finalNodelist.add(innernode.getNodeName());
	               //// break;
	            }

	        }
	    }

	
	public static void printNode(NodeList nodeList) {
		NodeList nodeListMaintain;
		for(int i=0,j=0;i<nodeList.getLength();i++){

			Node node=nodeList.item(i);
			
			if (node.hasChildNodes()){
				System.out.println("\n"+node.getNodeName());
				String nodeName=node.getNodeName();
				if (node.getChildNodes().getLength()==1 ) {
					Node last=nodeList.item(nodeList.getLength()-2);
					String lastNodeName=last.getNodeName();
					if(nodeName.equals(lastNodeName)){
					//System.out.println("Before if"+node.getNodeName()+" length"+last.getNodeName());

					}
							
				
				System.out.println("-->"+node.getTextContent());
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
	//	OutputStream fileOut = new FileOutputStream("IncomeStatements.xls");
		try{
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Demo", "root", "abc123");
			String sql="create table IncomeStatements (srNo int(10));";
			Statement st=conn.createStatement();
			st.execute(sql);
			//String alterTable="ALTER TABLE IncomeStatements ADD column_name datatype;"; 
			
			
			}catch(Exception e){
				System.out.print("jdbc connnection");
				
				e.printStackTrace();
				
			}
		/*NodeList
		for(int i=0;i<document.getChildNodes().getLength();i++){
			NodeList childList=document.getChildNodes().item(i);
			System.out.println(node.getNodeName());
			if(node.hasChildNodes()){
			//	String sql="create table "+node.getNodeName();
				
			}
		}*/
	

		
		
		
		
		 Node company = document.getFirstChild();
         System.out.println(company.hasChildNodes());
         NodeList nl = company.getChildNodes();
         for (int i = 0; i < nl.getLength(); i++) {
             Node node = nl.item(i);
             //System.out.println("inner node::"+node.getNodeName());

             if (node.hasChildNodes()) {
                 System.out.println("outer node::" + node.getNodeName());
                 readChildNodes(node);
             } else {

             }
         }

		
		
		
		
		
		
		
		
		
		
		//printNode(document.getChildNodes());
		
		//headers
		
		
		
	///	wb.write(fileOut);

	} catch ( Exception  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();

	}
}
}
