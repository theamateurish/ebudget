/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.allotment;

import classes.MsgBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author theamateurish
 */
public class FrmPrepAllotment extends javax.swing.JInternalFrame {
    private static final long serialVersionUID = 4676303089799270571L;
    private java.util.ArrayList <String>arrObjID=new java.util.ArrayList<>();
    private String[] header = { "FPP","PROGRAMS","SUB-OFFICE","OFFICE","LEVEL","CENTRE_UID"};
    
    private final boolean NUMERIC = true;    
    private org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode root;
    private org.jdesktop.swingx.treetable.DefaultTreeTableModel model;
    private Integer fundID;
    private String  fppcode,controlID;
    private Boolean editMode=false;
    private Boolean readOnly=false;
//    private Integer month;
    private Boolean canEdit=false;
    private Boolean initForm;
    /**
     * Creates new form iFrmPreparation
     */
    public FrmPrepAllotment() {
        initComponents();  
        initForm=true;
        chDatePrepAllotment.setSelectedDate(DateToCalendar(new connect.SystemOptions().ServerDate()));
//        month=Integer.valueOf(org.joda.time.format.DateTimeFormat.forPattern("M").print(new org.joda.time.LocalDate(new connect.SystemOptions().ServerDate())));
//        month=Integer.valueOf(org.joda.time.format.DateTimeFormat.forPattern("M").print(new org.joda.time.LocalDate(chDatePrepAllotment.getSelectedDate())));
        loadAppropriation();
    }       
         
