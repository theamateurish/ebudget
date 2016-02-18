/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author felixiong
 */
public class SpinnerCellEditor extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellEditor {

    //public final static short NUMERICAL_VALUE = 1;
    //public final static short DATETIME_VALUE = 0;
    private javax.swing.JSpinner Spinner;// = new javax.swing.JSpinner();
    //private String formatPattern;
    //private short ValueType;

    // Initializes the spinner.
    public SpinnerCellEditor(javax.swing.JSpinner spinner) { //, String pattern, short valueType, java.awt.Font font) {
        //spinner.setModel(new javax.swing.SpinnerListModel(java.util.Arrays.asList(items)));
        Spinner = spinner;
        //ValueType =  valueType;
        //formatPattern = pattern;

//        switch (valueType) {
//            case DATETIME_VALUE:
//                spinner.setFont(font); // NOI18N
//                spinner.setModel(new javax.swing.SpinnerDateModel());
//                spinner.setEditor(new javax.swing.JSpinner.DateEditor(spinner, formatPattern));
//                break;
//
//            default:
//                spinner.setFont(font); // NOI18N
//                spinner.setModel(new javax.swing.SpinnerNumberModel());
//                spinner.setEditor(new javax.swing.JSpinner.NumberEditor(spinner, formatPattern));
//        }
    }

    // Prepares the spinner component and returns it.
    @Override
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
//            try {
//                switch (ValueType) {
//                    case DATETIME_VALUE:
//                        Spinner.setValue(new java.text.SimpleDateFormat(formatPattern).parse(String.valueOf(value)));
//                        break;
//
//                    default:
//                        Spinner.setValue(new java.text.DecimalFormat(formatPattern).parse(String.valueOf(value)));
//                }
//            } catch (java.text.ParseException ex) {
//                java.util.logging.Logger.getLogger(SpinnerCellEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//            }
            Spinner.setValue(value);
        }
        return Spinner;
    }

    // Enables the editor only for double-clicks.
    @Override
    public boolean isCellEditable(java.util.EventObject evt) {
        if (evt instanceof java.awt.event.MouseEvent) {
            return ((java.awt.event.MouseEvent) evt).getClickCount() >= 2;
        }
        return true;
    }

    // Returns the spinners current value.
    @Override
    public Object getCellEditorValue() {
        //return new java.text.SimpleDateFormat(formatPattern).format(Spinner.getValue());
        return Spinner.getValue();
    }
}
