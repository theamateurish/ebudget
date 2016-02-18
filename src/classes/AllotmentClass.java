/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author theamateurish
 */
public class AllotmentClass {
    
    public  Object[][]  allot_class(String fppcode) {        
        connect.DBConnection dbc = null;
        PreparedStatement preparedStatement;   
        Object[][] allot_class=null;
        
        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement("SELECT * FROM buds.allotment_class('"+fppcode +"')");
                ResultSet rs = preparedStatement.executeQuery();
                int ctrow = 0;
                while(rs.next()){
                    ctrow=rs.getRow();
                }
                rs.close();
                
                allot_class = new Object[ctrow][4];
        
                int row = 0;
                ResultSet rs1 = preparedStatement.executeQuery();
                while (rs1.next()) {
                    allot_class[row][0] = rs1.getString("titles");
                    allot_class[row][1] = rs1.getString("coding");
                    allot_class[row][2] = new java.text.DecimalFormat("#,##0.00").format(rs1.getDouble("amount"));
                    allot_class[row][3] = (rs1.getString("fpp_code")==null?"":rs1.getString("fpp_code"));
                    row++;
                }
                rs1.close();   
                preparedStatement.close();
                                                                                    
        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("AllotmentClass").log(java.util.logging.Level.SEVERE, null, ex);
                }
        } 
        return allot_class;
    }
    
}
