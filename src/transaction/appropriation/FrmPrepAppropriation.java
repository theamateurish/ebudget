/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction.appropriation;

import classes.MsgBox;
import javax.swing.JOptionPane;

/**
 *
 * @author theamateurish
 */
public class FrmPrepAppropriation extends javax.swing.JInternalFrame {
    private java.util.ArrayList <String>arrObjID=new java.util.ArrayList<>();
    private java.util.ArrayList <String>arrProgID=new java.util.ArrayList<>();
    private java.util.ArrayList <Integer>arrLegalID=new java.util.ArrayList<>();
    private java.util.ArrayList <String>arrFundID=new java.util.ArrayList<>();
    private java.util.ArrayList <Integer>arrAllotID=new java.util.ArrayList<>();
    private String[] header = { "FPP","PROGRAMS","SUB-OFFICE","OFFICE","LEVEL","CENTRE_UID"};
    
    private final boolean NUMERIC = true;    
    private org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode root;
    private org.jdesktop.swingx.treetable.DefaultTreeTableModel model;
    private Integer service,ofis,subofis,fundID;
    private String ofisname,subofisname,fppcode,controlID;
    private Object[][] tempRespCenter=new Object[4][8];
    private Boolean editMode=false;
    private Boolean noSubOfis=false;
    
    /**
     * Creates new form iFrmPreparation
     */
    public FrmPrepAppropriation() {
        initComponents();  
        chDatePrepApprop.setSelectedDate(DateToCalendar(new connect.SystemOptions().ServerDate()));
    }       
    
    private void selectRecordsFrom_Fund(){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                cboFund.removeAllItems();
                arrFundID.clear();
		String selectSQL = "SELECT centre_uid,description FROM buds.fund_central "
                                +"WHERE array_length(regexp_split_to_array(centre_uid, '-'), 1)=? "
                                +"ORDER  BY centre_uid";
		try {
			dbc = new connect.DBConnection();
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setInt(1,1);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        arrFundID.add("");
                        cboFund.addItem("");
			while (rs.next()) {
                                arrFundID.add(rs.getString("centre_uid"));
				cboFund.addItem(rs.getString("description"));
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }    
   
    
    private void selectRecordsFrom_Legal(){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                cboLegalBasis.removeAllItems();
                arrLegalID.clear();
		String selectSQL = "SELECT * FROM buds.ordinances "
                                    + "WHERE legal_year=? ORDER BY legal_desc";

		try {
			dbc = new connect.DBConnection();
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setInt(1, Integer.valueOf(org.joda.time.format.DateTimeFormat.forPattern("yyyy").print(new org.joda.time.LocalDate(chDatePrepApprop.getSelectedDate()))));
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        arrLegalID.add(0);
                        cboLegalBasis.addItem("");
			while (rs.next()) {
                            arrLegalID.add(rs.getInt("legal_uid"));
                            cboLegalBasis.addItem(rs.getString("legal_desc"));
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void selectRecordsFrom_Accounts(){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                cboClass.removeAllItems();
                arrAllotID.clear();
		String selectSQL = "SELECT * FROM buds.allot_class ORDER BY class_code";

		try {
			dbc = new connect.DBConnection();
			preparedStatement = dbc.prepareStatement(selectSQL);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        arrAllotID.add(0);
                        cboClass.addItem("");
			while (rs.next()) {
                                arrAllotID.add(rs.getInt("class_code"));
				cboClass.addItem(rs.getString("class_desc"));
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
   
    
    private void total_FPP(){
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
                        preparedStatement.setString(1, arrFundID.get(cboFund.getSelectedIndex()));
                        preparedStatement.setInt(2, arrLegalID.get(cboLegalBasis.getSelectedIndex()));
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void prep_appropriation(String fppcode){
                setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                String selectSQL="SELECT * FROM temp.prep_appropriation t1 "
                                        +"INNER JOIN acct.accounts t2 ON t1.obj_expend_code=t2.coding "
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
                        preparedStatement.setInt(3, arrLegalID.get(cboLegalBasis.getSelectedIndex()));
			java.sql.ResultSet rs = preparedStatement.executeQuery();
                        classes.utils.clearTable(tblDetails);
                        int R=0;
			while(rs.next()) {                                       
                            if (R >=tblDetails.getRowCount()){
                                            ((javax.swing.table.DefaultTableModel)tblDetails.getModel()).addRow(new Object[] {null, null, null, null, null});
                            }               
                            tblDetails.setValueAt(rs.getString("titles"), R, 0);
                            tblDetails.setValueAt(rs.getString("coding"), R, 1);  
                            tblDetails.setValueAt(rs.getDouble("amount"), R, 2);
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
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

        treeOffice = new javax.swing.JTree();
        cboLegalBasis = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        splitPane = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        cboFund = new javax.swing.JComboBox();
        cboOffice = new classes.TreeComboBox(treeOffice);
        cboProgram = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTreeFPP = new org.jdesktop.swingx.JXTreeTable();
        btnAddFpp = new javax.swing.JButton();
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
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cboClass = new javax.swing.JComboBox();
        btnAddClass = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetails = new javax.swing.JTable();
        lblAmountCaption = new javax.swing.JLabel();
        lblTotalFPPDetail = new javax.swing.JLabel();
        btnAddObjectExp = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnPost = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        chDatePrepApprop = new datechooser.beans.DateChooserCombo();
        jToolBar1 = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSaveUpdate = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        lblMode = new javax.swing.JLabel();
        lblControlNo = new javax.swing.JLabel();

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeOffice.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));

        setClosable(true);
        setMaximizable(true);
        setTitle("APPROPRIATION-Preparation");
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

        cboLegalBasis.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboLegalBasis.setEnabled(false);
        cboLegalBasis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLegalBasisItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setText("Legal Basis");

        cboFund.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboFund.setEnabled(false);
        cboFund.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboFundItemStateChanged(evt);
            }
        });

        cboOffice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboOffice.setEnabled(false);
        cboOffice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboOfficeItemStateChanged(evt);
            }
        });
        cboOffice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOfficeActionPerformed(evt);
            }
        });

