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

import java.util.Enumeration;
import java.util.Hashtable;

import java.sql.Timestamp;
import java.util.Date;



public class TaiwanWeather {

    public static void main(String[] args) {

       
        
        saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0001-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0001.xml");
        saveURLToFile("http://opendata.cwb.gov.tw/member/opendataapi?dataid=O-A0002-001&authorizationkey=CWB-9488A5D7-3B34-449B-81E9-51836A5A6CA6","src/main/resources/A0002.xml");
        Parser parser=new Parser();
        Hashtable tempTable=parser.parseTemp("src/main/resources/A0001.xml");  
        Hashtable rainTable=parser.parseRain("src/main/resources/A0002.xml"); 
        writeHashTableToDB(tempTable);

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
    public static void writeHashTableToDB(Hashtable hashTable){
    	
    	
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
            
            Enumeration e = hashTable.keys();
            Date date= new Date();
  	        Timestamp timestamp=new Timestamp(date.getTime());
    	    while (e.hasMoreElements()) {
    	      String key = (String) e.nextElement();
    	      System.out.println(key + " : " + hashTable.get(key));      
    	      query= "insert into \"Taiwan_Temp\" values(\'"+timestamp.toString()+"\',\'"+hashTable.get(key)+"\')";
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