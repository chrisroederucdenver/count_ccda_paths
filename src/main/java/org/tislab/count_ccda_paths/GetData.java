package org.tislab.count_ccda_paths;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.Instant;
import java.time.Duration;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;

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




public class GetData {
	
	//static final String paths_filename = "src/main/resources/PersonPaths.txt";
	
	static final String OUTPUT_ROOT = ".";
	BufferedReader pathsReader=null;
	private XPath xPath=null;
	private Document ccdaDocument=null;
    private static final List<String> file_list = Arrays.asList(
            "170.314b2_AmbulatoryToC.xml",
            "CCDA_CCD_b1_Ambulatory_v2.xml",
            "CCDA_CCD_b1_InPatient_v2.xml",
            "Inpatient_Encounter_Discharged_to_Rehab_Location(C-CDA2.1).xml",
            "ToC_CCDA_CCD_CompGuideSample_FullXML.xml");
    
    private static final List<String> tables_list = Arrays.asList(
    		"PersonPathsOMOP.txt",
    		"ProviderPathsOMOP.txt",
    		"LocationPathsOMOP.txt",
    		"CareSitePathsOMOP.txt"
    		);
	
	
	public static void main(String[] args) { 
	    Instant start = Instant.now();
	    
	    PrintWriter out_writer = null;
		for (String ccda_filepath : file_list) {
			try {
				GetData gd = new GetData(ccda_filepath);
				
				for (String table_filename : tables_list) {
					String output_filepath = OUTPUT_ROOT + "/" 
					    + ccda_filepath.substring(0, ccda_filepath.length() - 4)
					    + "_"
					    + table_filename.substring(0, table_filename.length() -4)
					    + ".csv"; 									
					out_writer = new PrintWriter(new FileWriter(output_filepath));
					gd.processPathList(table_filename, out_writer);							
				}
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
			out_writer.close();
		}
        Instant end = Instant.now();
        Duration d = Duration.between(start, end);
        System.out.println("duration:" + d.getSeconds() + ", " + d.getNano()/1000000);
				
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
	
	public void processPathList(String paths_filename, PrintWriter out_writer) 
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
				
				String xPath_string = pathsReader.readLine();
				System.out.print("xPath expression: \"" + xPath_string + "\"");				
				if (!xPath_string.equals("") && xPath_string.charAt(0) != '#') {
					XPathExpression expr = xPath.compile(xPath_string);
					String value = expr.evaluate(ccdaDocument);
					if (!value.equals("")) {
						System.out.println("   value: \"" + value + "\"");					
						out_writer.print(value + ", ");		
					}
				}
			}
			out_writer.println();
		}
		catch (IOException x) {
				System.out.println("can't read " + x);
		}
	}
	

	
}

