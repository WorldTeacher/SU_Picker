package logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

public class XMLParser {
	
	private static Document doc;
	
	public XMLParser(String xmlString) throws Exception
	{
	      XMLParser.doc = loadXMLFromString(xmlString);
	      XMLParser.doc.getDocumentElement().normalize();
	}
	
	private static Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();	    
	       
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	public String getNumberOfRecords()
	{	
		return XMLParser.doc.getElementsByTagName("zs:numberOfRecords").item(0).getTextContent();		
	}	


}

