/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import classes.MsgBox;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author theamateurish
 */
public class SystemOptions {
    
    public String getUniqueId(){
        String identifier="";
        long timeMilli = ServerDate().getTime();
        String strTimeMilli = String.valueOf(timeMilli);
        try { 
		java.net.InetAddress ip = java.net.InetAddress.getLocalHost();                
		java.net.NetworkInterface network = java.net.NetworkInterface.getByInetAddress(ip);                    
		byte[] mac = network.getHardwareAddress();  
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		identifier = ip.getHostAddress().toString().replaceAll("\\s", "").replace(".", "") + sb.toString().replaceAll("\\s", "").replace("-", "") + strTimeMilli;
 
	} catch (UnknownHostException e) { 
		e.printStackTrace(); 
	} catch (SocketException e){ 
		e.printStackTrace(); 
	}
        return identifier;
    }
    
    public java.util.Date ServerDate(){
        connect.DBConnection dbc = null;
        java.sql.ResultSet rs=null;
        java.util.Date systemDate=null;
        try{
            dbc = new connect.DBConnection();
            rs=dbc.executeQuery("SELECT now() AS SERVERDATE");   
            if(rs.next()){
                systemDate=rs.getTimestamp("SERVERDATE");
            }
            rs.close();
        }catch (Exception ex) {
            MsgBox.showWarning(null, ex.getMessage(), "Server Date and Time");
            Logger.getLogger(SystemOptions.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(dbc!=null){
                try {
                    dbc.close();                    
                } catch (SQLException ex) {
                    MsgBox.showWarning(null, ex.getMessage(), "Server Date and Time");
                    Logger.getLogger(SystemOptions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return systemDate;
    }
}
