package org.tislab.count_paths;



import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;



public class CountPaths {

	public static void main(String[] args) {
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
	
	private static Map<String, Integer> leafCounts = new HashMap<String, Integer>();

	// TODO  #1: how to specify the hash creation function as a lambda to make it easy to swithc them around without using interfaces etc.
//	static Function(String a, Integer b) makeKey =  (String a) -> { return a.hashCode(); };
	
	// TODO #2 is what do you want to count? Just creating a hash of the entire path string will be too specific: everything will have a count of 1
	// If you want to get some measure of genericness, but count variables per person you'll need to be able to
	// access the different parts of the path.
	
	// at the risk of re-creating something from XPath (TODO: look htat up!)
	// here's a more detailed or abstract path element class. Stack these like the strings the code
	// below creates so you can pull apart the data chunks you want to build  key from, at the
	// level of detail you want to count.
	private static class PathElement {
		String elementName;
		Map<String, String> attributeMap = new HashMap<String, String>();
		
	}
	
	// The real hard part is deciding what values you want to include and which you don't
	// - people, encounters, dates, variable?

	
	
	enum Direction  { UP, DOWN };
	
	static class MyHandler extends DefaultHandler {
		Deque<String> pathStack = new ArrayDeque();
		Direction pathDirection = Direction.DOWN;
		
		String getPathString() {	
			StringBuilder pathString = new StringBuilder(); 
			for (String part : pathStack) {
				pathString.append(part);
				pathString.append("/");
			}
			return pathString.toString();
		}
		
		StringBuilder data = new StringBuilder();
			
		public void startDocument() 
				throws SAXException {
		}
		
		public void endDocument() 
				throws SAXException {
		}
		
		public void startElement(String uri, String localName, String qname, Attributes attributes)
				throws SAXException {
			data = new StringBuilder();
			pathDirection = Direction.DOWN;
			StringBuilder sb = new StringBuilder();
			sb.append(qname);	
			if (attributes.getLength() > 0) {
				sb.append("[");
				for (int i=0; i<attributes.getLength(); i++) {
					sb.append("@");
					sb.append(attributes.getLocalName(i));
					sb.append("='");
					sb.append(attributes.getValue(i)); 
					sb.append("'");				
					if (attributes.getLength() > (i + 1)) { sb.append(" & "); }
				}
				sb.append("]");				
			}

			
			pathStack.addLast(sb.toString());
		}
		
		public void endElement(String uri, String localName, String qname)
				throws SAXException {

			if (pathDirection == Direction.DOWN ) {
				if (data.length() > 0) { 
					System.out.println(getPathString() + "\"" + data + "\"");
				} else {
					System.out.println(getPathString());
				}
			}
			pathDirection = Direction.UP;				
			pathStack.removeLast();
			data = new StringBuilder();			
		}
		
		public void characters(char ch[], int start, int length)
		throws SAXException {
			String charData = new String(ch, start, length);		
			data.append(charData.strip());
		}
	}
}


