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

		saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0001-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0001.xml");
		saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0002-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0002.xml");
		Parser parser = new Parser();
		HashMap tempMap = parser.parseTemp("src/main/resources/A0001.xml");
		HashMap rainMap = parser.parseTemp("src/main/resources/A0002.xml");
		writehashMapToDB(true, tempMap);
		writehashMapToDB(false, rainMap);

	}

	public static void saveURLToFile(String URLString, String fileName) {
		URL URL = null;
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


    
        String url = "jdbc:postgresql://132.147.88.190:5433/ems3";
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
            if (isTemp){
            Iterator it = hashMap.entrySet().iterator();
    	    while (it.hasNext()){
    	      HashMap.Entry pair = (HashMap.Entry)it.next();
    	      String id = (String) pair.getKey();
    	      String time = (String)((ArrayList)pair.getValue()).get(0);  //get time stamp string
    	      String elev = (String)((ArrayList)pair.getValue()).get(1);
    	      String wdir = (String)((ArrayList)pair.getValue()).get(2);
    	      String wdsd = (String)((ArrayList)pair.getValue()).get(3);
    	      String temp = (String)((ArrayList)pair.getValue()).get(4);
    	      String humd = (String)((ArrayList)pair.getValue()).get(5);
    	      String pres = (String)((ArrayList)pair.getValue()).get(6);
    	      String sun = (String)((ArrayList)pair.getValue()).get(7);
    	      String h24r = (String)((ArrayList)pair.getValue()).get(8);
    	      String ws15m = (String)((ArrayList)pair.getValue()).get(9);
    	      String wd15m = (String)((ArrayList)pair.getValue()).get(10);
    	      String ws15t = (String)((ArrayList)pair.getValue()).get(11);
    	    
    	      //System.out.println(pair.getKey() + " : " + time + value); 
    	      
    	      query= "insert into \"taiwan_temp\" values(\'"+time+"\',\'"+id+"\',\'"+elev+"\',\'"+wdir+"\',\'"+wdsd+"\',\'"+temp+"\',\'"+humd+"\',\'"+pres+"\',\'"+sun+"\',\'"+h24r+"\',\'"+ws15m+"\',\'"+wd15m+"\',\'"+ws15t+"\')";
    	      st.executeUpdate(query);  
    	    }
    	      
              
    	    }else 
    	    {
    	    	Iterator it = hashMap.entrySet().iterator();
        	    while (it.hasNext()){
        	      HashMap.Entry pair = (HashMap.Entry)it.next();
        	      String id = (String) pair.getKey();
        	      String time = (String)((ArrayList)pair.getValue()).get(0);  //get time stamp string
        	      String elev = (String)((ArrayList)pair.getValue()).get(1);
        	      String rain = (String)((ArrayList)pair.getValue()).get(2);
        	      String min10 = (String)((ArrayList)pair.getValue()).get(3);
        	      String hour3 = (String)((ArrayList)pair.getValue()).get(4);
        	      String hour6 = (String)((ArrayList)pair.getValue()).get(5);
        	      String hour12 = (String)((ArrayList)pair.getValue()).get(6);
        	      String hour24 = (String)((ArrayList)pair.getValue()).get(7);
        	      String now = (String)((ArrayList)pair.getValue()).get(8);
        	      
        	    
        	      //System.out.println(pair.getKey() + " : " + time + value); 
        	      
        	      query= "insert into \"taiwan_rain\" values(\'"+time+"\',\'"+id+"\',\'"+elev+"\',\'"+rain+"\',\'"+min10+"\',\'"+hour3+"\',\'"+hour6+"\',\'"+hour12+"\',\'"+hour24+"\',\'"+now+"\')";
        	      st.executeUpdate(query);
        	    }
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