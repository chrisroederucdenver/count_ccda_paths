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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;




class GetData {
	
	//static final String paths_filename = "src/main/resources/PersonPaths.txt";
	final String paths_filename = "PersonPaths.txt";
	BufferedReader pathsReader=null;
    private static final List<String> file_list = Arrays.asList(
            "test_2.xml",
            "170.314b2_AmbulatoryToC.xml",
            "CCDA_CCD_b1_Ambulatory_v2.xml",
            "CCDA_CCD_b1_InPatient_v2.xml",
            "Inpatient_Encounter_Discharged_to_Rehab_Location(C-CDA2.1).xml",
            "ToC_CCDA_CCD_CompGuideSample_FullXML.xml");
	
	
	public static void main(String[] args) {
		String ccda_filepath = file_list.get(5);
		System.out.println("GET DATA: " + ccda_filepath);
		
		try {
			GetData gd = new GetData(ccda_filepath);
//			gd.processPathList();
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
		}
				
	}
	
	public GetData(String ccdaFilePath) 
	throws ParserConfigurationException, IOException, SAXException {
		InputStream ccdaStream = getStreamFromClasspath(ccdaFilePath);
		
		// https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html#newDocumentBuilder--
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		
		// Schema schema = SchemaFactory.newSchema();   // W3C_XML_SCHEMA_INSTANCE_NS_URI // CDA.xsl
		//docBuilderFactory.setSchema()
		//docBuilderFactory.setValidating()
		//docBuilderFactory

		
		//showStreamContents(ccdaStream);
		
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();		
		docBuilder.parse(ccdaStream);
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
	
	public void processPathList() {
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
				processXPath(pathsReader.readLine());
			}
		}
		catch (IOException x) {
				System.out.println("can't read " + x);
		}
		
		
	}
	
	public void processXPath(String xPath) {

		
		
	}
	
	
}

