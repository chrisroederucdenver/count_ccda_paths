package org.tislab.count_ccda_paths;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.w3c.dom.*;
import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;




class GetData {
	
	//static final String paths_filename = "src/main/resources/PersonPaths.txt";
	static String paths_filename = "PersonPaths.txt";
	BufferedReader pathsReader=null;
	private XPath xPath=null;
	private Document ccdaDocument=null;
    private static final List<String> file_list = Arrays.asList(
            "170.314b2_AmbulatoryToC.xml",
            "CCDA_CCD_b1_Ambulatory_v2.xml",
            "CCDA_CCD_b1_InPatient_v2.xml",
            "Inpatient_Encounter_Discharged_to_Rehab_Location(C-CDA2.1).xml",
            "ToC_CCDA_CCD_CompGuideSample_FullXML.xml");
	
	
	public static void main(String[] args) {
		for (String ccda_filepath : file_list) {
			System.out.println("GET DATA: " + ccda_filepath);
			
			try {
				GetData gd = new GetData(ccda_filepath);
				//gd.processPathList();
				
				paths_filename="ProviderPaths.txt";
				gd.processPathList();
			}
			catch (ParserConfigurationException pce) {
				System.err.println("parser not correctly configured." + pce);
				System.err.println(pce.toString());			
			}
			catch (IOException ioe) {
				System.err.println("cant' read the CCDA file: " + ccda_filepath);
				System.err.println(ioe.toString());
			}
			catch (SAXException se) {
				System.err.println("can't parse the CCDA file: " + ccda_filepath);
				System.err.println(se.toString());			
			} catch (XPathExpressionException e) {
				System.err.println("can't use expression in the xPath the CCDA file: ");			
				e.printStackTrace();
			}
		}
				
	}
	
	public GetData(String ccdaFilePath) 
	throws ParserConfigurationException, IOException, SAXException {
		InputStream ccdaStream = getStreamFromClasspath(ccdaFilePath);
		// https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html#newDocumentBuilder--
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();		
		ccdaDocument = docBuilder.parse(ccdaStream);
		
		xPath = XPathFactory.newInstance().newXPath();
	}
	
	public void showStreamContents(InputStream is) {
		System.out.println("---> showStreamContent ");
		try {
			BufferedReader contentsReader = new BufferedReader(new InputStreamReader(is));
			while (contentsReader.ready()) {		
				System.out.println(contentsReader.readLine());
			}
		}
		catch (Exception x) {
			System.err.println("can't show contents??");
		}
		System.out.println("showStreamContent <--- ");		
	}
	
	private InputStream getStreamFromClasspath(String filename) 
	throws IOException {
		Class clazz = GetData.class;
		InputStream is = clazz.getClassLoader().getResourceAsStream(filename);
		return is;
	}
	
	public void processPathList()
	throws XPathExpressionException {
		
		BufferedReader pathsReader = null;
		try {
			pathsReader = new BufferedReader(new InputStreamReader(getStreamFromClasspath(paths_filename)));
			System.out.println("using pathList file:" + paths_filename);					
		}
		catch(IOException x) {
			System.err.println("couldn't read the pathlist file:" + x);
			return;
		}

		try {
			while (pathsReader.ready()) {
				String s = pathsReader.readLine();
				if (!s.equals("") && s.charAt(0) != '#') {
					processXPath(s);
				}
			}
		}
		catch (IOException x) {
				System.out.println("can't read " + x);
		}
	}
	
	public void processXPath(String xPath_expression_string) 
	throws XPathExpressionException{
		System.out.print("xPath expression: \"" + xPath_expression_string + "\"");
		XPathExpression expr = xPath.compile(xPath_expression_string);

		String nodeList = expr.evaluate(ccdaDocument); 
		System.out.println("   value: \"" + nodeList + "\"");
	}
	
	
}
