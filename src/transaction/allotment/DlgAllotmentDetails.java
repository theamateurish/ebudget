/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.allotment;

/**
 *
 * @author theamateurish
 */
public class DlgAllotmentDetails extends javax.swing.JDialog {

    /**
     * Creates new form DlgAllotmentDetails
     */
    public DlgAllotmentDetails(java.awt.Frame parent, boolean modal, String fppcode, Integer clas, String obj, Integer legal, Integer colIndx) {
        super(parent, modal);
        initComponents();
        load(fppcode,clas,legal,obj,colIndx);
    }
    
    private void load(String fpp_code,Integer class_code, Integer legal_base, String obj, Integer colIndx){
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement = null;
        String selectSQL="";
        selectSQL="SELECT t1.legal_base,t1.fpp_code,t1.obj_expend_code,t1.class_code,t2.titles,t1.amount, "
                        +"t3.retention,t3.bal_approp,t4.amnt_alloted,t4.date_prepared, "
                        +"t4.run_bal,t4.control_id,t4.remark FROM temp.prep_appropriation t1 " 
                        +"LEFT JOIN acct.accounts t2 ON t1.obj_expend_code=t2.coding "
                        +"LEFT JOIN buds.allot_retention t3 ON t1.legal_base=t3.legal_base AND t1.fpp_code=t3.fpp_code AND t1.obj_expend_code=t3.obj_expend_code "
                        +"LEFT JOIN temp.prep_allotment t4 ON t1.legal_base=t4.legal_base AND t1.fpp_code=t4.fpp_code AND t1.obj_expend_code=t4.obj_expend_code "
                        +"WHERE t1.fpp_code=? "
                        +"AND t1.class_code=? "
                        +"AND t1.legal_base=? "
                        +"AND t1.obj_expend_code=? "
                        +"AND t4.amnt_alloted>0 ";
        switch (colIndx){
            case 3:selectSQL=selectSQL +"AND (to_char(t4.date_prepared,'MM')::smallint=1 OR to_char(t4.date_prepared,'MM')::smallint=2 OR to_char(t4.date_prepared,'MM')::smallint=3)";break;         
            case 4:selectSQL=selectSQL +"AND (to_char(t4.date_prepared,'MM')::smallint=4 OR to_char(t4.date_prepared,'MM')::smallint=5 OR to_char(t4.date_prepared,'MM')::smallint=6)";break;         
            case 5:selectSQL=selectSQL +"AND (to_char(t4.date_prepared,'MM')::smallint=7 OR to_char(t4.date_prepared,'MM')::smallint=8 OR to_char(t4.date_prepared,'MM')::smallint=9)";break;         
            case 6:selectSQL=selectSQL +"AND (to_char(t4.date_prepared,'MM')::smallint=10 OR to_char(t4.date_prepared,'MM')::smallint=11 OR to_char(t4.date_prepared,'MM')::smallint=12)";break;    
        }
                   selectSQL=selectSQL +" ORDER BY t4.control_id";
        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement(selectSQL);
                preparedStatement.setString(1, fpp_code);
                preparedStatement.setInt(2, class_code);                
                preparedStatement.setInt(3, legal_base);
                preparedStatement.setString(4, obj);
                java.sql.ResultSet rs = preparedStatement.executeQuery();
                classes.utils.clearTable(tblDetail);
                int R=0;
                while(rs.next()) {                                       
                    if (R >=tblDetail.getRowCount()){
                                    ((javax.swing.table.DefaultTableModel)tblDetail.getModel()).addRow(new Object[] {null, null, null, null, null});
                    } 
                    tblDetail.setValueAt(rs.getDouble("amnt_alloted"), R, 0);
                    tblDetail.setValueAt(new java.text.SimpleDateFormat("M/d/yyyy h:mm:ss a").format(rs.getTimestamp("date_prepared")), R, 1);
                    tblDetail.setValueAt(rs.getString("control_id"), R, 2);  
                    tblDetail.setValueAt("", R, 3);
                    R++;
                }

                preparedStatement.close();
                rs.close();                        

        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("DlgAllotmentDetails").log(java.util.logging.Level.SEVERE, null, e);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("DlgAllotmentDetails").log(java.util.logging.Level.SEVERE, null, ex);
                }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Allotment", "Date", "Advice Allotment No.", "Inputtted By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetail.getColumnModel().getColumn(0).setCellRenderer(
            new DecimalFormatRenderer() );
        jScrollPane1.setViewportView(tblDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDetail;
    // End of variables declaration//GEN-END:variables

     static class DecimalFormatRenderer extends javax.swing.table.DefaultTableCellRenderer {
      private static final java.text.DecimalFormat formatter = new java.text.DecimalFormat( "#,##0.00" );
 
      public java.awt.Component getTableCellRendererComponent(
         javax.swing.JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column) {
 
         // First format the cell value as required
 
         value = formatter.format((Number)(value==null?0:value));
         setHorizontalAlignment(RIGHT);
            // And pass it on to parent class
 
         return super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column );
      }
   }
}
