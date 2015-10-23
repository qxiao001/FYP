import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.Timestamp;
import java.util.Date;



public class TaiwanWeather {

    public static void main(String[] args) {

       
        
      //  saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0001-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0001.xml");
      //  saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0002-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0002.xml");
        Parser parser=new Parser();
        HashMap tempMap=parser.parseTemp("src/main/resources/A0001.xml");  
        HashMap rainMap=parser.parseRain("src/main/resources/A0002.xml"); 
        writehashMapToDB(true,tempMap);
        writehashMapToDB(false,rainMap);

    }
    
    public static void saveURLToFile(String URLString,String fileName){
    	URL URL=null;
		try {
			URL = new URL(URLString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			FileUtils.copyURLToFile(URL, new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("file save successfully!" + fileName);
    	
    }
    public static void writehashMapToDB(Boolean isTemp, HashMap hashMap){
    	
    	
        Connection con = null;

        Statement st = null;
        ResultSet rs = null;


    
        String url = "jdbc:postgresql://132.147.88.190:5432/ChillerProject";
        String user = "ecoadm";
        String password = "ev093qer";
        
        try {
        	
        	/* connect to remote server database*/
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();

            String query= "SELECT VERSION()";
            rs = st.executeQuery(query);
            if (rs.next()) {
            	System.out.println("local Connection Established: going to insert");
                System.out.println(rs.getString(1));
            }
  
            
         //   Date date= new Date();
  	     //   Timestamp timestamp=new Timestamp(date.getTime());
            
            Iterator it = hashMap.entrySet().iterator();
    	    while (it.hasNext()){
    	      HashMap.Entry pair = (HashMap.Entry)it.next();
    	      String id = (String) pair.getKey();
    	      String time = (String)((ArrayList)pair.getValue()).get(0);  //get time stamp string
    	      String value = (String)((ArrayList)pair.getValue()).get(1);  //get value string
    	      System.out.println(pair.getKey() + " : " + time + value); 
    	      if (isTemp){
    	      query= "insert into \"Taiwan_Temp\" values(\'"+time+"\',\'"+id+"\',\'"+value+"\')";}
    	      else {
    	    	  query= "insert into \"Taiwan_Rain\" values(\'"+time+"\',\'"+id+"\',\'"+value+"\')";
    	      }
    	    	  
              st.executeUpdate(query);
    	    }
           
           
          
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(TaiwanWeather.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
                
      

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(TaiwanWeather.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
   
}