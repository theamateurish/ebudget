/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author asus_k52
 */
public class utils {

    public static double RoundUp(double value, int decimal) {
        return new java.math.BigDecimal(value).divide(new java.math.BigDecimal(1D), decimal, java.math.RoundingMode.HALF_EVEN).doubleValue();
    }

    public static void clearTable(javax.swing.JTable table)
    {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int numrows = model.getRowCount();

        for(int i = numrows - 1; i >=0; i--)
        {
            model.removeRow(i);
        }
    }  
    
}