        cboProgram.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboProgram.setEnabled(false);
        cboProgram.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboProgramItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Fund Account");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Office");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Projects/Program");

        model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(root,java.util.Arrays.asList(header));
        tblTreeFPP=new org.jdesktop.swingx.JXTreeTable(model);
        tblTreeFPP.setShowGrid(true, true);
        tblTreeFPP.getColumnExt(5).setVisible(false);
        tblTreeFPP.getColumnExt(4).setVisible(false);
        //tblTreeFPP.setColumnControlVisible(true);

        tblTreeFPP.packAll();
        tblTreeFPP.setEditable(false);
        tblTreeFPP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTreeFPPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTreeFPP);

        btnAddFpp.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnAddFpp.setText("Add");
        btnAddFpp.setBorderPainted(false);
        btnAddFpp.setEnabled(false);
        btnAddFpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFppActionPerformed(evt);
            }
        });

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
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnDelete.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setBorderPainted(false);
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboOffice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboFund, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboProgram, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddFpp))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddFpp)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalFPPSum, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        splitPane.setLeftComponent(jPanel1);

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setText("Classes");

        cboClass.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboClass.setEnabled(false);
        cboClass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboClassItemStateChanged(evt);
            }
        });

        btnAddClass.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnAddClass.setText("Add to FPP");
        btnAddClass.setEnabled(false);
        btnAddClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClassActionPerformed(evt);
            }
        });

        tblDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Object of Expenditures", "Code", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            boolean[] locked = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                String code=(tblDetails.getValueAt(rowIndex,1)==null?"":tblDetails.getValueAt(rowIndex, 1).toString());
                if(!code.equals("")){
                    return canEdit [columnIndex];
                }else{
                    return locked [columnIndex];
                }
            }
        });
        tblDetails.getColumnModel().getColumn(2).setCellRenderer(
            new DecimalFormatRenderer() );
        tblDetails.getModel().addTableModelListener(
            new javax.swing.event.TableModelListener()
            {
                public void tableChanged(javax.swing.event.TableModelEvent evt)
                {
                    double total=0.0;
                    btnSaveUpdate.setEnabled(false);
                    for(int row=0;row<tblDetails.getRowCount();row++){
                        lblTotalFPPDetail.setText("₱ " + new java.text.DecimalFormat("#,##0.00").format(
                            total+= Double.parseDouble(String.valueOf((tblDetails.getValueAt(row, 2)==null?0.0:tblDetails.getValueAt(row, 2)))
                            )));
                        }
                        if(!lblMode.getText().equals("Read Only...")){
                            btnPost.setEnabled(total>0);
                        }else{
                            btnPost.setEnabled(false);
                        }
                    }
                });
                tblDetails.putClientProperty("terminateEditOnFocusLost", true);
                tblDetails.getTableHeader().setReorderingAllowed(false);
                tblDetails.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        tblDetailsMouseClicked(evt);
                    }
                });
                jScrollPane1.setViewportView(tblDetails);
                tblDetails.setColumnSelectionAllowed(true);
                tblDetails.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                tblDetails.setRowHeight(25);
                tblDetails.getTableHeader().setPreferredSize(new java.awt.Dimension(150,40));

                lblAmountCaption.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                lblAmountCaption.setText("Total Amount:");

                lblTotalFPPDetail.setForeground(new java.awt.Color(0, 51, 153));
                lblTotalFPPDetail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblTotalFPPDetail.setText("0.00");

                btnAddObjectExp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                btnAddObjectExp.setText("Add");
                btnAddObjectExp.setBorderPainted(false);
                btnAddObjectExp.setEnabled(false);
                btnAddObjectExp.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnAddObjectExpActionPerformed(evt);
                    }
                });

                btnRemove.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                btnRemove.setText("Remove");
                btnRemove.setBorderPainted(false);
                btnRemove.setEnabled(false);
                btnRemove.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnRemoveActionPerformed(evt);
                    }
                });

                btnPost.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                btnPost.setText("Post Total Amount to FPP");
                btnPost.setBorderPainted(false);
                btnPost.setEnabled(false);
                btnPost.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnPostActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAddObjectExp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(btnPost))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAddClass)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblAmountCaption)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalFPPDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                );
                jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddClass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAmountCaption)
                            .addComponent(lblTotalFPPDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddObjectExp)
                            .addComponent(btnRemove)
                            .addComponent(btnPost))
                        .addContainerGap())
                );

                splitPane.setRightComponent(jPanel2);

                jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
                jLabel7.setText("Date");

                chDatePrepApprop.setLocale(new java.util.Locale("en", "PH", ""));
                chDatePrepApprop.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
                    public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                        chDatePrepAppropOnSelectionChange(evt);
                    }
                });

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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1)
                                .addGap(65, 65, 65)
                                .addComponent(cboLegalBasis, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chDatePrepApprop, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblControlNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboLegalBasis)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(lblControlNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chDatePrepApprop, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(splitPane)
                        .addContainerGap())
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void btnAddObjectExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObjectExpActionPerformed
        String[]code=fppcode.toString().split("-");
        java.awt.Image img=new javax.swing.ImageIcon(getClass().getResource("/images/open.png")).getImage();
        transaction.appropriation.DlgListExpenditure dialog=new transaction.appropriation.DlgListExpenditure(new budget.MainMDI(), true,code[5]);
        dialog.setTitle("Object of Expenditures");
        dialog.setIconImage(img);    
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        if(tblDetails.getRowCount()<=0){
            for(int i=0;i<dialog.getArrObjExpDesc().size();i++){
                String[] splitted=dialog.getArrObjExpDesc().get(i).toString().split("~/~");
                if (i >=tblDetails.getRowCount()){
                    ((javax.swing.table.DefaultTableModel)tblDetails.getModel()).addRow(new Object[] {null, null, null, null, null});
                }
                tblDetails.setValueAt(splitted[1].toString(), i, 0);
                tblDetails.setValueAt(splitted[0].toString(), i, 1);            
            }
        }else{
            int existingRows=tblDetails.getRowCount();
            int escape=0;
            for(int i=existingRows;i< existingRows + dialog.getArrObjExpDesc().size();i++){
                String[] splitted=dialog.getArrObjExpDesc().get(i-existingRows).toString().split("~/~");    
                boolean found=false;                
                for(int j=0;j<tblDetails.getRowCount();j++) {
                    if(String.valueOf(tblDetails.getValueAt(j, 0)).equals(splitted[1])){
                        found=true;
                        escape=escape+1;
                        break;
                    }    
                }
                if(!found){
                    ((javax.swing.table.DefaultTableModel)tblDetails.getModel()).addRow(new Object[] {null, null, null, null, null});                
                    tblDetails.setValueAt(splitted[1].toString(), i-escape, 0);
                    tblDetails.setValueAt(splitted[0].toString(), i-escape, 1); 
                }
            }
        }
    }//GEN-LAST:event_btnAddObjectExpActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int[] selectedRows = tblDetails.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                ((javax.swing.table.DefaultTableModel)tblDetails.getModel()).removeRow(selectedRows[i]);
            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        long compares = new connect.SystemOptions().ServerDate().getTime();
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            String[]code=fppcode.split("-"); 
            if (tblDetails.getModel().getRowCount() > 0) {
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDetails.getModel();
                for (short xyz = 0; xyz < tblDetails.getModel().getRowCount(); xyz++) {
                    if(model.getValueAt(xyz, 2)!=null){
                        if(Double.valueOf(model.getValueAt(xyz, 2).toString())>0.0){
                            dbase.SQLExecute saver = new dbase.SQLExecute("temp.prep_appropriation");
                            saver.FieldName("fpp_code", !NUMERIC, enums.Take.InsertUpdate,true, code[0]+"-"+code[1]+"-"+code[2]+"-"+code[3]+"-"+code[4]);
                            saver.FieldName("class_code", NUMERIC, enums.Take.InsertUpdate,true, code[5]);
                            saver.FieldName("obj_expend_code",  !NUMERIC, enums.Take.InsertUpdate, true,(String) (model.getValueAt(xyz, 1) == null?"":model.getValueAt(xyz, 1)));
                            saver.FieldName("amount",   NUMERIC, enums.Take.InsertUpdate,  Double.valueOf(model.getValueAt(xyz, 2).toString()));
                            saver.FieldName("legal_base", NUMERIC, enums.Take.InsertUpdate, true,arrLegalID.get(cboLegalBasis.getSelectedIndex()));
                            saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate, org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(new org.joda.time.LocalDate(chDatePrepApprop.getSelectedDate()))
                                    + new java.text.SimpleDateFormat(" HH:mm:ss.SSSz").format(new connect.SystemOptions().ServerDate()));
                            saver.FieldName("compare_id", NUMERIC, enums.Take.InsertUpdate, compares);
                            try {
                                success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                                if (success == 0) {
                                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                                }                        
                            } catch (Exception ex) {
                                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                                return;
                            }
                        }
                    }
                }
                dbc.executeUpdate("DELETE FROM temp.prep_appropriation WHERE (fpp_code||'-'||class_code = '" + fppcode + "') AND (legal_base =" + arrLegalID.get(cboLegalBasis.getSelectedIndex()) + ") AND (compare_id <> " + compares + ")");
            }else{
                dbc.executeUpdate("DELETE FROM temp.prep_appropriation WHERE (fpp_code||'-'||class_code = '" + fppcode + "') AND (legal_base =" + arrLegalID.get(cboLegalBasis.getSelectedIndex()) + ")");
            }             
            dbc.commit();       
            btnPost.setEnabled(false);
            classes.utils.clearTable(tblDetails);
            lblTotalFPPDetail.setText("0.00");
            btnSaveUpdate.setEnabled(true);            
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                    if(editMode){
                        setEditFPP(arrLegalID.get(cboLegalBasis.getSelectedIndex()), fundID);
                    }else{
                        fpp_tree();  
                    }
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPostActionPerformed
    
    private void cboFundItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboFundItemStateChanged
        
        cboLegalBasis.setEnabled(false);
        btnAddObjectExp.setEnabled(false);
        btnAddFpp.setEnabled(cboFund.getSelectedIndex()>0);
        classes.utils.clearTable(tblDetails); 
        lblTotalFPPDetail.setText("0.00");
        if(cboFund.getSelectedIndex()>0){            
            if(isFundExist(arrLegalID.get(cboLegalBasis.getSelectedIndex()),
                Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex()))) &  editMode==false & lblMode.getText().equals("")){
                classes.MsgBox.showWarning(this, cboFund.getSelectedItem()+ " based on \n"+ cboLegalBasis.getSelectedItem() + "\nhas been already prepared!", "NEW ENTRY");
                cboLegalBasis.setSelectedIndex(0);
                lblStatus.setText("");
                return;
            }
            tblTreeFPP.setTreeTableModel(model);
            office_tree(arrFundID.get(cboFund.getSelectedIndex()).toString());
            tempRespCenter[0][0]=arrFundID.get(cboFund.getSelectedIndex());             //center_uid
            tempRespCenter[0][1]=arrFundID.get(cboFund.getSelectedIndex());             //funds_id
            tempRespCenter[0][2]=0;                                                     //services
            tempRespCenter[0][3]=0;                                                     //resp_id
            tempRespCenter[0][4]=0;                                                     //resp_sub
            tempRespCenter[0][5]=0;                                                     //programs
            tempRespCenter[0][6]=cboFund.getSelectedItem();                             //description
            tempRespCenter[0][7]=0;                                                     //parent_id
            cboClass.setEnabled(false);   
            cboOffice.setEnabled(true);
            fpp_tree();
        }else{
            cboOffice.removeAllItems();
            cboOffice.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_cboFundItemStateChanged

    private void cboOfficeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOfficeActionPerformed
        javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode) treeOffice.getLastSelectedPathComponent();
        String[]parentData=null;
        if (node == null){ return;}
        
        
        Object nodeInfo = node.getUserObject();         
        String[] fpp= nodeInfo.toString().split("~");
        String[] ofises=fpp[0].toString().split("-");
        service=Integer.valueOf(ofises[0].toString());
        ofis=Integer.valueOf(ofises[1].toString());
        subofis=Integer.valueOf((ofises.length<=2?"0":ofises[2].toString()));        
        subofisname=fpp[1].toString();
        if(node.isLeaf()){
            Object parentNode=node.getParent();
            parentData=parentNode.toString().split("~");   
            ofisname=(parentData.length>1?parentData[1].toString():subofisname);
        }
        
        
        if(ofises.length==2){
            noSubOfis=true;
            tempRespCenter[1][0]=arrFundID.get(cboFund.getSelectedIndex()) +"-"+String.format("%02d",service)+"-"+ofis;             //center_uid
            tempRespCenter[1][1]=arrFundID.get(cboFund.getSelectedIndex());             //funds_id
            tempRespCenter[1][2]=service;                                               //services
            tempRespCenter[1][3]=ofis;                                                  //resp_id
            tempRespCenter[1][4]=0;                                                     //resp_sub
            tempRespCenter[1][5]=0;                                                     //programs
            tempRespCenter[1][6]=ofisname;                                              //description
            tempRespCenter[1][7]=tempRespCenter[0][0];                                  //parent_id
            
        }else if(ofises.length>2){
            noSubOfis=false;
            tempRespCenter[1][0]=arrFundID.get(cboFund.getSelectedIndex())+"-"+String.format("%02d",service)+"-"+ofis;             //center_uid
            tempRespCenter[1][1]=arrFundID.get(cboFund.getSelectedIndex());             //funds_id
            tempRespCenter[1][2]=service;                                               //services
            tempRespCenter[1][3]=ofis;                                                  //resp_id
            tempRespCenter[1][4]=0;                                                     //resp_sub
            tempRespCenter[1][5]=0;                                                     //programs
            tempRespCenter[1][6]=ofisname;                                              //description
            tempRespCenter[1][7]=tempRespCenter[0][0];                                  //parent_id
                       
            tempRespCenter[2][0]=arrFundID.get(cboFund.getSelectedIndex())+"-"+String.format("%02d",service)+"-"+ofis + "-"+String.format("%02d",subofis);  //center_uid
            tempRespCenter[2][1]=arrFundID.get(cboFund.getSelectedIndex());             //funds_id
            tempRespCenter[2][2]=service;                                               //services
            tempRespCenter[2][3]=ofis;                                                  //resp_id
            tempRespCenter[2][4]=subofis;                                               //resp_sub
            tempRespCenter[2][5]=0;                                                     //programs
            tempRespCenter[2][6]=subofisname;                                           //description
            tempRespCenter[2][7]=tempRespCenter[1][0];                                  //parent_id
          
        }
        selectPrograms(Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex())),service, ofis, subofis);
    }//GEN-LAST:event_cboOfficeActionPerformed

    private void btnAddFppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFppActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            for(int i=0;i<tempRespCenter.length;i++){
                if(tempRespCenter[i][0]==null){i++;}
                dbase.SQLExecute saver = new dbase.SQLExecute("temp.prep_respcenter");
                saver.FieldName("centre_uid",  !NUMERIC, enums.Take.InsertUpdate, true,tempRespCenter[i][0]);
                saver.FieldName("funds_id",   NUMERIC, enums.Take.InsertUpdate,  tempRespCenter[i][1]);
                saver.FieldName("services",   NUMERIC, enums.Take.InsertUpdate,  tempRespCenter[i][2]);
                saver.FieldName("resp_id",   NUMERIC, enums.Take.InsertUpdate, tempRespCenter[i][3]);
                saver.FieldName("resp_sub",   NUMERIC, enums.Take.InsertUpdate, tempRespCenter[i][4]);
                saver.FieldName("programs",   NUMERIC, enums.Take.InsertUpdate,  tempRespCenter[i][5]);
                saver.FieldName("description",   !NUMERIC, enums.Take.InsertUpdate, tempRespCenter[i][6].toString().replace("'", "''"));
                saver.FieldName("parent_id",   !NUMERIC, enums.Take.InsertUpdate, tempRespCenter[i][7]);
                saver.FieldName("legal_base", NUMERIC, enums.Take.InsertUpdate, true,arrLegalID.get(cboLegalBasis.getSelectedIndex()));
                         
                try {
                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                    if (success == 0) {
                        success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                    }                        
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                    return;
                }     
            }
            dbc.commit();    
            lblStatus.setText("Partial Entry");
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                    if(editMode){
                        setEditFPP(arrLegalID.get(cboLegalBasis.getSelectedIndex()), fundID);
                    }else{
                        fpp_tree();  
                    }
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnAddFppActionPerformed

    private void cboProgramItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboProgramItemStateChanged
        if(cboProgram.getSelectedIndex()>=0){    
            tempRespCenter[3][0]=arrFundID.get(cboFund.getSelectedIndex())+"-"+String.format("%02d",service)+"-"+ofis + "-"+String.format("%02d",subofis) +"-"+String.format("%03d",Integer.valueOf(arrProgID.get(cboProgram.getSelectedIndex())));             //center_uid
            tempRespCenter[3][1]=arrFundID.get(cboFund.getSelectedIndex());             //funds_id
            tempRespCenter[3][2]=service;                                               //services
            tempRespCenter[3][3]=ofis;                                                  //resp_id
            tempRespCenter[3][4]=subofis;                                               //resp_sub
            tempRespCenter[3][5]=arrProgID.get(cboProgram.getSelectedIndex());          //programs
            tempRespCenter[3][6]=cboProgram.getSelectedItem();                          //description
            tempRespCenter[3][7]=(noSubOfis?tempRespCenter[1][0]:tempRespCenter[2][0]);                                  //parent_id
            btnAddFpp.setEnabled(true);
        }
    }//GEN-LAST:event_cboProgramItemStateChanged

    private void tblTreeFPPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTreeFPPMouseClicked
         
            final int rowIndex = tblTreeFPP.getSelectedRow();
            if (rowIndex < 0) {
                return;
            }
            if(!lblMode.getText().equals("Read Only...")){              
                btnDelete.setEnabled(evt.getClickCount()==2);
                btnRemove.setEnabled(false);
                classes.utils.clearTable(tblDetails); 
                tblDetails.setEnabled(true);
                lblAmountCaption.setText("");
                lblTotalFPPDetail.setText("0.00");
                final org.jdesktop.swingx.treetable.TreeTableNode selectedNode = (org.jdesktop.swingx.treetable.TreeTableNode)tblTreeFPP.getPathForRow(rowIndex).getLastPathComponent();  
                Object[] node=(Object[]) selectedNode.getUserObject();
                cboClass.setEnabled(!node[4].toString().equals("0")& node[5].toString().split("-").length==5); 
                cboClass.setSelectedIndex(0);
                btnAddObjectExp.setEnabled(node[5].toString().split("-").length>5);
                fppcode=node[5].toString();
                String[]code=node[5].toString().split("-"); 
                String[]desc=node[0].toString().split("~");                 
                if(node[5].toString().split("-").length>5){                    
                    cboClass.setSelectedItem(desc[1]);
                    btnAddClass.setEnabled(false); 
                    prep_appropriation(fppcode);
                    lblAmountCaption.setText("Total " + desc[1].toString());
                }               
            }else{
                btnDelete.setEnabled(false);
                btnRemove.setEnabled(false);
                classes.utils.clearTable(tblDetails);  
                tblDetails.setEnabled(false);
                lblAmountCaption.setText("");
                lblTotalFPPDetail.setText("0.00");
                final org.jdesktop.swingx.treetable.TreeTableNode selectedNode = (org.jdesktop.swingx.treetable.TreeTableNode)tblTreeFPP.getPathForRow(rowIndex).getLastPathComponent();  
                Object[] node=(Object[]) selectedNode.getUserObject();
                cboClass.setEnabled(false); 
                cboClass.setSelectedIndex(0);
                btnAddObjectExp.setEnabled(false);
                fppcode=node[5].toString();
                String[]code=node[5].toString().split("-"); 
                String[]desc=node[0].toString().split("~"); 
                if(node[5].toString().split("-").length>5){
                    cboClass.setSelectedItem(desc[1]);
                    btnAddClass.setEnabled(false);                      
                    prep_appropriation(fppcode);
                    lblAmountCaption.setText("Total " + desc[1].toString());
                } 
            }
    }//GEN-LAST:event_tblTreeFPPMouseClicked

    private void btnAddClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClassActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            String[] codeSplitted=fppcode.split("-");
            dbase.SQLExecute saver = new dbase.SQLExecute("temp.prep_respcenter");
            saver.FieldName("centre_uid",  !NUMERIC, enums.Take.InsertUpdate, true,fppcode + "-" + arrAllotID.get(cboClass.getSelectedIndex()));
            saver.FieldName("funds_id",   NUMERIC, enums.Take.InsertUpdate,  Integer.valueOf(codeSplitted[0]));
            saver.FieldName("services",   NUMERIC, enums.Take.InsertUpdate,  Integer.valueOf(codeSplitted[1]));
            saver.FieldName("resp_id",   NUMERIC, enums.Take.InsertUpdate, Integer.valueOf(codeSplitted[2]));
            saver.FieldName("resp_sub",   NUMERIC, enums.Take.InsertUpdate, Integer.valueOf(codeSplitted[3]));
            saver.FieldName("programs",   NUMERIC, enums.Take.InsertUpdate,  Integer.valueOf(codeSplitted[4]));
            saver.FieldName("classes",   NUMERIC, enums.Take.InsertUpdate,  arrAllotID.get(cboClass.getSelectedIndex()));
            saver.FieldName("description",   !NUMERIC, enums.Take.InsertUpdate, cboClass.getSelectedItem());
            saver.FieldName("parent_id",   !NUMERIC, enums.Take.InsertUpdate, fppcode);
            saver.FieldName("legal_base", NUMERIC, enums.Take.InsertUpdate, true,arrLegalID.get(cboLegalBasis.getSelectedIndex()));
            if(editMode){
//                saver.FieldName("control_uid",  !NUMERIC, enums.Take.InsertUpdate,controlID);            
//                saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate, (chDatePrepApprop==null?chDatePrepApprop.getSelectedDate():org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(new org.joda.time.LocalDate(chDatePrepApprop.getSelectedDate()))));
            }
            
            try {
                success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                if (success == 0) {
                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                }                        
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                return;
            }     
          
            dbc.commit();    
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                    if(editMode){
                        setEditFPP(arrLegalID.get(cboLegalBasis.getSelectedIndex()), fundID);
                    }else{
                        fpp_tree();  
                    }
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnAddClassActionPerformed

    private void cboClassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboClassItemStateChanged
        btnAddClass.setEnabled(cboClass.getSelectedIndex()>0);       
    }//GEN-LAST:event_cboClassItemStateChanged

    private void tblDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailsMouseClicked
        btnRemove.setEnabled(!lblMode.getText().equals("Read Only..."));        
    }//GEN-LAST:event_tblDetailsMouseClicked

    private void cboLegalBasisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLegalBasisItemStateChanged
        
        if(cboLegalBasis.getSelectedIndex()>0){
            selectRecordsFrom_Fund();
            cboFund.setEnabled(true);
            chDatePrepApprop.setEnabled(false);
            
        }else{
            cboFund.removeAllItems();
            cboFund.setEnabled(false); 
            model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
            tblTreeFPP.setTreeTableModel(model);
            cboProgram.removeAllItems();
            cboProgram.setEnabled(false);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_cboLegalBasisItemStateChanged

    private void cboOfficeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboOfficeItemStateChanged
        if(cboOffice.getSelectedIndex()>0){            
            cboProgram.setEnabled(true);
            
        }else{
            cboProgram.setEnabled(false);
        }
    }//GEN-LAST:event_cboOfficeItemStateChanged

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
     
    }//GEN-LAST:event_formInternalFrameActivated

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        selectRecordsFrom_Legal(); 
        selectRecordsFrom_Accounts(); 
        btnSaveUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnRemove.setEnabled(false);
        cboLegalBasis.setEnabled(true);
        chDatePrepApprop.setEnabled(true);
        cboFund.removeAllItems();
        cboOffice.removeAllItems();
        cboProgram.removeAllItems();
        model = new org.jdesktop.swingx.treetable.DefaultTreeTableModel(null,java.util.Arrays.asList(header)); 
        tblTreeFPP.setTreeTableModel(model);
        tblTreeFPP.getColumnExt(5).setVisible(false);
        tblTreeFPP.getColumnExt(4).setVisible(false);
        lblTotalFPPSum.setText("0.00");
        lblMode.setText("");
        lblStatus.setText("");
        lblControlNo.setText("");
        editMode=false;
        fundID=0;
        controlID="";
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        java.awt.Image img=new javax.swing.ImageIcon(getClass().getResource("/images/open.png")).getImage();
        transaction.appropriation.DlgBugdetAppropriation dialog=new transaction.appropriation.DlgBugdetAppropriation(new budget.MainMDI(), true);
        dialog.setTitle("SEARCH");
        dialog.setIconImage(img);    
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);         
        if(dialog.isEditMode()){
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            lblMode.setText("");
            editMode=dialog.isEditMode();
            fundID=dialog.getFundID();
            controlID=dialog.getControlID();
            selectRecordsFrom_Legal();
            selectRecordsFrom_Fund();
            selectRecordsFrom_Accounts();
            cboLegalBasis.setSelectedItem(dialog.getLegalDesc());
            cboFund.setSelectedItem(dialog.getFundDesc());
            setEditFPP(dialog.getLegalID(),fundID);
            cboLegalBasis.setEnabled(false);
            cboFund.setEnabled(false);
            lblMode.setText("CAUTION!..Edit Mode");
            lblControlNo.setText("APPROPRIATION#: "+controlID);
            btnAddFpp.setEnabled(false);
            btnSaveUpdate.setEnabled(true);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }else if(dialog.isReadOnly()){
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
            lblMode.setText("Read Only...");
            editMode=dialog.isEditMode();
            fundID=dialog.getFundID();
            controlID=dialog.getControlID();
            selectRecordsFrom_Legal();
            selectRecordsFrom_Fund();
            cboLegalBasis.setSelectedItem(dialog.getLegalDesc());
            cboFund.setSelectedItem(dialog.getFundDesc());
            setEditFPP(dialog.getLegalID(),fundID);
            cboLegalBasis.setEnabled(false);
            cboFund.setEnabled(false);
            btnAddFpp.setEnabled(false);   
            cboOffice.setEnabled(false);
            btnSaveUpdate.setEnabled(false);
            lblControlNo.setText("APPROPRIATION#: "+controlID);            
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }else{
            btnNewActionPerformed(evt);
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveUpdateActionPerformed
        if(tblTreeFPP.getTreeTableModel().getRoot()==null){
            classes.MsgBox.showWarning(this, "No FPP!", "SAVING RECORD");
            return;
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT seq_id FROM buds.appropriation_status ORDER BY seq_id DESC LIMIT 1";
        try {
            int success = 0;
            int sequence=0;
            dbc = new connect.DBConnection();
            preparedStatement = dbc.prepareStatement(selectSQL);
            java.sql.ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                sequence=rs.getInt(1) ;
            }            
           
            dbc.setAutoCommit(false);           

            dbase.SQLExecute saver = new dbase.SQLExecute("buds.appropriation_status");
             
            saver.FieldName("legal_base", NUMERIC, enums.Take.InsertUpdate, true,Integer.valueOf(arrLegalID.get(cboLegalBasis.getSelectedIndex())));
            saver.FieldName("fund_id", NUMERIC, enums.Take.InsertUpdate, true,Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex())));
            saver.FieldName("date_prepared",   !NUMERIC, enums.Take.InsertUpdate, org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").print(new org.joda.time.LocalDate(chDatePrepApprop.getSelectedDate()))
                                    + new java.text.SimpleDateFormat(" HH:mm:ss.SSSz").format(new connect.SystemOptions().ServerDate()));
            saver.FieldName("status",  !NUMERIC, enums.Take.InsertUpdate,"For Review");
            if(!editMode){
                saver.FieldName("control_id",  !NUMERIC, enums.Take.InsertUpdate,"AB"+ org.joda.time.format.DateTimeFormat.forPattern("yyyy").print(new org.joda.time.LocalDate(chDatePrepApprop.getSelectedDate()))+"-"+String.format("%03d",sequence + 1 ));            
                saver.FieldName("seq_id",  NUMERIC, enums.Take.InsertUpdate,sequence + 1 );
            }
            
            try {
                success = dbc.executeUpdate(saver.Perform(enums.Fire.doUpdate));
                if (success == 0) {
                    success = dbc.executeUpdate(saver.Perform(enums.Fire.doInsert));
                }  
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                return;
            }                                
            
            if (success > 0){
                dbc.commit();                 
                javax.swing.JOptionPane.showMessageDialog(this, "Record saved successfully.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                btnNewActionPerformed(evt);
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "No record to saved.", "SAVE/UPDATE RECORD", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();                    
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnSaveUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        try {
            int success = 0;
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            dbc.executeUpdate("DELETE FROM temp.prep_respcenter WHERE array_length(regexp_split_to_array(centre_uid, '-'), 1)>="+fppcode.toString().split("-").length
                    +" AND centre_uid like '"+fppcode
                    +"%' AND (legal_base=" + arrLegalID.get(cboLegalBasis.getSelectedIndex()) + ")");
            
            dbc.commit();  
            btnDelete.setEnabled(false);
            classes.utils.clearTable(tblDetails);
            lblTotalFPPDetail.setText("0.00");
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception exp) {
                javax.swing.JOptionPane.showMessageDialog(this, exp.getMessage(), "ROLL BACKING", javax.swing.JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, exp);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "SAVE/UPDATE RECORD", javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            if (dbc != null){ 
                try {
                    dbc.close();
                    if(editMode){
                        setEditFPP(arrLegalID.get(cboLegalBasis.getSelectedIndex()), fundID);
                    }else{
                        fpp_tree();  
                    }
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void chDatePrepAppropOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_chDatePrepAppropOnSelectionChange
        btnNewActionPerformed(null);
    }//GEN-LAST:event_chDatePrepAppropOnSelectionChange
     
       
    private void fpp_tree() {    
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement;  
        Object[][] treevalue;
        if(cboFund.getSelectedIndex()<0){
            return;
        }

        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement("SELECT * FROM temp.prep_respcenter t1 "
                                                    +"FULL OUTER JOIN (SELECT fpp_code,legal_base,amount "
                                                      +"FROM temp.compute_amount() WHERE legal_base="+arrLegalID.get(cboLegalBasis.getSelectedIndex())+") t2 "
                                                   +"ON t1.centre_uid=t2.fpp_code "
                                                 +"WHERE funds_id=?"
                                                 +" AND t1.legal_base=?"
                                                 +" ORDER BY centre_uid");
                preparedStatement.setInt(1, Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex())));
                preparedStatement.setInt(2, Integer.valueOf(arrLegalID.get(cboLegalBasis.getSelectedIndex())));
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
                total_FPP();
                if(tblTreeFPP.getRowCount()==0){
                    deleteRoot();
                }else{
                    lblStatus.setText("Partial Entry");
                }
                                                                                    
        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
        } 
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void office_tree(String fundcode) {  
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        javax.swing.tree.DefaultMutableTreeNode rootNode;
        javax.swing.tree.DefaultTreeModel treeModel;
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement;        
        cboOffice.removeAllItems();
        cboProgram.removeAllItems();
        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement("SELECT "
                                                               +"CASE WHEN parent!='0' AND description!='Offices' "
                                                                     +"THEN split_part(centre_uid,'-',2)||'-'||split_part(centre_uid,'-',3)||'-'||split_part(centre_uid,'-',4)::INTEGER "
                                                                     +"WHEN description='Offices' THEN centre_uid "
                                                                     +"ELSE split_part(centre_uid,'-',2)||'-'||split_part(centre_uid,'-',3) "
                                                                     +"END AS centre_uid,"
                                                               +"description, "
                                                               +"CASE WHEN parent!='0' AND description!='Offices' "
                                                                     +"THEN split_part(parent,'-',2)||'-'||split_part(parent,'-',3) "
                                                                     +"ELSE  parent END "
                                                        + "FROM buds.tree_office('"+fundcode +"')");
                java.sql.ResultSet rs = preparedStatement.executeQuery();
                int ctrow = 0;
                while(rs.next()){
                    ctrow=rs.getRow();
                }
                rs.close();
                
                Object[][] table = new Object[ctrow][3];
        
                int row = 0;
                java.sql.ResultSet rs1 = preparedStatement.executeQuery();
                while (rs1.next()) {
                    table[row][0] = rs1.getString("centre_uid");
                    table[row][1] = rs1.getString("parent");
                    table[row][2] = rs1.getString("centre_uid") + "~" + rs1.getString("description");
                              
                    row++;
                }
                rs1.close();   
                preparedStatement.close();
                                            
                //Create as many nodes as there are rows of data.
                javax.swing.tree.DefaultMutableTreeNode[] node = new javax.swing.tree.DefaultMutableTreeNode[table.length];
                for (int i = 0; i < table.length; i++) {
                    node[i] = new javax.swing.tree.DefaultMutableTreeNode(table[i][2].toString());   
                    node[0]=new javax.swing.tree.DefaultMutableTreeNode(""); 
                }

                rootNode =node[0];   //Set the root node

                //Cycle through the table above and assign nodes to nodes
                for (int i = 0; i < table.length; i++) {
                    for (int j = 1; j < table.length; j++) {
                        if (table[i][0] .equals(table[j][1])) {
                            node[i].add(node[j]);
                        }
                    }
                }   
                
                //Creating the tree model. setting the root node.
                treeModel = new javax.swing.tree.DefaultTreeModel(rootNode);
                treeOffice.setModel(treeModel);                                                     

        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmFPP").log(java.util.logging.Level.SEVERE, null, ex);
                }
        } 
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void selectPrograms(Integer fund,Integer services,Integer ofis,Integer subofis){
		setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                connect.DBConnection dbc = null;
		java.sql.PreparedStatement preparedStatement = null;
                cboProgram.removeAllItems();
                arrProgID.clear();
		String selectSQL = "SELECT * FROM buds.fund_central "
                                    +"WHERE programs!=0 "
                                    +"AND resp_sub=? "
                                    +"AND resp_id=? "
                                    +"AND services=? "
                                    +"AND funds_id=? "
                                    +"ORDER BY centre_uid";

		try {
			dbc = new connect.DBConnection();
			preparedStatement = dbc.prepareStatement(selectSQL);
                        preparedStatement.setInt(1, subofis);
                        preparedStatement.setInt(2, ofis);
                        preparedStatement.setInt(3, services);
                        preparedStatement.setInt(4, fund);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
                                arrProgID.add(rs.getString("programs"));
				cboProgram.addItem(rs.getString("description"));
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
                            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                        }
		}
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	}
    
    private Boolean isFundExist(Integer legal,Integer fund){
        boolean exist=false;
        connect.DBConnection dbc = null;
        java.sql.PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT COUNT(*) FROM buds.appropriation_status" 
                                +" WHERE fund_id=?"
                                +" AND legal_base=?"
                                +" AND status<>'Cancelled'";

        try {
                dbc = new connect.DBConnection();
                preparedStatement = dbc.prepareStatement(selectSQL);
                preparedStatement.setInt(1, fund);
                preparedStatement.setInt(2, legal);
                java.sql.ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                        if(rs.getInt(1)>0){
                            exist=true;
                        }
                }

                preparedStatement.close();
                rs.close();                        

        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
        }
        return exist;
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
                    lblStatus.setText(rs1.getString("status"));
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
                total_FPP();
                if(tblTreeFPP.getRowCount()==0){
                    deleteRoot();                    
                    btnNewActionPerformed(null);
                }
                                                                                    
        } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, e.getMessage(), "Select Records", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
                if (dbc != null) try {
                    dbc.close();
                } catch (java.sql.SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "CLOSING CONNECTION", javax.swing.JOptionPane.ERROR_MESSAGE);
                    java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
                }
        } 
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void deleteRoot(){
        connect.DBConnection dbc = null;
        try {
            dbc = new connect.DBConnection();
            dbc.setAutoCommit(false);
            dbase.SQLExecute deleteParent = new dbase.SQLExecute("temp.prep_respcenter");
            deleteParent.FieldName("funds_id",  NUMERIC, enums.Take.ConditionOnly, Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex())));
            deleteParent.FieldName("legal_base",  NUMERIC, enums.Take.ConditionOnly, Integer.valueOf(arrLegalID.get(cboLegalBasis.getSelectedIndex())));
            deleteParent.FieldName("parent_id",  !NUMERIC, enums.Take.ConditionOnly, "0");
            dbc.executeUpdate(deleteParent.Perform(enums.Fire.doDelete));  
            
            dbase.SQLExecute deleteStatus = new dbase.SQLExecute("buds.appropriation_status");
            deleteStatus.FieldName("fund_id",  NUMERIC, enums.Take.ConditionOnly, Integer.valueOf(arrFundID.get(cboFund.getSelectedIndex())));
            deleteStatus.FieldName("legal_base",  NUMERIC, enums.Take.ConditionOnly, Integer.valueOf(arrLegalID.get(cboLegalBasis.getSelectedIndex())));         
            dbc.executeUpdate(deleteStatus.Perform(enums.Fire.doDelete));    
            
            dbc.commit();
            dbc.close();  
            lblStatus.setText("");
        } catch (Exception ex) {
            if (dbc != null) try {
                dbc.rollback();
            } catch (Exception rlbk) {
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, rlbk);
            }
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
            java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);

        } finally {
            if (dbc != null) try {
                dbc.close();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger("iFrmPrepAppropriation").log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    private  java.util.Calendar DateToCalendar(java.util.Date date){ 
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddClass;
    private javax.swing.JButton btnAddFpp;
    private javax.swing.JButton btnAddObjectExp;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPost;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveUpdate;
    private javax.swing.JComboBox cboClass;
    private javax.swing.JComboBox cboFund;
    private javax.swing.JComboBox cboLegalBasis;
    private javax.swing.JComboBox cboOffice;
    private javax.swing.JComboBox cboProgram;
    private datechooser.beans.DateChooserCombo chDatePrepApprop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblAmountCaption;
    private javax.swing.JLabel lblControlNo;
    private javax.swing.JLabel lblMode;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTotalFPPDetail;
    private javax.swing.JLabel lblTotalFPPSum;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTable tblDetails;
    private org.jdesktop.swingx.JXTreeTable tblTreeFPP;
    private javax.swing.JTree treeOffice;
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
