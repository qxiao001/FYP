/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCExample {

    public static void main(String[] args) {

        Connection con = null;
        Connection localcon = null;
        Statement st = null;
        ResultSet rs = null;
        Statement localst = null;
        ResultSet localrs = null;

    
        String url = "jdbc:postgresql://132.147.88.190:5432/ChillerProject";
        String user = "ecoadm";
        String password = "ev093qer";
        
        String localurl = "jdbc:postgresql://localhost:5432/postgres";
        String localuser = "postgres";
        String localpassword = "123456";
        

        try {
        	
        	 connect to remote server database
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            
             Local connection with database  
            localcon=DriverManager.getConnection(localurl, localuser, localpassword);
            localst = localcon.createStatement();
            
            String query= "SELECT \"Chiller#4_CHWR_Temp\".\"tube_temp\",\"Chiller#4_CHWS_Temp\".\"tube_temp\",\"Chiller#4_CHWR_Temp\".\"time_stamp\",\"Chiller#4_CHWS_Temp\".\"time_stamp\",(\"Chiller#4_CHWR_Temp\".\"time_stamp\" - \"Chiller#4_CHWS_Temp\".\"time_stamp\") AS \"time_diff\" FROM \"Chiller#4_CHWR_Temp\" , \"Chiller#4_CHWS_Temp\" WHERE  ((\"Chiller#4_CHWR_Temp\".\"time_stamp\" - \"Chiller#4_CHWS_Temp\".\"time_stamp\") < INTERVAL \'30 seconds\' AND ((\"Chiller#4_CHWS_Temp\".\"time_stamp\" - \"Chiller#4_CHWR_Temp\".\"time_stamp\") < INTERVAL \'30 seconds\')) LIMIT 10;";
            rs = st.executeQuery(query);
            
            localrs = localst.executeQuery("SELECT VERSION()");
            
            if (localrs.next()) {
            	System.out.println("local Connection Established: table going to be truncated");
                System.out.println(localrs.getString(1));
            }
            localst.executeUpdate("truncate \"MeanTubeTemp\";");
            
            while (rs.next()) {
            	System.out.println("remote connection Established:");
                System.out.println(rs.getString(1));
                Double tubein=rs.getDouble(2);
                Double tubeout=rs.getDouble(1);
                Double mean=(tubein+tubeout)/2;
                Timestamp ts=rs.getTimestamp(3);
                query="INSERT INTO \"MeanTubeTemp\" VALUES ("+tubein+" , "+tubeout+" , "+ mean+" , \'"+ts+"\');";
                localst.executeUpdate(query);
                
            }
            
          
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JDBCExample.class.getName());
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
                
                if (localrs != null) {
                    localrs.close();
                }
                if (localst != null) {
                    localst.close();
                }
                if (localcon != null) {
                    localcon.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(JDBCExample.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}*/