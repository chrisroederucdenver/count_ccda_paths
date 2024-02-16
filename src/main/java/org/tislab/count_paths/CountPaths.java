package org.tislab.count_paths;



import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;



public class CountPaths {

	public static final boolean show_tree=false; 
	
	public static void main(String[] args) {
		System.out.println("starting...");
	    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		    try {
		        SAXParser saxParser = saxParserFactory.newSAXParser();
		        MyHandler handler = new MyHandler();
		        //saxParser.parse(new File("src/main/resources/Inpatient_Encounter_Discharged_to_Rehab_Location(C-CDA2.1).xml"), handler);
		        saxParser.parse(new File("src/main/resources/test_2.xml"), handler);		        
		    } catch (ParserConfigurationException | SAXException | IOException e) {
		        e.printStackTrace();
		    }
		    System.out.println("....that's all folks!");		    
	}
	
	enum Direction  { UP, DOWN };
	
	static class MyHandler extends DefaultHandler {
		Deque<String> pathStack = new ArrayDeque();
		Direction pathDirection = Direction.DOWN;
		
		String getPathString() {	
			StringBuilder pathString = new StringBuilder(); 
			for (String part : pathStack) {
				pathString.append(part);
				pathString.append(" / ");
			}
			return pathString.toString();
		}
		String indent() {
			StringBuilder sb = new StringBuilder();
			for (int i=1; i<nest_level; i++) {
				sb.append("    ");
			}
			return sb.toString();
		}
		
		StringBuilder data = new StringBuilder();
		int nest_level =0;
			
		public void startDocument() 
				throws SAXException {
		}
		
		public void endDocument() 
				throws SAXException {
		}
		
		public void startElement(String uri, String localName, String qname, Attributes attributes)
				throws SAXException {
			data = new StringBuilder();
			nest_level++;
			pathDirection = Direction.DOWN;
			//System.out.println(indent(nest_level) + " Start: uri:" + uri + " localName:" + localName + " qname:" + qname);
			if (show_tree) { System.out.println(indent() + qname); }
			for (int i=0; i<attributes.getLength(); i++) {
				System.out.println(attributes.getLocalName(i) + ":" + attributes.getValue(i) + "");				
			}
			pathStack.addLast(qname);
		}
		
		public void endElement(String uri, String localName, String qname)
				throws SAXException {
			//System.out.println(indent(nest_level) + " End: uri:" + uri + " localName:" + localName + " qname:" + qname);
			if (qname != "text" && data.length() > 0) {
				if (show_tree) { System.out.println(indent() + "    " + data.toString()); 
				}
			}
			if (show_tree) { System.out.println(indent() + "/" + qname); }						
			nest_level--;	
		//pathStack.addLast("(" + qname + ")");
//			System.out.println("XXX: " + qname);
			data = new StringBuilder();
			if (pathDirection == Direction.DOWN) { 
				System.out.println(getPathString());
			}
			pathDirection = Direction.UP;				
			pathStack.removeLast();
		}
		
		public void characters(char ch[], int start, int length)
		throws SAXException {
			data.append(new String(ch, start, length));
		}
	}
}