    private void loadAppropriation(){
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement = null;
        String selectSQL="SELECT t1.fund_id,t1.legal_base,t1.control_id,t1.status,t1.date_prepared,"
                + "t2.description,t3.legal_desc,t3.legal_year FROM buds.appropriation_status t1 "
                + "INNER JOIN temp.prep_respcenter t2 "
                + "ON t1.legal_base=t2.legal_base AND t1.fund_id=t2.funds_id "
                + "INNER JOIN buds.ordinances t3 ON t1.legal_base=t3.legal_uid "
                + "WHERE resp_id=0 "
                + "AND resp_sub=0 "
                + "AND programs=0 "
                + "AND classes=0 "
                + "AND t3.legal_year=? "
                + "AND status='Approved' "
                + "ORDER BY t1.fund_id";               

        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement(selectSQL);
                preparedStatement.setInt(1, Integer.valueOf(org.joda.time.format.DateTimeFormat.forPattern("yyyy").print(new org.joda.time.LocalDate(chDatePrepAllotment.getSelectedDate()))));
                java.sql.ResultSet rs = preparedStatement.executeQuery();
                classes.utils.clearTable(tblRecords);
                int R=0;
                while(rs.next()) {                                       
                    if (R >=tblRecords.getRowCount()){
                                    ((javax.swing.table.DefaultTableModel)tblRecords.getModel()).addRow(new Object[] {null, null, null, null, null});
                    } 
                    tblRecords.setValueAt(rs.getString("control_id"), R, 0);
                    tblRecords.setValueAt(rs.getInt("legal_year"), R, 1);
                    tblRecords.setValueAt(rs.getString("legal_desc"), R, 2);  
                    tblRecords.setValueAt(rs.getString("description"), R, 3);
                    tblRecords.setValueAt(rs.getString("status"), R, 4);
                    tblRecords.setValueAt(rs.getInt("legal_base"), R, 5);  
                    tblRecords.setValueAt(rs.getInt("fund_id"), R, 6);
                    R++;
                }

                preparedStatement.close();
                rs.close();                        

        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, e);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
   
    
    private void total_FPP(Integer legal,String fund){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR)); 
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT sum(total_fund) AS total_fpp FROM(SELECT legal_base,split_part(fpp_code, '-',1) AS fpp_code,sum(amount) AS total_fund "
                                    +"FROM temp.prep_appropriation "
                                    +"GROUP BY split_part(fpp_code, '-',1),legal_base "
                                    +"ORDER BY fpp_code) AS appropriation WHERE fpp_code=? "
                                    +"AND legal_base=?";

		try {
			dbc = new connect.DBConnection();
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setString(1, fund);
                        preparedStatement.setInt(2,legal);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
                            lblTotalFPPSum.setText("₱ " + String.valueOf(new java.text.DecimalFormat("#,##0.00").format(rs.getDouble("total_fpp"))));
			}

                        preparedStatement.close();
                        rs.close();                        
                        
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
		} finally {
			if (dbc != null) try {
                            dbc.close();
                        } catch (java.sql.SQLException ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void allot_retention(String fppcode){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                String selectSQL="SELECT * FROM temp.prep_appropriation t1 "
                                        +"INNER JOIN acct.accounts t2 ON t1.obj_expend_code=t2.coding "
                                        +"LEFT JOIN buds.allot_retention t3 ON t1.obj_expend_code=t3.obj_expend_code "
                                            +"AND t1.legal_base=t3.legal_base "
					    +"AND t1.fpp_code=t3.fpp_code "
                                        +"WHERE t1.fpp_code=?"
                                        +" AND class_code=?"
                                        +" AND t1.legal_base=?"
                                        +" ORDER BY coding";               

		try {
			dbc = new connect.DBConnection();
                        String[]code=fppcode.split("-"); 
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setString(1, code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                        preparedStatement.setInt(2, Integer.valueOf(code[5].toString()));
                        preparedStatement.setInt(3, Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        classes.utils.clearTable(tblDetailsReten);
                        int R=0;
			while(rs.next()) {                                       
                            if (R >=tblDetailsReten.getRowCount()){
                                            ((javax.swing.table.DefaultTableModel)tblDetailsReten.getModel()).addRow(new Object[] {null, null, null, null, null});
                            }               
                            tblDetailsReten.setValueAt(rs.getString("titles"), R, 0);
                            tblDetailsReten.setValueAt(rs.getString("coding"), R, 1);  
                            tblDetailsReten.setValueAt(rs.getDouble("amount"), R, 2);
                            tblDetailsReten.setValueAt(rs.getDouble("retention"), R, 3);
                            tblDetailsReten.setValueAt((rs.getDouble("retention")>0?rs.getDouble("bal_approp"):rs.getDouble("amount")), R, 4);
                            R++;
			}

                        preparedStatement.close();
                        rs.close();                        
                        
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
		} finally {
			if (dbc != null) try {
                            dbc.close();
                        } catch (java.sql.SQLException ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void allotment(String fppcode){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                String[]code=fppcode.split("-");
                String selectSQL,tblTemp="";
                tblTemp="SELECT t1.legal_base,t1.fpp_code,t1.obj_expend_code,t1.class_code,t2.titles,t1.amount,"
                        + "t3.retention,t3.bal_approp,t4.amnt_alloted,t4.run_bal,t4.control_id,t4.remark FROM temp.prep_appropriation t1 "
                        + "LEFT JOIN acct.accounts t2 ON t1.obj_expend_code=t2.coding "
                        + "LEFT JOIN buds.allot_retention t3 ON t1.legal_base=t3.legal_base AND t1.fpp_code=t3.fpp_code AND t1.obj_expend_code=t3.obj_expend_code "
                        + "LEFT JOIN temp.prep_allotment t4 ON t1.legal_base=t4.legal_base AND t1.fpp_code=t4.fpp_code AND t1.obj_expend_code=t4.obj_expend_code "
                        + "WHERE t1.fpp_code='" + code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]
                        + "' AND t1.class_code=" + Integer.valueOf(code[5].toString())
                        + "AND t1.legal_base=" + Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString());
                
                selectSQL="SELECT * FROM ("+tblTemp+") tblTmpAllot ";
                if(editMode){
                    selectSQL=selectSQL + "WHERE tblTmpAllot.control_id='"+controlID + "' ORDER BY obj_expend_code";
                }else{
                    if(isExisting(fppcode)){
                        selectSQL=selectSQL + "WHERE tblTmpAllot.control_id=(SELECT control_id FROM ("+tblTemp+")tblTempCntrl "
                                         + "ORDER BY tblTempCntrl.control_id DESC LIMIT 1) ORDER BY obj_expend_code";
                    }else{
                        selectSQL=selectSQL + "ORDER BY obj_expend_code"; 
                        
                    }
                }
                      
                                                          
		try {
			dbc = new connect.DBConnection();                         
			preparedStatement = dbc.prepareStatement(selectSQL);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        classes.utils.clearTable(tblDetailsAllot);
                        int R=0;
			while(rs.next()) {                                       
                            if (R >=tblDetailsAllot.getRowCount()){
                                            ((javax.swing.table.DefaultTableModel)tblDetailsAllot.getModel()).addRow(new Object[] {null, null, null, null, null});
                            }               
                            tblDetailsAllot.setValueAt(rs.getString("titles"), R, 0);
                            tblDetailsAllot.setValueAt(rs.getString("obj_expend_code"), R, 1);  
                            tblDetailsAllot.setValueAt(rs.getDouble("bal_approp"), R, 2);
                            if(editMode){
                                tblDetailsAllot.setValueAt(rs.getDouble("amnt_alloted"), R, 3);
                            }else{
                                if(rs.getString("control_id")!=null){
                                    if(rs.getString("control_id").trim().equals(controlID)){
                                        tblDetailsAllot.setValueAt((rs.getDouble("amnt_alloted")>0?rs.getDouble("amnt_alloted"):0.0), R, 3);
                                    }
                                }
                            }
                            if(rs.getString("remark")!=null){
                                if(editMode){
                                    tblDetailsAllot.setValueAt((rs.getDouble("amnt_alloted")+rs.getDouble("run_bal")),R,4);
                                    tblDetailsAllot.setValueAt((rs.getDouble("amnt_alloted")+rs.getDouble("run_bal")),R,5);
                                    tblDetailsAllot.setValueAt(rs.getString("remark"),R,6);
                                }else{
                                    tblDetailsAllot.setValueAt((rs.getString("remark").equals("OPEN")?rs.getDouble("run_bal"):(rs.getString("remark").equals("CLOSED")?0:rs.getDouble("bal_approp"))), R, 4);
                                    tblDetailsAllot.setValueAt((rs.getString("remark").equals("OPEN")?rs.getDouble("run_bal"):(rs.getString("remark").equals("CLOSED")?0:rs.getDouble("bal_approp"))), R, 5);
                                    tblDetailsAllot.setValueAt(rs.getString("remark"),R,6);
                                }
                            }else{
                                tblDetailsAllot.setValueAt(rs.getDouble("bal_approp"), R, 4);
                                tblDetailsAllot.setValueAt(rs.getDouble("bal_approp"), R, 5);
                                tblDetailsAllot.setValueAt("OPEN",R,6);
                            }
                            R++;
			}
                        preparedStatement.close();
                        rs.close();                       
                        
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
		} finally {
			if (dbc != null) try {
                            dbc.close();
                        } catch (java.sql.SQLException ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private boolean isExisting(String fppcode){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                String[]code=fppcode.split("-");
                String selectSQL="";
                boolean isFound=false;
                selectSQL="SELECT * FROM temp.prep_allotment "
                          + "WHERE fpp_code=?" 
                          + " AND class_code=?"
                          + "AND legal_base=?";          
                                                          
		try {
			dbc = new connect.DBConnection();                         
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setString(1, code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                        preparedStatement.setInt(2, Integer.valueOf(code[5].toString()));
                        preparedStatement.setInt(3, Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
			java.sql.ResultSet rs = preparedStatement.executeQuery();                        
			if(rs.next()) {                                       
                            isFound=true;
			}
                        preparedStatement.close();
                        rs.close();                       
                        
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
		} finally {
			if (dbc != null) try {
                            dbc.close();
                        } catch (java.sql.SQLException ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                return isFound;
    }
    
    private void distribution(String fppcode){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                String[]code=fppcode.split("-");
                String[] seq=lblControlNo.getText().split("-");
                
                String selectSQL="SELECT titles,obj_expend_code,bal_approp,sum(CASE WHEN firstqtr IS NULL THEN 0 ELSE firstqtr END) AS firstqtr, "
                                        +"sum(CASE WHEN secondqtr IS NULL THEN 0 ELSE secondqtr END) AS secondqtr, "
                                        +"sum(CASE WHEN thirdqtr IS NULL THEN 0 ELSE thirdqtr END) AS thirdqtr, "
                                        +"sum(CASE WHEN fourthqtr IS NULL THEN 0 ELSE fourthqtr END) AS fourthqtr, "
                                        +"(bal_approp-(sum(CASE WHEN firstqtr IS NULL THEN 0 ELSE firstqtr END) + sum(CASE WHEN secondqtr IS NULL THEN 0 ELSE secondqtr END) "
                                        +"+ sum(CASE WHEN thirdqtr IS NULL THEN 0 ELSE thirdqtr END) + sum(CASE WHEN fourthqtr IS NULL THEN 0 ELSE fourthqtr END))) as bal "
                                +"FROM "
                                        +"(SELECT t1.legal_base,t1.fpp_code,t1.obj_expend_code,t1.class_code,t2.titles,t1.amount, "
                                        +"t3.retention,t3.bal_approp,t4.amnt_alloted,t4.date_prepared, "
                                        +"CASE WHEN to_char(t4.date_prepared,'MM')::smallint=1 OR to_char(t4.date_prepared,'MM')::smallint=2 OR to_char(t4.date_prepared,'MM')::smallint=3 THEN amnt_alloted END AS firstqtr, "
                                        +"CASE WHEN to_char(t4.date_prepared,'MM')::smallint=4 OR to_char(t4.date_prepared,'MM')::smallint=5 OR to_char(t4.date_prepared,'MM')::smallint=6THEN amnt_alloted END AS secondqtr, "
                                        +"CASE WHEN to_char(t4.date_prepared,'MM')::smallint=7 OR to_char(t4.date_prepared,'MM')::smallint=8 OR to_char(t4.date_prepared,'MM')::smallint=9THEN amnt_alloted END AS thirdqtr, "
                                        +"CASE WHEN to_char(t4.date_prepared,'MM')::smallint=10 OR to_char(t4.date_prepared,'MM')::smallint=11 OR to_char(t4.date_prepared,'MM')::smallint=12THEN amnt_alloted END AS fourthqtr, "
                                        +"t4.run_bal,t4.control_id,t4.remark FROM temp.prep_appropriation t1 "
                                        +"LEFT JOIN acct.accounts t2 ON t1.obj_expend_code=t2.coding "
                                        +"LEFT JOIN buds.allot_retention t3 ON t1.legal_base=t3.legal_base AND t1.fpp_code=t3.fpp_code AND t1.obj_expend_code=t3.obj_expend_code "
                                        +"LEFT JOIN temp.prep_allotment t4 ON t1.legal_base=t4.legal_base AND t1.fpp_code=t4.fpp_code AND t1.obj_expend_code=t4.obj_expend_code "
                                        +"WHERE t1.fpp_code=? "
                                        +"AND t1.class_code=? "
                                        +"AND t1.legal_base=?) "
                                +"GROUP BY titles,obj_expend_code,bal_approp "
                                +"ORDER BY obj_expend_code";
                      
                                                          
		try {
			dbc = new connect.DBConnection();                         
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setString(1, code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                        preparedStatement.setInt(2, Integer.valueOf(code[5].toString()));
                        preparedStatement.setInt(3, Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        classes.utils.clearTable(tblDistribution);
                        int R=0;
			while(rs.next()) {                                       
                            if (R >=tblDistribution.getRowCount()){
                                            ((javax.swing.table.DefaultTableModel)tblDistribution.getModel()).addRow(new Object[] {null, null, null, null, null});
                            }               
                            tblDistribution.setValueAt(rs.getString("titles"), R, 0);
                            tblDistribution.setValueAt(rs.getString("obj_expend_code"), R, 1);  
                            tblDistribution.setValueAt(rs.getDouble("bal_approp"), R, 2);
                            tblDistribution.setValueAt(rs.getDouble("firstqtr"), R, 3);
                            tblDistribution.setValueAt(rs.getDouble("secondqtr"), R, 4);
                            tblDistribution.setValueAt(rs.getDouble("thirdqtr"), R, 5);
                            tblDistribution.setValueAt(rs.getDouble("fourthqtr"), R, 6);
                            tblDistribution.setValueAt(rs.getDouble("bal"), R, 7);
                            
                            R++;
			}
                        preparedStatement.close();
                        rs.close();                       
                        
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
		} finally {
			if (dbc != null) try {
                            dbc.close();
                        } catch (java.sql.SQLException ex) {
                            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void autosaveRentention(){
        if(lblControlNo.getText().equals(""))return;
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            String[]code=fppcode.split("-"); 
            for(int i=0;i<tblDetailsReten.getRowCount();i++){
                dbase.SQLExecute saver = new dbase.SQLExecute("buds.allot_retention");
                saver.FieldName("legal_base",  NUMERIC, enums.Take.InsertUpdate, true,Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
                saver.FieldName("fpp_code",   !NUMERIC, enums.Take.InsertUpdate, true,code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                saver.FieldName("obj_expend_code",   !NUMERIC, enums.Take.InsertUpdate, true,tblDetailsReten.getValueAt(i, 1).toString());
                saver.FieldName("approved_approp",   NUMERIC, enums.Take.InsertUpdate, Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 2)==null?0.0:tblDetailsReten.getValueAt(i, 2)))));
                saver.FieldName("retention",   NUMERIC, enums.Take.InsertUpdate,  Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 3)==null?0.0:tblDetailsReten.getValueAt(i, 3)))));
                saver.FieldName("bal_approp",   NUMERIC, enums.Take.InsertUpdate,  Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 4)==null?0.0:tblDetailsReten.getValueAt(i, 4)))));
                saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate,  new connect.SystemOptions().ServerDate());  
                         
                try {
                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                    if (success == 0) {
                        success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                    }                        
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                    return;
                }     
            }
            if (success > 0){
                dbc.commit();  
                tblDetailsAllot.setEnabled(true);
            }else{
                tblDetailsAllot.setEnabled(false);
                javax.swing.JOptionPane.showMessageDialog(this, "No based appropriation.", "Allotment", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } 
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
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

        cboObjectEx = new javax.swing.JComboBox();
        treeOffice = new javax.swing.JTree();
        popupReten = new javax.swing.JPopupMenu();
        five = new javax.swing.JRadioButtonMenuItem();
        ten = new javax.swing.JRadioButtonMenuItem();
        fifteen = new javax.swing.JRadioButtonMenuItem();
        twenty = new javax.swing.JRadioButtonMenuItem();
        twentyfive = new javax.swing.JRadioButtonMenuItem();
        thirty = new javax.swing.JRadioButtonMenuItem();
        thirtyfive = new javax.swing.JRadioButtonMenuItem();
        forty = new javax.swing.JRadioButtonMenuItem();
        fortyfive = new javax.swing.JRadioButtonMenuItem();
        fifty = new javax.swing.JRadioButtonMenuItem();
        fiftyfive = new javax.swing.JRadioButtonMenuItem();
        sixty = new javax.swing.JRadioButtonMenuItem();
        sixtyfive = new javax.swing.JRadioButtonMenuItem();
        seventy = new javax.swing.JRadioButtonMenuItem();
        seventyfive = new javax.swing.JRadioButtonMenuItem();
        eighty = new javax.swing.JRadioButtonMenuItem();
        eightyfive = new javax.swing.JRadioButtonMenuItem();
        ninety = new javax.swing.JRadioButtonMenuItem();
        ninetyfive = new javax.swing.JRadioButtonMenuItem();
        onehund = new javax.swing.JRadioButtonMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jProgressBar1 = new javax.swing.JProgressBar();
        splitPane = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTreeFPP = new org.jdesktop.swingx.JXTreeTable();
        lblTotalFPPSum = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRecords = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        chDatePrepAllotment = new datechooser.beans.DateChooserCombo();
        jLabel7 = new javax.swing.JLabel();
        lblControlNo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tabPaneAllotment = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetailsReten = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblFooterReten = new javax.swing.JTable(new DefaultTableModel(1,tblDetailsReten.getColumnCount()),tblDetailsReten.getColumnModel());
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDetailsAllot = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblFooterAllot = new javax.swing.JTable(new DefaultTableModel(1,tblDetailsAllot.getColumnCount()),tblDetailsAllot.getColumnModel());
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblDistribution = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblFooterDistrib = new javax.swing.JTable(new DefaultTableModel(1,tblDistribution.getColumnCount()),tblDistribution.getColumnModel());
        jLabel6 = new javax.swing.JLabel();
        btnClass = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSaveAllot = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSaveUpdate = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblMode = new javax.swing.JLabel();

        cboObjectEx.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboObjectEx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboObjectExItemStateChanged(evt);
            }
        });
        cboObjectEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboObjectExActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeOffice.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));

        buttonGroup1.add(five);
        five.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        five.setText("5%");
        five.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveActionPerformed(evt);
            }
        });
        popupReten.add(five);

        buttonGroup1.add(ten);
        ten.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ten.setText("10%");
        ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenActionPerformed(evt);
            }
        });
        popupReten.add(ten);

        buttonGroup1.add(fifteen);
        fifteen.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        fifteen.setText("15%");
        fifteen.setToolTipText("");
        fifteen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fifteenActionPerformed(evt);
            }
        });
        popupReten.add(fifteen);

        buttonGroup1.add(twenty);
        twenty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        twenty.setText("20%");
        twenty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twentyActionPerformed(evt);
            }
        });
        popupReten.add(twenty);

        buttonGroup1.add(twentyfive);
        twentyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        twentyfive.setText("25%");
        twentyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twentyfiveActionPerformed(evt);
            }
        });
        popupReten.add(twentyfive);

        buttonGroup1.add(thirty);
        thirty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        thirty.setText("30%");
        thirty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirtyActionPerformed(evt);
            }
        });
        popupReten.add(thirty);

        buttonGroup1.add(thirtyfive);
        thirtyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        thirtyfive.setText("35%");
        thirtyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirtyfiveActionPerformed(evt);
            }
        });
        popupReten.add(thirtyfive);

        buttonGroup1.add(forty);
        forty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        forty.setText("40%");
        forty.setToolTipText("");
        forty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fortyActionPerformed(evt);
            }
        });
        popupReten.add(forty);

        buttonGroup1.add(fortyfive);
        fortyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        fortyfive.setText("45%");
        fortyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fortyfiveActionPerformed(evt);
            }
        });
        popupReten.add(fortyfive);

        buttonGroup1.add(fifty);
        fifty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        fifty.setText("50%");
        fifty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiftyActionPerformed(evt);
            }
        });
        popupReten.add(fifty);

        buttonGroup1.add(fiftyfive);
        fiftyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        fiftyfive.setText("55%");
        fiftyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiftyfiveActionPerformed(evt);
            }
        });
        popupReten.add(fiftyfive);

        buttonGroup1.add(sixty);
        sixty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sixty.setText("60%");
        sixty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixtyActionPerformed(evt);
            }
        });
        popupReten.add(sixty);

        buttonGroup1.add(sixtyfive);
        sixtyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sixtyfive.setText("65%");
        sixtyfive.setToolTipText("");
        sixtyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixtyfiveActionPerformed(evt);
            }
        });
        popupReten.add(sixtyfive);

        buttonGroup1.add(seventy);
        seventy.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        seventy.setText("70%");
        seventy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seventyActionPerformed(evt);
            }
        });
        popupReten.add(seventy);

        buttonGroup1.add(seventyfive);
        seventyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        seventyfive.setText("75%");
        seventyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seventyfiveActionPerformed(evt);
            }
        });
        popupReten.add(seventyfive);

        buttonGroup1.add(eighty);
        eighty.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        eighty.setText("80%");
        eighty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightyActionPerformed(evt);
            }
        });
        popupReten.add(eighty);

        buttonGroup1.add(eightyfive);
        eightyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        eightyfive.setText("85%");
        eightyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightyfiveActionPerformed(evt);
            }
        });
        popupReten.add(eightyfive);

        buttonGroup1.add(ninety);
        ninety.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ninety.setText("90%");
        ninety.setToolTipText("");
        ninety.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ninetyActionPerformed(evt);
            }
        });
        popupReten.add(ninety);

        buttonGroup1.add(ninetyfive);
        ninetyfive.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ninetyfive.setText("95%");
        ninetyfive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ninetyfiveActionPerformed(evt);
            }
        });
        popupReten.add(ninetyfive);

        buttonGroup1.add(onehund);
        onehund.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        onehund.setText("100%");
        onehund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onehundActionPerformed(evt);
            }
        });
        popupReten.add(onehund);

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximizable(true);
        setTitle("PREPARATION-Allotment");
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(root,java.util.Arrays.asList(header));
        tblTreeFPP=new org.jdesktop.swingx.JXTreeTable(model);
        tblTreeFPP.setShowGrid(true, true);
        tblTreeFPP.getColumnExt(5).setVisible(false);
        tblTreeFPP.getColumnExt(4).setVisible(false);
        //tblTreeFPP.setColumnControlVisible(true);

        tblTreeFPP.packAll();
        tblTreeFPP.setEditable(false);
        tblTreeFPP.setEnabled(false);
        tblTreeFPP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTreeFPPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTreeFPP);

        lblTotalFPPSum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalFPPSum.setForeground(new java.awt.Color(102, 0, 102));
        lblTotalFPPSum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalFPPSum.setText("0.00");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setText("Total FPP:");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("Prepared By");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setText("Certified Correct By");

        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)));

        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setText("Status:");

        lblStatus.setFont(new java.awt.Font("Lucida Sans Typewriter", 2, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
        );

        tblRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ANNUAL BUDGET#", "YEAR", "LEGAL BASIS", "FUND ACCOUNT", "STATUS", "LEGAL", "CTRLUID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRecords.setEnabled(false);
        tblRecords.getTableHeader().setReorderingAllowed(false);
        tblRecords.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRecordsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblRecords);
        tblRecords.getColumnModel().getColumn(1).setMinWidth(50);
        tblRecords.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblRecords.getColumnModel().getColumn(1).setMaxWidth(50);
        tblRecords.getColumnModel().getColumn(4).setMinWidth(100);
        tblRecords.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblRecords.getColumnModel().getColumn(4).setMaxWidth(100);
        tblRecords.getColumnModel().getColumn(5).setMinWidth(0);
        tblRecords.getColumnModel().getColumn(5).setPreferredWidth(0);
        tblRecords.getColumnModel().getColumn(5).setMaxWidth(0);
        tblRecords.getColumnModel().getColumn(6).setMinWidth(0);
        tblRecords.getColumnModel().getColumn(6).setPreferredWidth(0);
        tblRecords.getColumnModel().getColumn(6).setMaxWidth(0);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalFPPSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalFPPSum, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        splitPane.setLeftComponent(jPanel1);

        chDatePrepAllotment.setEnabled(false);
        chDatePrepAllotment.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                chDatePrepAllotmentOnSelectionChange(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setText("Date");

        lblControlNo.setForeground(new java.awt.Color(0, 51, 255));

        jLabel2.setText("Allotment No:");

        tabPaneAllotment.setEnabled(false);
        tabPaneAllotment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPaneAllotmentMouseClicked(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDetailsReten.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<HTML>Object of <BR/> Expenditures</HTML>", "Code", "<HTML>Original <BR/> Appropriation</HTML>", "<HTML>Retention <BR/> Amount</HTML>", "<HTML>Balance<BR/>Appropriation</HTML>"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] lockedReten = new boolean [] {
                false, false, false, false, false
            };
            boolean[] editReten = new boolean [] {
                false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                String[]code=fppcode.split("-");
                if(readOnly){
                    return lockedReten [columnIndex];
                }else{
                    if(code[5].equals("200")|code[5].equals("400")){
                        return editReten [columnIndex];
                    }else{
                        return lockedReten [columnIndex];
                    }
                }
            }
        });
        tblDetailsReten.getColumnModel().getColumn(2).setCellRenderer(
            new DecimalFormatRenderer() );
        tblDetailsReten.getColumnModel().getColumn(3).setCellRenderer(
            new DecimalFormatRenderer() );
        tblDetailsReten.getColumnModel().getColumn(4).setCellRenderer(
            new DecimalFormatRenderer() );

        tblDetailsReten.getModel().addTableModelListener(
            new javax.swing.event.TableModelListener()
            {
                public void tableChanged(javax.swing.event.TableModelEvent evt)
                {
                    double totalApprop=0.0;
                    double totalReten=0.0;
                    double totalBalApprop=0.0;
                    for(int row=0;row<tblDetailsReten.getRowCount();row++){
                        totalApprop+= Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(row, 2)==null?0.0:tblDetailsReten.getValueAt(row, 2))));
                        totalReten+= Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(row, 3)==null?0.0:tblDetailsReten.getValueAt(row, 3))));
                        totalBalApprop+= Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(row, 4)==null?0.0:tblDetailsReten.getValueAt(row, 4))));
                    }
                    tblFooterReten.setValueAt(totalApprop, 0, 2);
                    tblFooterReten.setValueAt(totalReten, 0, 3);
                    tblFooterReten.setValueAt(totalBalApprop, 0, 4);

                }
            });
            tblDetailsReten.getTableHeader().setReorderingAllowed(false);
            tblDetailsReten.getTableHeader().setPreferredSize(new java.awt.Dimension(150,70));
            tblDetailsReten.getColumnModel().getColumn(1).setMinWidth(0);
            tblDetailsReten.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblDetailsReten.getColumnModel().getColumn(1).setMaxWidth(0);
            tblDetailsReten.getColumnModel().getColumn(0).setPreferredWidth(300);
            tblDetailsReten.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDetailsRetenMouseClicked(evt);
                }
            });
            tblDetailsReten.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    tblDetailsRetenKeyPressed(evt);
                }
            });
            jScrollPane1.setViewportView(tblDetailsReten);
            tblDetailsReten.setColumnSelectionAllowed(true);
            tblDetailsReten.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            tblFooterReten.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
            tblFooterReten.setForeground(new java.awt.Color(0, 51, 153));
            tblFooterReten.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null, null}
                },
                new String [] {
                    "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            tblFooterReten.setAutoscrolls(false);
            tblFooterReten.setRowHeight(17);
            tblFooterReten.setRowSelectionAllowed(false);
            tblFooterReten.getTableHeader().setReorderingAllowed(false);
            SyncListener syncListener = new SyncListener(tblDetailsReten,tblFooterReten);
            jScrollPane4.setViewportView(tblFooterReten);
            tblFooterReten.setTableHeader(null);
            tblFooterReten.setValueAt("TOTAL", 0, 0);

            jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(51, 51, 255));
            jLabel1.setText("<HTML>Note: Press <b>ENTER</b> key after inputting amount to compute balance and activate ALLOTMENT tab.</HTML>");

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addComponent(jScrollPane4)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            tabPaneAllotment.addTab("RETENTION(20%)", jPanel4);

            jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            tblDetailsAllot.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "<HTML>Object of <BR/> Expenditures</HTML>", "Code", "Appropriation", "Amount", "<HTML>Running<BR/>Balance</HTML>", "RunBalReference","Remark"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,java.lang.Object.class
                };
                boolean[] editAmount = new boolean [] {
                    false, false, false, true, false,false,false
                };
                boolean[] lockedAmount = new boolean [] {
                    false, false, false, false, false,false,false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    String remark=tblDetailsAllot.getValueAt(rowIndex, 6).toString();
                    if(readOnly){
                        return lockedAmount [columnIndex];
                    }else{
                        if(remark.equals("CLOSED")&(!canEdit)){
                            return lockedAmount [columnIndex];
                        }else{
                            return editAmount [columnIndex];
                        }
                    }
                }
            });
            tblDetailsAllot.getColumnModel().getColumn(2).setCellRenderer(
                new DecimalFormatRenderer() );
            tblDetailsAllot.getColumnModel().getColumn(3).setCellRenderer(
                new DecimalFormatRenderer() );
            tblDetailsAllot.getColumnModel().getColumn(4).setCellRenderer(
                new DecimalFormatRenderer() );
            tblDetailsAllot.getColumnModel().getColumn(5).setCellRenderer(
                new DecimalFormatRenderer() );

            tblDetailsAllot.getModel().addTableModelListener(
                new javax.swing.event.TableModelListener()
                {
                    public void tableChanged(javax.swing.event.TableModelEvent evt)
                    {
                        double totalBalApprop=0.0;
                        double totalAmnt=0.0;
                        double totalRunningBal=0.0;
                        btnSaveAllot.setEnabled(false);
                        for(int row=0;row<tblDetailsAllot.getRowCount();row++){
                            totalBalApprop+= Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(row, 2)==null?0.0:tblDetailsAllot.getValueAt(row, 2))));
                            totalAmnt+= Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(row, 3)==null?0.0:tblDetailsAllot.getValueAt(row, 3))));
                            totalRunningBal+= Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(row, 4)==null?0.0:tblDetailsAllot.getValueAt(row, 4))));
                        }
                        tblFooterAllot.setValueAt(totalBalApprop, 0, 2);
                        tblFooterAllot.setValueAt(totalAmnt, 0, 3);
                        tblFooterAllot.setValueAt(totalRunningBal, 0, 4);
                    }
                });
                tblDetailsAllot.getTableHeader().setReorderingAllowed(false);
                tblDetailsAllot.getTableHeader().setPreferredSize(new java.awt.Dimension(150,70));
                tblDetailsAllot.getColumnModel().getColumn(1).setMinWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(1).setPreferredWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(1).setMaxWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(0).setPreferredWidth(300);
                tblDetailsAllot.getColumnModel().getColumn(5).setMinWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(5).setPreferredWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(5).setMaxWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(6).setMinWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(6).setPreferredWidth(0);
                tblDetailsAllot.getColumnModel().getColumn(6).setMaxWidth(0);
                tblDetailsAllot.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        tblDetailsAllotMouseClicked(evt);
                    }
                });
                tblDetailsAllot.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        tblDetailsAllotKeyPressed(evt);
                    }
                });
                jScrollPane5.setViewportView(tblDetailsAllot);
                tblDetailsAllot.setColumnSelectionAllowed(true);
                tblDetailsAllot.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

                tblFooterAllot.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
                tblFooterAllot.setForeground(new java.awt.Color(0, 51, 153));
                tblFooterAllot.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                        {null, null, null, null, null, null, null}
                    },
                    new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
                    }
                ) {
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false, false, false, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                });
                tblFooterAllot.setAutoscrolls(false);
                tblFooterAllot.setRowHeight(17);
                tblFooterAllot.setRowSelectionAllowed(false);
                tblFooterAllot.getTableHeader().setReorderingAllowed(false);
                SyncListener syncListener1 = new SyncListener(tblDetailsAllot,tblFooterAllot);
                jScrollPane6.setViewportView(tblFooterAllot);
                tblFooterAllot.setTableHeader(null);
                tblFooterAllot.setValueAt("TOTAL", 0, 0);

                jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(51, 51, 255));
                jLabel3.setText("<HTML>Note: Press <b>ENTER</b> key after inputting amount to compute balance and activate SAVE button.</HTML>");

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                        .addContainerGap())
                );
                jPanel5Layout.setVerticalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

                tabPaneAllotment.addTab("ALLOTMENT", jPanel5);

                jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                tblDistribution.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "<HTML>Object of <BR/> Expenditures</HTML>", "Code", "Appropriation", "1st QTR", "2nd QTR", "3rd QTR","4th QTR", "<HTML>Appropriation <BR/> Balance</HTML>"
                    }
                ) {
                    Class[] types = new Class [] {
                        java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
                    };
                    boolean[] lockedAmount = new boolean [] {
                        false, false, false, false, false,false,false,false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return lockedAmount [columnIndex];
                    }
                });
                tblDistribution.getColumnModel().getColumn(2).setCellRenderer(
                    new DecimalFormatRenderer() );
                tblDistribution.getColumnModel().getColumn(3).setCellRenderer(
                    new DecimalFormatRenderer() );
                tblDistribution.getColumnModel().getColumn(4).setCellRenderer(
                    new DecimalFormatRenderer() );
                tblDistribution.getColumnModel().getColumn(5).setCellRenderer(
                    new DecimalFormatRenderer() );
                tblDistribution.getColumnModel().getColumn(6).setCellRenderer(
                    new DecimalFormatRenderer() );
                tblDistribution.getColumnModel().getColumn(7).setCellRenderer(
                    new DecimalFormatRenderer() );

                tblDistribution.getModel().addTableModelListener(
                    new javax.swing.event.TableModelListener()
                    {
                        public void tableChanged(javax.swing.event.TableModelEvent evt)
                        {
                            double totalBalApprop=0.0;
                            double totalAmnt=0.0;
                            double totalRunningBal=0.0;
                            for(int row=0;row<tblDistribution.getRowCount();row++){
                                totalBalApprop+= Double.parseDouble(String.valueOf((tblDistribution.getValueAt(row, 2)==null?0.0:tblDistribution.getValueAt(row, 2))));
                                totalAmnt+= Double.parseDouble(String.valueOf((tblDistribution.getValueAt(row, 3)==null?0.0:tblDistribution.getValueAt(row, 3))));
                                totalRunningBal+= Double.parseDouble(String.valueOf((tblDistribution.getValueAt(row, 7)==null?0.0:tblDistribution.getValueAt(row, 7))));
                            }
                            tblFooterDistrib.setValueAt(totalBalApprop, 0, 2);
                            tblFooterDistrib.setValueAt(totalAmnt, 0, 3);
                            tblFooterDistrib.setValueAt(totalRunningBal, 0, 7);
                        }
                    });
                    tblDistribution.getTableHeader().setReorderingAllowed(false);
                    tblDistribution.getTableHeader().setPreferredSize(new java.awt.Dimension(150,70));
                    tblDistribution.getColumnModel().getColumn(1).setMinWidth(0);
                    tblDistribution.getColumnModel().getColumn(1).setPreferredWidth(0);
                    tblDistribution.getColumnModel().getColumn(1).setMaxWidth(0);
                    tblDistribution.getColumnModel().getColumn(0).setPreferredWidth(300);
                    tblDistribution.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            tblDistributionMouseClicked(evt);
                        }
                    });
                    tblDistribution.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent evt) {
                            tblDistributionKeyPressed(evt);
                        }
                    });
                    jScrollPane9.setViewportView(tblDistribution);
                    tblDistribution.setColumnSelectionAllowed(true);
                    tblDistribution.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

                    tblFooterDistrib.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
                    tblFooterDistrib.setForeground(new java.awt.Color(0, 51, 153));
                    tblFooterDistrib.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                            {null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                            "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
                        }
                    ) {
                        boolean[] canEdit = new boolean [] {
                            false, false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    });
                    tblFooterDistrib.setAutoscrolls(false);
                    tblFooterDistrib.setRowHeight(17);
                    tblFooterDistrib.setRowSelectionAllowed(false);
                    tblFooterDistrib.getTableHeader().setReorderingAllowed(false);
                    SyncListener syncListener2 = new SyncListener(tblDistribution,tblFooterDistrib);
                    jScrollPane10.setViewportView(tblFooterDistrib);
                    tblFooterDistrib.setTableHeader(null);
                    tblFooterDistrib.setValueAt("TOTAL", 0, 0);

                    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    jLabel6.setForeground(new java.awt.Color(51, 51, 255));
                    jLabel6.setText("<HTML>Note: <b>Double-click</b> on the table cell of every quarter to show details.</HTML>");

                    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                    jPanel7.setLayout(jPanel7Layout);
                    jPanel7Layout.setHorizontalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane10)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                            .addContainerGap())
                    );
                    jPanel7Layout.setVerticalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                    );

                    tabPaneAllotment.addTab("DISTRIBUTION", jPanel7);

                    btnClass.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnClass.setText("Select Class");
                    btnClass.setEnabled(false);
                    btnClass.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnClassActionPerformed(evt);
                        }
                    });

                    btnCancel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnCancel.setText("Cancel");
                    btnCancel.setEnabled(false);
                    btnCancel.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnCancelActionPerformed(evt);
                        }
                    });

                    btnSaveAllot.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnSaveAllot.setText("Save");
                    btnSaveAllot.setEnabled(false);
                    btnSaveAllot.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnSaveAllotActionPerformed(evt);
                        }
                    });

                    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                    jPanel2.setLayout(jPanel2Layout);
                    jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tabPaneAllotment)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chDatePrepAllotment, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblControlNo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(btnClass)
                            .addGap(18, 18, 18)
                            .addComponent(btnCancel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSaveAllot)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(chDatePrepAllotment, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblControlNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addComponent(tabPaneAllotment)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCancel)
                                .addComponent(btnSaveAllot)
                                .addComponent(btnClass))
                            .addContainerGap())
                    );

                    splitPane.setRightComponent(jPanel2);

                    jToolBar1.setRollover(true);

                    btnNew.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/filenew.png"))); // NOI18N
                    btnNew.setText("NEW");
                    btnNew.setFocusable(false);
                    btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    btnNew.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnNewActionPerformed(evt);
                        }
                    });
                    jToolBar1.add(btnNew);

                    btnOpen.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/open.png"))); // NOI18N
                    btnOpen.setText("OPEN");
                    btnOpen.setFocusable(false);
                    btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    btnOpen.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnOpenActionPerformed(evt);
                        }
                    });
                    jToolBar1.add(btnOpen);

                    btnSaveUpdate.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                    btnSaveUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
                    btnSaveUpdate.setText("SAVE/UPDATE (For Review)");
                    btnSaveUpdate.setEnabled(false);
                    btnSaveUpdate.setFocusable(false);
                    btnSaveUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    btnSaveUpdate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    btnSaveUpdate.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            btnSaveUpdateActionPerformed(evt);
                        }
                    });
                    jToolBar1.add(btnSaveUpdate);
                    jToolBar1.add(jSeparator2);

                    lblMode.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
                    lblMode.setForeground(new java.awt.Color(204, 0, 0));
                    jToolBar1.add(lblMode);

                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                    getContentPane().setLayout(layout);
                    layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(splitPane)
                            .addContainerGap())
                    );
                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(splitPane)
                            .addContainerGap())
                    );

                    pack();
                }// </editor-fold>//GEN-END:initComponents
    
    private void cboObjectExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboObjectExActionPerformed
        
    }//GEN-LAST:event_cboObjectExActionPerformed

    private void cboObjectExItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboObjectExItemStateChanged
        if(tblDetailsReten.getRowCount()>0){
            if(cboObjectEx.getSelectedIndex()>=0){
                tblDetailsReten.setValueAt(arrObjID.get(cboObjectEx.getSelectedIndex()), tblDetailsReten.getSelectedRow(), 1);
            }
        }
    }//GEN-LAST:event_cboObjectExItemStateChanged

    private void tblTreeFPPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTreeFPPMouseClicked

        final int rowIndex = tblTreeFPP.getSelectedRow();
        if (rowIndex < 0) {
            return;
        }
        classes.utils.clearTable(tblDetailsReten);
        final org.jdesktop.swingx.treetable.TreeTableNode selectedNode = (org.jdesktop.swingx.treetable.TreeTableNode)tblTreeFPP.getPathForRow(rowIndex).getLastPathComponent();
        Object[] node=(Object[]) selectedNode.getUserObject();
        fppcode=node[5].toString();
        String[]code=node[5].toString().split("-");
        String[]desc=node[0].toString().split("~");         
        if(node[5].toString().split("-").length>5){
            tabPaneAllotment.setEnabled(true);
            tabPaneAllotment.setSelectedIndex(0);
            tabPaneAllotment.setEnabledAt(1, !(desc[0].equals("200")|desc[0].equals("400")));
            classes.utils.clearTable(tblDetailsAllot);
            classes.utils.clearTable(tblDetailsReten);
            allot_retention(fppcode);
            tblTreeFPP.setEnabled(false);
            btnCancel.setEnabled(true);
            btnClass.setEnabled(true);
            tblRecords.setEnabled(false);
            tblFooterReten.setValueAt("Total " + desc[1].toString(), 0, 0);
            tblFooterAllot.setValueAt("Total " + desc[1].toString(), 0, 0);
        }
    }//GEN-LAST:event_tblTreeFPPMouseClicked

    private void tblRecordsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRecordsMouseClicked
        if(!tblRecords.isEnabled())return;
        setEditFPP(Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()), Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 6).toString()));
        total_FPP(Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()), tblRecords.getValueAt(tblRecords.getSelectedRow(), 6).toString());   
        tblTreeFPP.setEnabled(true);
    }//GEN-LAST:event_tblRecordsMouseClicked

    private void fiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.05, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fiveActionPerformed

    private void tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.10, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_tenActionPerformed

    private void fifteenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fifteenActionPerformed
       tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.15, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fifteenActionPerformed

    private void twentyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twentyActionPerformed
       tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.20, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_twentyActionPerformed

    private void twentyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twentyfiveActionPerformed
       tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.25, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_twentyfiveActionPerformed

    private void thirtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirtyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.30, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_thirtyActionPerformed

    private void thirtyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirtyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.35, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_thirtyfiveActionPerformed

    private void fortyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fortyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.40, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fortyActionPerformed

    private void fortyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fortyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.45, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fortyfiveActionPerformed

    private void fiftyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiftyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.50, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fiftyActionPerformed

    private void fiftyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiftyfiveActionPerformed
       tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.55, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_fiftyfiveActionPerformed

    private void sixtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixtyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.60, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_sixtyActionPerformed

    private void sixtyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixtyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.65, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_sixtyfiveActionPerformed

    private void seventyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seventyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.70, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_seventyActionPerformed

    private void seventyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seventyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.75, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_seventyfiveActionPerformed

    private void eightyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightyActionPerformed
       tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.80, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_eightyActionPerformed

    private void eightyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.85, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_eightyfiveActionPerformed

    private void ninetyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ninetyActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.90, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_ninetyActionPerformed

    private void ninetyfiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ninetyfiveActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 0.95, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_ninetyfiveActionPerformed

    private void onehundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onehundActionPerformed
        tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)==null?0.0:tblDetailsReten.getValueAt(tblDetailsReten.getSelectedRow(), 2)))) * 1, tblDetailsReten.getSelectedRow(), 3);
    }//GEN-LAST:event_onehundActionPerformed

    private void chDatePrepAllotmentOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_chDatePrepAllotmentOnSelectionChange
