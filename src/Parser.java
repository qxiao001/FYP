// for DOM Parser
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
//for hashmap
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class Parser {
	
	public Parser(){
		
	}

	public HashMap parseTemp(String fileString){
		
		
		HashMap<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		//tempMap.put("time", )
		
		
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
	        
	           // get stationid
	           NodeList stationids =((Element)nlocation).getElementsByTagName("stationId");
	           Element stationElement = (Element) stationids.item(1);  
	           String stationid=stationElement.getTextContent();
	           
	           // get obstime
	           NodeList times=((Element)nlocation).getElementsByTagName("time");
	           Element timeElement = (Element) times.item(1);  
	           String time=timeElement.getElementsByTagName("obsTime").item(0).getTextContent();
	           
	           // get city id , to check if it is Taichung
	           NodeList parameters=((Element)nlocation).getElementsByTagName("parameter");
	           Element cityElement = (Element) parameters.item(1);  
	           String cityParameterName=cityElement.getElementsByTagName("parameterName").item(0).getTextContent();
	           String cityParameterValue=cityElement.getElementsByTagName("parameterValue").item(0).getTextContent();
	      
	            
	           // if is Taizhong SN=02,
	           if (cityParameterValue.equals("02")){
	        	   Element townElement = (Element) parameters.item(3);
	        	   String townParameterName=townElement.getElementsByTagName("parameterName").item(0).getTextContent();
		           String townParameterValue=townElement.getElementsByTagName("parameterValue").item(0).getTextContent();
		       
		           
		           NodeList weathers=((Element)nlocation).getElementsByTagName("weatherElement");
		          
		           for (int j = 0; j < nList.getLength(); j++) {
		        	//   System.out.println(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent());
		        	   if(((Element)weathers.item(j)).getElementsByTagName("elementName").item(0).getTextContent().equals("TEMP")){
		        		   Node value =((Element)weathers.item(j)).getElementsByTagName("elementValue").item(0);
		        		   Double tempc=Double.parseDouble(((Element)value).getElementsByTagName("value").item(0).getTextContent());
		        	       ArrayList<String> values = new ArrayList<String> ();
		        	       values.add(time);
		        	       values.add(tempc.toString());
		        		   tempMap.put(stationid, values);
		        		   break;
		        	   }
		        	 
		           }
		           
	           }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		Iterator it = tempMap.entrySet().iterator();
	    while (it.hasNext()){
	      HashMap.Entry pair = (HashMap.Entry)it.next();
	      String id = (String) pair.getKey();
	      String time = (String)((ArrayList)pair.getValue()).get(0);  //get time stamp string
	      String value = (String)((ArrayList)pair.getValue()).get(1);  //get value string
	      System.out.println(pair.getKey() + " : " + time + value);      
	      
	    }
	    return tempMap;

	}
	
public HashMap parseRain(String fileString){
		
		
		HashMap tempMap = new HashMap();
		
		
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
		        	       tempMap.put(townParameterValue, tempc);
		        		   break;
		        	   }
		        	 
		           }
		           
	           }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		Iterator it = tempMap.entrySet().iterator();
	    while (it.hasNext()){
	      HashMap.Entry pair = (HashMap.Entry)it.next();
	      String id = (String) pair.getKey();
	      String time = (String)((ArrayList)pair.getValue()).get(0);  //get time stamp string
	      String value = (String)((ArrayList)pair.getValue()).get(1);  //get value string
	      System.out.println(pair.getKey() + " : " + time + value);      
	     
	    }
	    return tempMap;

	}

}
