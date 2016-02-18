package setting;

/**
 *
 * @author felix
 */
public class ResOrdForm extends javax.swing.JInternalFrame {

    private final java.util.List<javax.swing.JRadioButton> arType = new java.util.ArrayList();
    
    /**
     * Creates new form ExpenseSetFrame
     */
    public ResOrdForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currentGroup = new javax.swing.ButtonGroup();
        continueGroup = new javax.swing.ButtonGroup();
        mainGroup = new javax.swing.ButtonGroup();
        pnlSub = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnTikyop = new javax.swing.JButton();
        TabbedPane = new javax.swing.JTabbedPane();
        Container = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        txtLegal = new javax.swing.JTextField();
        spnYear = new javax.swing.JSpinner();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        fmtSpan = new javax.swing.JFormattedTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        optCurrent = new javax.swing.JRadioButton();
        optCurrReg = new javax.swing.JRadioButton();
        optCurrSupp = new javax.swing.JRadioButton();
        optContinue = new javax.swing.JRadioButton();
        optContReg = new javax.swing.JRadioButton();
        optContSupp = new javax.swing.JRadioButton();
        pnlMain = new javax.swing.JPanel();
        javax.swing.JButton btnAppend = new javax.swing.JButton();
        javax.swing.JButton btnModify = new javax.swing.JButton();
        javax.swing.JButton btnRemove = new javax.swing.JButton();
        javax.swing.JButton btnPreview = new javax.swing.JButton();
        javax.swing.JButton btnClose = new javax.swing.JButton();
        javax.swing.JPanel jPanel5 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();

