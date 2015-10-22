// for DOM Parser
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

//for hashmap
import java.util.Enumeration;
import java.util.Hashtable;

public class Parser {
	
	public Parser(){
		
	}

	public Hashtable parseTemp(String fileString){
		
		
		Hashtable tempTable = new Hashtable();
		//tempTable.put("time", )
		
		
		try {
	         File inputFile = new File(fileString);
	         DocumentBuilderFactory dbFactory = 
	            DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	        // System.out.print("Root element: ");
	        // System.out.println(doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("location");
	         System.out.println("----------------------------");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	           System.out.println(temp+"node: ");
	           Node nlocation = nList.item(temp);
	        
	           NodeList parameters=((Element)nlocation).getElementsByTagName("parameter");
	           
	           
	           Element cityElement = (Element) parameters.item(1);  
	           String cityParameterName=cityElement.getElementsByTagName("parameterName").item(0).getTextContent();
	           String cityParameterValue=cityElement.getElementsByTagName("parameterValue").item(0).getTextContent();
	        //   System.out.println(cityParameterName+cityParameterValue);
	            
	           // if is Taizhong SN=02,
	           if (cityParameterValue.equals("02")){
	        	   Element townElement = (Element) parameters.item(3);
	        	   String townParameterName=townElement.getElementsByTagName("parameterName").item(0).getTextContent();
		           String townParameterValue=townElement.getElementsByTagName("parameterValue").item(0).getTextContent();
		       //    System.out.println(townParameterName+townParameterValue);
		           
		           NodeList weathers=((Element)nlocation).getElementsByTagName("weatherElement");
		          
		           for (int j = 0; j < nList.getLength(); j++) {
		        	//   System.out.println(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent());
		        	   if(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent().equals("TEMP")){
		        		   Node value =((Element)weathers.item(j)).getElementsByTagName("elementValue").item(0);
		        		   Double tempc=Double.parseDouble(((Element)value).getElementsByTagName("value").item(0).getTextContent());
		        	       tempTable.put(townParameterValue, tempc);
		        		   break;
		        	   }
		        	 
		           }
		           
	           }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		Enumeration e = tempTable.keys();
	    while (e.hasMoreElements()) {
	      String key = (String) e.nextElement();
	      System.out.println(key + " : " + tempTable.get(key));
	    }
	    return tempTable;

	}
public Hashtable parseRain(String fileString){
		
		
		Hashtable tempTable = new Hashtable();
		
		
		try {
	         File inputFile = new File(fileString);
	         DocumentBuilderFactory dbFactory = 
	            DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	        // System.out.print("Root element: ");
	        // System.out.println(doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("location");
	         System.out.println("----------------------------");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	           System.out.println(temp+"node: ");
	           Node nlocation = nList.item(temp);
	        
	           NodeList parameters=((Element)nlocation).getElementsByTagName("parameter");
	           
	           
	           Element cityElement = (Element) parameters.item(1);  
	           String cityParameterName=cityElement.getElementsByTagName("parameterName").item(0).getTextContent();
	           String cityParameterValue=cityElement.getElementsByTagName("parameterValue").item(0).getTextContent();
	         //  System.out.println(cityParameterName+cityParameterValue);
	            
	           // if is Taizhong SN=02,
	           if (cityParameterValue.equals("02")){
	        	   Element townElement = (Element) parameters.item(3);
	        	   String townParameterName=townElement.getElementsByTagName("parameterName").item(0).getTextContent();
		           String townParameterValue=townElement.getElementsByTagName("parameterValue").item(0).getTextContent();
		          // System.out.println(townParameterName+townParameterValue);
		           
		           NodeList weathers=((Element)nlocation).getElementsByTagName("weatherElement");
		          
		           for (int j = 0; j < nList.getLength(); j++) {
		        //	   System.out.println(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent());
		        	   if(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent().equals("NOW")){
		        		   Node value =((Element)weathers.item(j)).getElementsByTagName("elementValue").item(0);
		        		   Double tempc=Double.parseDouble(((Element)value).getElementsByTagName("value").item(0).getTextContent());
		        	       tempTable.put(townParameterValue, tempc);
		        		   break;
		        	   }
		        	 
		           }
		           
	           }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		Enumeration e = tempTable.keys();
	    while (e.hasMoreElements()) {
	      String key = (String) e.nextElement();
	      System.out.println(key + " : " + tempTable.get(key));
	    }
	    return tempTable;

	}

}