//        month=Integer.valueOf(org.joda.time.format.DateTimeFormat.forPattern("M").print(new org.joda.time.LocalDate(chDatePrepAllotment.getSelectedDate())));
    }//GEN-LAST:event_chDatePrepAllotmentOnSelectionChange

    private void btnSaveUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveUpdateActionPerformed
        
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        String[]code=fppcode.split("-");
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            dbase.SQLExecute saver = new dbase.SQLExecute("buds.allotment_status");
            saver.FieldName("legal_base", NUMERIC, enums.Take.InsertUpdate, Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
            saver.FieldName("fund_id", NUMERIC, enums.Take.InsertUpdate, Integer.valueOf(code[0]));
            saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate, new connect.SystemOptions().ServerDate());
            saver.FieldName("status",  !NUMERIC, enums.Take.InsertUpdate,"For Review");
            saver.FieldName("control_id",  !NUMERIC, enums.Take.InsertUpdate,true,lblControlNo.getText());
            String[] seq=lblControlNo.getText().split("-");
            saver.FieldName("seq_id",  NUMERIC, enums.Take.InsertUpdate,Integer.valueOf(seq[2].toString()));

            try {
                success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                if (success == 0) {
                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                }
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                return;
            }

            if (success > 0){
                dbc.commit();
                classes.utils.clearTable(tblDetailsReten);
                classes.utils.clearTable(tblDetailsAllot);
                classes.utils.clearTable(tblDistribution);
                tabPaneAllotment.setEnabled(false);
                btnNew.setEnabled(true);
                lblControlNo.setText("");
                tblRecords.clearSelection();
                tblRecords.setEnabled(false);
                model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
                tblTreeFPP.setTreeTableModel(model);
                lblTotalFPPSum.setText("");
                btnSaveUpdate.setEnabled(false);
                javax.swing.JOptionPane.showMessageDialog(this, "Record saved successfully.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);                
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "No record to saved.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){
                try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnSaveUpdateActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        java.awt.Image img=new javax.swing.ImageIcon(getClass().getResource("/images/open.png")).getImage();
        transaction.allotment.DlgAppropriationAllotment dialog=new transaction.allotment.DlgAppropriationAllotment(new budget.MainMDI(), true);
        dialog.setTitle("SEARCH");
        dialog.setIconImage(img);    
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true); 
        if(dialog.isEditMode()){
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            lblMode.setText("");
            readOnly=false;
            editMode=dialog.isEditMode();
            fundID=dialog.getFundID();
            controlID=dialog.getControlID();
            setEditFPP(dialog.getLegalID(),fundID);
            lblMode.setText("CAUTION!..Edit Mode");
            lblControlNo.setText(controlID);
            btnSaveUpdate.setEnabled(false);
            for(int i=0;i<tblRecords.getRowCount();i++) {
                if(tblRecords.getValueAt(i, 5).toString().equalsIgnoreCase(dialog.getLegalID().toString()) 
                        & tblRecords.getValueAt(i, 6).toString().equals(dialog.getFundID().toString())){
                    tblRecords.setRowSelectionInterval(i, i);
                    break;
                }    
            }
            tblRecords.setEnabled(false);
            tblTreeFPP.setEnabled(true);
            btnNew.setEnabled(false);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }else if(dialog.isReadOnly()){
            lblMode.setText("Read Only...");
            editMode=false;
            readOnly=dialog.isReadOnly();
            fundID=dialog.getFundID();
            controlID=dialog.getControlID();
            setEditFPP(dialog.getLegalID(),fundID);
            btnSaveUpdate.setEnabled(false);
            lblControlNo.setText(controlID); 
            tblRecords.setEnabled(true);
            classes.utils.clearTable(tblDetailsAllot);
            classes.utils.clearTable(tblDetailsReten);
            classes.utils.clearTable(tblDistribution);
        }else{
            btnNewActionPerformed(evt);
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if(editMode){return;}
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT seq_id FROM buds.allotment_status ORDER BY seq_id DESC LIMIT 1";
        try {
            int sequence=0;
            dbc = new connect.DBConnection();
            preparedStatement = dbc.prepareStatement(selectSQL);
            java.sql.ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                sequence=rs.getInt(1) ;
            }
            preparedStatement.close();
            rs.close(); 
            lblMode.setText("");
            controlID="AA"+ org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM").print(new org.joda.time.LocalDate(chDatePrepAllotment.getSelectedDate()))+"-"+String.format("%04d",sequence + 1 );
            String[] allotNo=controlID.split("-");
            lblControlNo.setText(allotNo[0]+"-"+allotNo[1]+"-####");
            tblRecords.setEnabled(true);     
            deleteTempAllotment();
            btnNew.setEnabled(false);
            canEdit=false;
            readOnly=false;
            initForm=false;
        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnNewActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        if(!lblMode.getText().equals("")|initForm){
            dispose();
        }else{
            if(!lblControlNo.getText().equals("")){
                connect.DBConnection dbc = null;
                int success=0;
                try {
                    dbc = new connect.DBConnection();
                    dbc.setAutoCommit(false);
                    dbase.SQLExecute deleteAllotment = new dbase.SQLExecute("temp.prep_allotment");
                    deleteAllotment.FieldName("control_id",  !NUMERIC, enums.Take.ConditionOnly, lblControlNo.getText().toString());
                    success=dbc.executeUpdate(deleteAllotment.Perform(enums.Fire.doDelete));            

                    if(success>0){
                        int resp=classes.MsgBox.showConfirm(this,"You have pending transaction. Are you sure you want to close? ", getTitle());
                        if(resp==javax.swing.JOptionPane.YES_OPTION){
                            dbc.commit();
                            dbc.close();
                            dispose();
                        }
                    }else{
                        dispose();
                    }

                } catch (Exception ex) {
                    if (dbc != null) try {
                        dbc.rollback();
                    } catch (Exception rlbk) {
                        java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, rlbk);
                    }
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);

                } finally {
                    if (dbc != null) try {
                        dbc.close();
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }       
            }
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void tblDetailsAllotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailsAllotMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDetailsAllotMouseClicked

    private void tblDetailsAllotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailsAllotKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            if (tblDetailsAllot.isEditing()){
                tblDetailsAllot.getCellEditor().stopCellEditing();
            }
            
            double runningBal=0.0;                   
            for(int i=0;i<tblDetailsAllot.getRowCount();i++){
                runningBal=Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 5)==null?0.0:tblDetailsAllot.getValueAt(i, 5))))
                        -Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 3)==null?0.0:tblDetailsAllot.getValueAt(i, 3))));
                tblDetailsAllot.setValueAt(runningBal,i,4);
                if(runningBal<0){
                    MsgBox.showWarning(this, "The running balance in " + (i+1)+ordinal((i+1)) +" row is already negative.", title);
                    return;                  
                } else{                    
                    btnSaveAllot.setEnabled(true);
                }
            }
            
        }
    }//GEN-LAST:event_tblDetailsAllotKeyPressed

    private void tblDetailsRetenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailsRetenKeyPressed
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            if (tblDetailsReten.isEditing()){
                tblDetailsReten.getCellEditor().stopCellEditing();
            }
            double totalApprop=0.0;
            double totalReten=0.0;
            for(int i=0;i<tblDetailsReten.getRowCount();i++){
                totalApprop+= Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 2)==null?0.0:tblDetailsReten.getValueAt(i, 2))));
                totalReten+= Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 3)==null?0.0:tblDetailsReten.getValueAt(i, 3))));
                tblDetailsReten.setValueAt(Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 2)==null?0.0:tblDetailsReten.getValueAt(i, 2))))-
                        Double.parseDouble(String.valueOf((tblDetailsReten.getValueAt(i, 3)==null?0.0:tblDetailsReten.getValueAt(i, 3)))),i,4);
            }       
            String[]code=fppcode.split("-");
            if(code[4].equals("200")|code[4].equals("400")){
                if(totalReten ==(totalApprop * 0.20)){
                    tabPaneAllotment.setEnabledAt(1, true);
                }else{
                    MsgBox.showError(this, "Total retention is not equal to 20% of the total appropriation.", title);
                    tabPaneAllotment.setEnabledAt(1, false);
                    tblFooterReten.setValueAt(0.00, 0, 3);
                }
            }else{
                tabPaneAllotment.setEnabledAt(1, true);
            }
        }
    }//GEN-LAST:event_tblDetailsRetenKeyPressed

    private void tblDetailsRetenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailsRetenMouseClicked
        tblDetailsReten.setComponentPopupMenu(null);
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            if(tblDetailsReten.getSelectedColumn()==3 && tblDetailsReten.isCellEditable(tblDetailsReten.getSelectedRow(), 3)){
                tblDetailsReten.setComponentPopupMenu(popupReten);
            }
        } else {
            tblDetailsReten.setComponentPopupMenu(null);
        }
    }//GEN-LAST:event_tblDetailsRetenMouseClicked

    private void btnClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClassActionPerformed
        if (tblDetailsReten.isEditing()){
            tblDetailsReten.getCellEditor().stopCellEditing();
        }
        classes.utils.clearTable(tblDetailsReten);
        tblTreeFPP.clearSelection();
        tblTreeFPP.setEnabled(true);
        btnClass.setEnabled(false);        
    }//GEN-LAST:event_btnClassActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if(!lblMode.getText().equals("")){
            if (tblDetailsAllot.isEditing()){
                    tblDetailsAllot.getCellEditor().stopCellEditing();
                }
                classes.utils.clearTable(tblDetailsAllot);
                classes.utils.clearTable(tblDetailsReten);
                tblRecords.clearSelection();
                tblRecords.setEnabled(false);
                tabPaneAllotment.setEnabled(false);
                model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
                tblTreeFPP.setTreeTableModel(model);
                lblTotalFPPSum.setText("");
                btnCancel.setEnabled(false);
                lblControlNo.setText("");
                btnNew.setEnabled(true);
                lblMode.setText("");
                editMode=false;
        }else{
            connect.DBConnection dbc = null;
            int success=0;
            try {
                dbc = new connect.DBConnection();
                dbc.setAutoCommit(false);
                dbase.SQLExecute deleteAllotment = new dbase.SQLExecute("temp.prep_allotment");
                deleteAllotment.FieldName("control_id",  !NUMERIC, enums.Take.ConditionOnly, lblControlNo.getText().toString());
                success=dbc.executeUpdate(deleteAllotment.Perform(enums.Fire.doDelete));            

                if(success>0){
                    int resp=classes.MsgBox.showConfirm(this,"You have pending transaction. Are you sure you want to cancel? ", getTitle());
                    if(resp==javax.swing.JOptionPane.YES_OPTION){
                        dbc.commit();
                        dbc.close();       
                        if (tblDetailsAllot.isEditing()){
                            tblDetailsAllot.getCellEditor().stopCellEditing();
                        }
                        classes.utils.clearTable(tblDetailsAllot);
                        classes.utils.clearTable(tblDetailsReten);
                        tblRecords.clearSelection();
                        tblRecords.setEnabled(false);
                        tabPaneAllotment.setEnabled(false);
                        model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
                        tblTreeFPP.setTreeTableModel(model);
                        lblTotalFPPSum.setText("");
                        btnCancel.setEnabled(false);
                        lblControlNo.setText("");  
                        btnNew.setEnabled(true);
                    }
                }else{
                    if (tblDetailsAllot.isEditing()){
                        tblDetailsAllot.getCellEditor().stopCellEditing();
                    }
                    classes.utils.clearTable(tblDetailsAllot);
                    classes.utils.clearTable(tblDetailsReten);
                    tblRecords.clearSelection();
                    tblRecords.setEnabled(false);
                    tabPaneAllotment.setEnabled(false);
                    model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
                    tblTreeFPP.setTreeTableModel(model);
                    lblTotalFPPSum.setText("");
                    btnCancel.setEnabled(false);
                    lblControlNo.setText("");
                    btnNew.setEnabled(true);
                }


            } catch (Exception ex) {
                if (dbc != null) try {
                    dbc.rollback();
                } catch (Exception rlbk) {
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, rlbk);
                }
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);

            } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }   
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveAllotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAllotActionPerformed
        if(Double.valueOf(tblFooterAllot.getValueAt(0, 3).toString())==0){MsgBox.showWarning(this, "Zero amount to save!", "SAVE/UPDATE RECORD");return;}
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            String[] allotNo=controlID.split("-");            
            String[]code=fppcode.split("-"); 
            for(int i=0;i<tblDetailsAllot.getRowCount();i++){
                if(Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 3)==null?0.0:tblDetailsAllot.getValueAt(i, 3))))>0.0){
                    dbase.SQLExecute saver = new dbase.SQLExecute("temp.prep_allotment");
                    saver.FieldName("legal_base",  NUMERIC, enums.Take.InsertUpdate, true,Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()));
                    saver.FieldName("fpp_code",   !NUMERIC, enums.Take.InsertUpdate, true,code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                    saver.FieldName("class_code",   NUMERIC, enums.Take.InsertUpdate, Integer.valueOf(code[5].toString()));
                    saver.FieldName("obj_expend_code",   !NUMERIC, enums.Take.InsertUpdate, true,tblDetailsAllot.getValueAt(i, 1).toString());                
                    saver.FieldName("amnt_alloted",   NUMERIC, enums.Take.InsertUpdate,  Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 3)==null?0.0:tblDetailsAllot.getValueAt(i, 3)))));
                    saver.FieldName("run_bal",   NUMERIC, enums.Take.InsertUpdate,  Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 4)==null?0.0:tblDetailsAllot.getValueAt(i, 4)))));
                    saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate,  new connect.SystemOptions().ServerDate());                  
    //                saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate,  org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(new org.joda.time.LocalDate(chDatePrepAllotment.getSelectedDate())));                  
                    saver.FieldName("control_id",   !NUMERIC, enums.Take.InsertUpdate,  true,controlID);
                    if(Double.parseDouble(String.valueOf((tblDetailsAllot.getValueAt(i, 4)==null?0.0:tblDetailsAllot.getValueAt(i, 4))))==0){
                        saver.FieldName("remark",   !NUMERIC, enums.Take.InsertUpdate,  "CLOSED");
                    }
                    try {
                        success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                        if (success == 0) {
                            success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                        }                        
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                        java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                        return;
                    }  
                }
            }
            if (success > 0){
                dbc.commit();                 
                javax.swing.JOptionPane.showMessageDialog(this, "Record temporarily saved.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                classes.utils.clearTable(tblDetailsAllot);
                tblTreeFPP.setEnabled(true);
                btnCancel.setEnabled(false);
                btnSaveUpdate.setEnabled(true);
                canEdit=true;
                lblControlNo.setText(allotNo[0]+"-"+allotNo[1]+"-"+allotNo[2]);
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "No record to save.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } 
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnSaveAllotActionPerformed

    private void tabPaneAllotmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPaneAllotmentMouseClicked
        if(!tabPaneAllotment.isEnabled())return;
                
        if(tabPaneAllotment.getSelectedIndex()==0){
            classes.utils.clearTable(tblDetailsReten);
            allot_retention(fppcode);
        }else if(tabPaneAllotment.getSelectedIndex()==1){  
            if(tabPaneAllotment.isEnabledAt(1)){   
                classes.utils.clearTable(tblDetailsAllot);
                autosaveRentention();
                allotment(fppcode);
            }
         }else if(tabPaneAllotment.getSelectedIndex()==2){ 
             distribution(fppcode);
         }
    }//GEN-LAST:event_tabPaneAllotmentMouseClicked

    private void tblDistributionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDistributionMouseClicked
        if(evt.getClickCount()==2 & (tblDistribution.getSelectedColumn()==3 | tblDistribution.getSelectedColumn()==4
                |tblDistribution.getSelectedColumn()==5|tblDistribution.getSelectedColumn()==6)){
            String code[]=fppcode.split("-");
            java.awt.Image img=new javax.swing.ImageIcon(getClass().getResource("/images/open.png")).getImage();
            transaction.allotment.DlgAllotmentDetails dialog=new transaction.allotment.DlgAllotmentDetails(new budget.MainMDI(), true,
                    code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4],
                    Integer.valueOf(code[5].toString()),
                    tblDistribution.getValueAt(tblDistribution.getSelectedRow(), 1).toString(),
                    Integer.valueOf(tblRecords.getValueAt(tblRecords.getSelectedRow(), 5).toString()),
                    tblDistribution.getSelectedColumn());
            dialog.setTitle(" ALLOTMENT DETAILS");
            dialog.setIconImage(img);    
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true); 
        }
    }//GEN-LAST:event_tblDistributionMouseClicked

    private void tblDistributionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDistributionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDistributionKeyPressed
         
    private void deleteTempAllotment(){
        connect.DBConnection dbc = null;
        int success=0;
        try {
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            dbase.SQLExecute deleteAllotment = new dbase.SQLExecute("temp.prep_allotment");
            deleteAllotment.FieldName("control_id",  !NUMERIC, enums.Take.ConditionOnly, controlID);
            success=dbc.executeUpdate(deleteAllotment.Perform(enums.Fire.doDelete));          
            if(success>0){
                dbc.commit();
                dbc.close();
            }
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception rlbk) {
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, rlbk);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
            java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);

        } finally {
            if (dbc != null) try {
                dbc.close();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
            }
        }       
    }
    
    private void setEditFPP(Integer legal,Integer fundID){          
      
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement;  
        Object[][] treevalue;
     
        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement("SELECT * FROM temp.prep_respcenter t1 "
                                                    +"FULL OUTER JOIN (SELECT fpp_code,sum(amount) AS amount "
                                                      +"FROM temp.compute_amount() WHERE legal_base=" + legal
                                                      +" GROUP BY fpp_code) t2 "
                                                   +"ON t1.centre_uid=t2.fpp_code "
                                                 +"INNER JOIN buds.appropriation_status t3 "
                                                 +"ON t1.funds_id=t3.fund_id AND t1.legal_base=t3.legal_base "
                                                 +"WHERE t1.legal_base=? AND t1.funds_id=? ORDER BY centre_uid");
                preparedStatement.setInt(1, legal);
                preparedStatement.setInt(2, fundID);
                java.sql.ResultSet rs = preparedStatement.executeQuery();
                int ctrow = 0;
                while(rs.next()){
                    ctrow=rs.getRow();
                }
                rs.close();
                
                treevalue = new Object[ctrow][9];
        
                int row = 0;
                java.sql.ResultSet rs1 = preparedStatement.executeQuery();
                while (rs1.next()) {
                    treevalue[row][0] = rs1.getString("centre_uid");
                    treevalue[row][1] = rs1.getString("parent_id");
                    if(rs1.getInt("classes")==0){
                        if(rs1.getInt("programs")==0){
                            if(rs1.getInt("resp_sub")==0){
                                if(rs1.getInt("resp_id")==0){
                                    if(rs1.getInt("funds_id")==0){
                                        treevalue[row][2] = rs1.getString("description");
                                    }else{
                                        treevalue[row][2] = rs1.getInt("funds_id") + "~" + rs1.getString("description");
                                    }                                
                                }else{
                                    treevalue[row][2] = rs1.getInt("resp_id") + "~" + rs1.getString("description");
                                }
                            }else{
                                treevalue[row][2] = rs1.getInt("resp_sub") + "~" + rs1.getString("description");
                            }
                        }else{
                            treevalue[row][2] = rs1.getInt("programs") + "~" + rs1.getString("description");
                        }
                    }else{
                        treevalue[row][2] = rs1.getInt("classes") + "~" + rs1.getString("description");
                    }
                        
                    treevalue[row][3]=rs1.getInt("programs");
                    treevalue[row][4]=rs1.getString("centre_uid");
                    if(rs1.getInt("classes")!=0){
                        treevalue[row][5]=new java.text.DecimalFormat("#,##0.00").format(rs1.getDouble("amount"));
                        treevalue[row][6]="";
                        treevalue[row][7]="";
                        treevalue[row][8]="";
                    }else if(rs1.getInt("programs")!=0){
                        treevalue[row][5]="";
                        treevalue[row][6]=new java.text.DecimalFormat("#,##0.00").format(rs1.getDouble("amount"));
                        treevalue[row][7]="";
                        treevalue[row][8]="";
                    }else if(rs1.getInt("resp_sub")!=0){
                        treevalue[row][5]="";
                        treevalue[row][6]="";
                        treevalue[row][7]=new java.text.DecimalFormat("#,##0.00").format(rs1.getDouble("amount"));
                        treevalue[row][8]="";
                    }else if(rs1.getInt("resp_id")!=0){
                        treevalue[row][5]="";
                        treevalue[row][6]="";
                        treevalue[row][7]="";
                        treevalue[row][8]=new java.text.DecimalFormat("#,##0.00").format(rs1.getDouble("amount"));
                    }else{
                        treevalue[row][5]="";
                        treevalue[row][6]="";
                        treevalue[row][7]="";
                        treevalue[row][8]="";
                    } 
                    lblStatus.setText("");
                    row++;
                }                
                rs1.close();   
                preparedStatement.close();
                
           
                    //Create as many nodes as there are rows of data.
                treetable.ChildNode[] node = new treetable.ChildNode[treevalue.length];               
                for (int i = 0; i < treevalue.length; i++) {        
                    node[i] = new treetable.ChildNode(new String[] {treevalue[i][2].toString(),treevalue[i][6].toString(),treevalue[i][7].toString(),
                        treevalue[i][8].toString(),treevalue[i][3].toString(),treevalue[i][4].toString()}); 
                }

                if(node.length>0){
                    root =  node[0];   //Set the root node

                    //Cycle through the table above and assign nodes to nodes
                    for (int i = 0; i < treevalue.length; i++) {
                        for (int j = 1; j < treevalue.length; j++) {
                                if (treevalue[i][0].equals(treevalue[j][1])) {
                                    node[i].add(node[j]);  
                                }    
                        }  
                    }
                    model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(root,java.util.Arrays.asList(header)); 
                    tblTreeFPP.setTreeTableModel(model);                    
                    tblTreeFPP.expandAll();
                }else{
                    model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
                    tblTreeFPP.setTreeTableModel(model);
                }
                tblTreeFPP.getColumnExt(0).setWidth(300);
                tblTreeFPP.getColumnExt(0).setPreferredWidth(300);
                tblTreeFPP.getColumnExt(0).setMinWidth(300);   
                tblTreeFPP.getColumnExt(5).setVisible(false);
                tblTreeFPP.getColumnExt(4).setVisible(false);
                tblTreeFPP.getColumnExt(1).setHighlighters(new org.jdesktop.swingx.decorator.ColorHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,null, new java.awt.Color(0,51,153)));
                tblTreeFPP.getColumnExt(2).setHighlighters(new org.jdesktop.swingx.decorator.ColorHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,null, new java.awt.Color(0,153,153)));
                tblTreeFPP.getColumnExt(3).setHighlighters(new org.jdesktop.swingx.decorator.ColorHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,null, new java.awt.Color(153,0,153)));
                tblTreeFPP.getColumnExt(1).addHighlighter(new org.jdesktop.swingx.decorator.AlignmentHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,4));
                tblTreeFPP.getColumnExt(2).addHighlighter(new org.jdesktop.swingx.decorator.AlignmentHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,4));
                tblTreeFPP.getColumnExt(3).addHighlighter(new org.jdesktop.swingx.decorator.AlignmentHighlighter(org.jdesktop.swingx.decorator.HighlightPredicate.ALWAYS,4));
                classes.utils.clearTable(tblDetailsReten); 
        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAllotment").log(java.util.logging.Level.SEVERE, null, ex);
                }
        } 
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
        
    
    private  java.util.Calendar DateToCalendar(java.util.Date date){ 
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }    
    
    private String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
        case 11:
        case 12:
        case 13:
            return "th";
        default:
            return sufixes[i % 10];

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClass;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSaveAllot;
    private javax.swing.JButton btnSaveUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboObjectEx;
    private datechooser.beans.DateChooserCombo chDatePrepAllotment;
    private javax.swing.JRadioButtonMenuItem eighty;
    private javax.swing.JRadioButtonMenuItem eightyfive;
    private javax.swing.JRadioButtonMenuItem fifteen;
    private javax.swing.JRadioButtonMenuItem fifty;
    private javax.swing.JRadioButtonMenuItem fiftyfive;
    private javax.swing.JRadioButtonMenuItem five;
    private javax.swing.JRadioButtonMenuItem forty;
    private javax.swing.JRadioButtonMenuItem fortyfive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblControlNo;
    private javax.swing.JLabel lblMode;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTotalFPPSum;
    private javax.swing.JRadioButtonMenuItem ninety;
    private javax.swing.JRadioButtonMenuItem ninetyfive;
    private javax.swing.JRadioButtonMenuItem onehund;
    private javax.swing.JPopupMenu popupReten;
    private javax.swing.JRadioButtonMenuItem seventy;
    private javax.swing.JRadioButtonMenuItem seventyfive;
    private javax.swing.JRadioButtonMenuItem sixty;
    private javax.swing.JRadioButtonMenuItem sixtyfive;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTabbedPane tabPaneAllotment;
    private javax.swing.JTable tblDetailsAllot;
    private javax.swing.JTable tblDetailsReten;
    private javax.swing.JTable tblDistribution;
    private javax.swing.JTable tblFooterAllot;
    private javax.swing.JTable tblFooterDistrib;
    private javax.swing.JTable tblFooterReten;
    private javax.swing.JTable tblRecords;
    private org.jdesktop.swingx.JXTreeTable tblTreeFPP;
    private javax.swing.JRadioButtonMenuItem ten;
    private javax.swing.JRadioButtonMenuItem thirty;
    private javax.swing.JRadioButtonMenuItem thirtyfive;
    private javax.swing.JTree treeOffice;
    private javax.swing.JRadioButtonMenuItem twenty;
    private javax.swing.JRadioButtonMenuItem twentyfive;
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
    
    
    class SyncListener implements TableColumnModelListener
{
    JTable jtable_data;
    JTable jtable_footer;

    public SyncListener(JTable main, JTable footer)
    {
        jtable_data = main;
        jtable_footer = footer;

        DefaultTableColumnModel dtcm = (DefaultTableColumnModel)jtable_data.getColumnModel();

        dtcm.removeColumnModelListener(dtcm.getColumnModelListeners()[1]);
        dtcm.addColumnModelListener(this);
    }

    public void columnMarginChanged(ChangeEvent changeEvent)
    {
        for (int column = 0; column < jtable_data.getColumnCount(); column++)
        {
            jtable_footer.getColumnModel().getColumn(column).setWidth(jtable_data.getColumnModel().getColumn(column).getWidth());
        }

        jtable_footer.repaint();
    }

    public void columnAdded(TableColumnModelEvent e){}
    public void columnMoved(TableColumnModelEvent e){}
    public void columnRemoved(TableColumnModelEvent e){}
    public void columnSelectionChanged(ListSelectionEvent e){}
}

}