        pnlSub.setPreferredSize(new java.awt.Dimension(486, 50));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/save.png"))); // NOI18N
        btnSave.setText("Save/Alter");
        btnSave.setBorderPainted(false);
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusable(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnTikyop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/close.png"))); // NOI18N
        btnTikyop.setText("Close");
        btnTikyop.setBorderPainted(false);
        btnTikyop.setFocusable(false);
        btnTikyop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTikyop.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnTikyop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTikyop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSubLayout = new javax.swing.GroupLayout(pnlSub);
        pnlSub.setLayout(pnlSubLayout);
        pnlSubLayout.setHorizontalGroup(
            pnlSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubLayout.createSequentialGroup()
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(btnTikyop, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlSubLayout.setVerticalGroup(
            pnlSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTikyop, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Appropriation Ordinances");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/office_setup.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
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
                formInternalFrameOpened(evt);
            }
        });

        TabbedPane.setFocusable(false);

        Container.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setText("Legal Basis");
        Container.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 16, -1, -1));

        txtLegal.setEnabled(false);
        Container.add(txtLegal, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 14, 405, -1));

        spnYear.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        spnYear.setModel(new javax.swing.SpinnerNumberModel(Short.valueOf((short)2000), Short.valueOf((short)2000), Short.valueOf((short)3000), Short.valueOf((short)1)));
        spnYear.setEditor(new javax.swing.JSpinner.NumberEditor(spnYear, "#0"));
        spnYear.setEnabled(false);
        Container.add(spnYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 39, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Year");
        Container.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 41, -1, -1));

        fmtSpan.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        fmtSpan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        fmtSpan.setEnabled(false);
        fmtSpan.setValue(1);
        Container.add(fmtSpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 65, 64, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Span To");
        Container.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 67, -1, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Yrs.");
        Container.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 67, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Appropriation Type"));

        mainGroup.add(optCurrent);
        optCurrent.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optCurrent.setText("Current");
        optCurrent.setEnabled(false);
        optCurrent.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optActionPerformed(evt);
            }
        });

        currentGroup.add(optCurrReg);
        optCurrReg.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optCurrReg.setSelected(true);
        optCurrReg.setText("Regular");
        optCurrReg.setEnabled(false);
        optCurrReg.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optCurrReg.setName("1"); // NOI18N

        currentGroup.add(optCurrSupp);
        optCurrSupp.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optCurrSupp.setText("Supplemental");
        optCurrSupp.setEnabled(false);
        optCurrSupp.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optCurrSupp.setName("2"); // NOI18N

        mainGroup.add(optContinue);
        optContinue.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optContinue.setText("Continuing");
        optContinue.setEnabled(false);
        optContinue.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optActionPerformed(evt);
            }
        });

        continueGroup.add(optContReg);
        optContReg.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optContReg.setSelected(true);
        optContReg.setText("Regular");
        optContReg.setEnabled(false);
        optContReg.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optContReg.setName("3"); // NOI18N

        continueGroup.add(optContSupp);
        optContSupp.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        optContSupp.setText("Supplemental");
        optContSupp.setEnabled(false);
        optContSupp.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optContSupp.setName("4"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optCurrent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(optContinue)
                .addGap(127, 127, 127))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optCurrReg)
                    .addComponent(optCurrSupp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optContSupp)
                    .addComponent(optContReg))
                .addGap(66, 66, 66))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optCurrent)
                    .addComponent(optContinue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optCurrReg)
                    .addComponent(optContReg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optCurrSupp)
                    .addComponent(optContSupp))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        Container.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 102, 487, -1));

        btnAppend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/new.png"))); // NOI18N
        btnAppend.setText("Append");
        btnAppend.setBorderPainted(false);
        btnAppend.setFocusable(false);
        btnAppend.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAppend.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnAppend.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAppend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEditActionPerformed(evt);
            }
        });

        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/modify.png"))); // NOI18N
        btnModify.setText("Modify");
        btnModify.setBorderPainted(false);
        btnModify.setFocusable(false);
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModify.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnModify.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEditActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/eraser.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setBorderPainted(false);
        btnRemove.setFocusable(false);
        btnRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemove.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/preview.png"))); // NOI18N
        btnPreview.setText("Preview");
        btnPreview.setBorderPainted(false);
        btnPreview.setFocusable(false);
        btnPreview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreview.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnPreview.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/close.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setBorderPainted(false);
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(btnAppend, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAppend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(btnPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        Container.add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 231, -1, -1));

        TabbedPane.addTab("Data Entry", Container);

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Type", "type", "Year", "span", "key"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Short.class, java.lang.Short.class, java.lang.Short.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblList);
        if (tblList.getColumnModel().getColumnCount() > 0) {
            tblList.getColumnModel().getColumn(0).setPreferredWidth(290);
            tblList.getColumnModel().getColumn(1).setPreferredWidth(110);
            tblList.getColumnModel().getColumn(2).setMinWidth(0);
            tblList.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblList.getColumnModel().getColumn(2).setMaxWidth(0);
            tblList.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblList.getColumnModel().getColumn(4).setMinWidth(0);
            tblList.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblList.getColumnModel().getColumn(4).setMaxWidth(0);
            tblList.getColumnModel().getColumn(5).setMinWidth(0);
            tblList.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblList.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Double click the item of your choice to open in [Data Entry] tab.");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setForeground(java.awt.Color.red);
        jLabel6.setText("Note:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        TabbedPane.addTab("List", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedPane)
                .addContainerGap())
        );

        setBounds(0, 0, 554, 377);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        pnlSub.setVisible(false);
        Container.add(pnlSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 231, -1, -1));
        //tblList.getModel().addTableModelListener(new MyTableModelListener());
        arType.add(optCurrReg);
        arType.add(optCurrSupp);
        arType.add(optContReg);
        arType.add(optContSupp);
        
        tblList.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent lse) {
                if (lse.getValueIsAdjusting()) return;
                if (tblList.getSelectedRow() >= 0) {
                    CURRENT_ROW = tblList.getSelectedRow();
                    RestoreValues();
                } /*else
                    CURRENT_ROW = 0;*/
            }
        });

        reLoad();
    }//GEN-LAST:event_formInternalFrameOpened

    private void reLoad() {
        String sqlCommand = 
                "SELECT " +
                    "legal_uid, " +
                    "legal_desc, " +
                    "ra_type, " +
                    "legal_year, " +
                    "span_to " +
                "FROM " +
                    "buds.ordinances " +
                "ORDER BY " +
                    "entered";
        try (budget.DBaseLink jdbc = new budget.DBaseLink();
                java.sql.Statement stmt = jdbc.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                java.sql.ResultSet rst = stmt.executeQuery(sqlCommand)) {
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel)tblList.getModel();
            modelo.setRowCount(0);
            while(rst.next()) {
                Object[] rowData = {
                    rst.getString(2),                // Description
                    getTypeDesc(rst.getShort(3)),    // Type
                    rst.getShort(3),                 // Type
                    rst.getShort(4),                 // Year
                    rst.getShort(5),                 // Span
                    rst.getInt(1)                    // uid
                };
                modelo.addRow(rowData);
            }
            PETSA.setTime(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(System.getProperty("PETSAH")));
            tblList.setRowSelectionInterval(0, 0);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), title, javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(ResOrdForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private String getTypeDesc(short value) {
        switch (value) {
            case 1:
                return "Current Regular";
            case 2:
                return "Current Supplemental";
            case 3:
                return "Continuing Regular";
            default:
                return "Continuing Supplemental";
        }
    }
    
    private void RestoreValues() {
        short type = Short.parseShort(tblList.getValueAt(CURRENT_ROW, 2).toString(), 10);
        txtLegal.setText(tblList.getValueAt(CURRENT_ROW, 0).toString());
        spnYear.setValue(tblList.getValueAt(CURRENT_ROW, 3));
        fmtSpan.setValue(tblList.getValueAt(CURRENT_ROW, 4));
        CURRENT_UID = Integer.parseInt(tblList.getValueAt(CURRENT_ROW, 5).toString(), 10);
        switch (type) {
            case 1:
                optCurrReg.setSelected(true);
                optCurrent.setSelected(true);
                break;
            case 2:
                optCurrSupp.setSelected(true);
                optCurrent.setSelected(true);
                break;
            case 3:
                optContReg.setSelected(true);
                optContinue.setSelected(true);
                break;
            default:
                optContSupp.setSelected(true);
                optContinue.setSelected(true);
        }
    }

    private void optActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optActionPerformed
        // TODO add your handling code here:
        optCurrReg.setEnabled(evt.getActionCommand().equals("Current"));
        optCurrSupp.setEnabled(evt.getActionCommand().equals("Current"));
        optContReg.setEnabled(evt.getActionCommand().equals("Continuing"));
        optContSupp.setEnabled(evt.getActionCommand().equals("Continuing"));
    }//GEN-LAST:event_optActionPerformed

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        // TODO add your handling code here:
        doDefaultCloseAction();
    }//GEN-LAST:event_CloseActionPerformed

    private void addEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEditActionPerformed
        // TODO add your handling code here:
        pnlSub.setVisible(true);
        pnlMain.setVisible(false);
        btnSave.setEnabled(true);
        TabbedPane.setEnabledAt(1, false);

        Switcher(true);
        
        if (evt.getActionCommand().equals("Append")) {
            txtLegal.setText("");
            spnYear.setValue(PETSA.get(java.util.Calendar.YEAR));
            fmtSpan.setValue(1);
            optCurrent.setSelected(false);
            optCurrReg.setEnabled(true);
            optCurrSupp.setEnabled(true);
            FLAG = "APPEND";
        } else
            FLAG = "MODIFY";

        txtLegal.requestFocus();
    }//GEN-LAST:event_addEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        pnlSub.setVisible(false);
        pnlMain.setVisible(true);
        Switcher(false);
        RestoreValues();
        optCurrReg.setEnabled(false);
        optCurrSupp.setEnabled(false);
        TabbedPane.setEnabledAt(1, true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        Object[] options = {"Yes", "No"};
        boolean checking = javax.swing.JOptionPane.showOptionDialog(
                this,
                "You are about to erase the selected item.\nDo you want to continue?",
                title,
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[options.length - 1]) == javax.swing.JOptionPane.NO_OPTION;
        if (checking) return;

        int index = tblList.getSelectedRow();
        index = tblList.convertRowIndexToModel(index);

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel)tblList.getModel();
        /*String id_code = modelo.getValueAt(index, 2).toString(),
                sqlCmd = 
                "SELECT " +
                    "fund_central.offices, " +
                    "fund_control.years " +
                "FROM " +
                    "buds.ordinances JOIN buds.fund_control " +
                    "ON fund_central.centre_uid = fund_control.centre_uid " +
                "WHERE " +
                    "(fund_central.centre_uid = '" + id_code + "')"*/
        try (budget.DBaseLink jdbc = new budget.DBaseLink();
                //java.sql.Statement stmt = jdbc.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
                java.sql.PreparedStatement ptmt = jdbc.prepareStatement("DELETE FROM buds.ordinances WHERE (legal_uid = ?)");
                java.sql.PreparedStatement utmt = jdbc.prepareStatement("UPDATE buds.ordinances SET tagoon = TRUE WHERE (centre_uid = ?)");
                /*java.sql.ResultSet rst = stmt.executeQuery(sqlCmd)*/) {
            /*if (rst.next()) {
                utmt.setString(1, id_code);
                utmt.executeUpdate();
            } else {*/
                ptmt.setInt(1, CURRENT_UID);
                ptmt.executeUpdate();
            //}
            modelo.removeRow(index);
            reLoad();


        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), title, javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(ResOrdForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {

        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String sqlCmd1 = 
                "UPDATE " +
                    "buds.ordinances " +
                "SET " +
                    "legal_desc = ?, ra_type = ?, legal_year = ?, span_to = ? " +
                "WHERE " +
                    "(legal_uid = ?)",
               sqlCmd2 = "INSERT INTO " +
                    "buds.ordinances (legal_desc, ra_type, legal_year, span_to) " +
                "VALUES " +
                    "(?, ?, ?, ?)";
        try (budget.DBaseLink jdbc = new budget.DBaseLink();
                java.sql.PreparedStatement upmt = jdbc.prepareStatement(sqlCmd1);
                java.sql.PreparedStatement ipmt = jdbc.prepareStatement(sqlCmd2)) {
            //for (javax.swing.JRadioButton option : arType) {
            short typed = 0;
            for (short abc = 0; abc < arType.size(); abc++) {
                if (arType.get(abc).isSelected() & arType.get(abc).isEnabled()) typed = (short)(abc + 1);
            }
            switch (FLAG) {
                case "APPEND":
                    ipmt.setString(1, txtLegal.getText().toUpperCase());
                    ipmt.setShort (2, typed);
                    ipmt.setShort (3, Short.parseShort(spnYear.getValue().toString(), 10));
                    ipmt.setShort (4, Short.parseShort(fmtSpan.getValue().toString(), 10));
                    ipmt.executeUpdate();
                    break;
                default:
                    upmt.setString(1, txtLegal.getText().toUpperCase());
                    upmt.setShort (2, typed);
                    upmt.setShort (3, Short.parseShort(spnYear.getValue().toString(), 10));
                    upmt.setShort (4, Short.parseShort(fmtSpan.getValue().toString(), 10));
                    upmt.setInt   (5, CURRENT_UID);
                    upmt.executeUpdate();
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Record save successfully.", title, javax.swing.JOptionPane.INFORMATION_MESSAGE);
            btnCancel.setText("Back");
            btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imahen/back.png"))); // NOI18N

            pnlSub.setVisible(false);
            pnlMain.setVisible(true);
            
            reLoad();
            btnCancelActionPerformed(evt);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), title, javax.swing.JOptionPane.ERROR_MESSAGE);
            java.util.logging.Logger.getLogger(ResOrdForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void Switcher(boolean toggle) {
        txtLegal.setEnabled(toggle);
        spnYear.setEnabled(toggle);
        fmtSpan.setEnabled(toggle);
        optCurrent.setEnabled(toggle);
        optContinue.setEnabled(toggle);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Container;
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTikyop;
    private javax.swing.ButtonGroup continueGroup;
    private javax.swing.ButtonGroup currentGroup;
    private javax.swing.JFormattedTextField fmtSpan;
    private javax.swing.ButtonGroup mainGroup;
    private javax.swing.JRadioButton optContReg;
    private javax.swing.JRadioButton optContSupp;
    private javax.swing.JRadioButton optContinue;
    private javax.swing.JRadioButton optCurrReg;
    private javax.swing.JRadioButton optCurrSupp;
    private javax.swing.JRadioButton optCurrent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSub;
    private javax.swing.JSpinner spnYear;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtLegal;
    // End of variables declaration//GEN-END:variables

    private Integer CURRENT_ROW, CURRENT_UID;
    private final java.util.Calendar PETSA = java.util.Calendar.getInstance();
    private String FLAG;
    
}