/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author felixiong
 */
public class FormattedTextField extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellEditor {

    private javax.swing.JFormattedTextField formatter = new javax.swing.JFormattedTextField();
    //private String formatPattern;

    // Initializes the spinner.
    public FormattedTextField(String pattern) {
        try {
            formatter.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter(pattern)));
            //formatter.setText("  /  /    ");
        } catch (java.text.ParseException ex) {
            java.util.logging.Logger.getLogger(FormattedTextField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    // Prepares the spinner component and returns it.
    @Override
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null)
            formatter.setValue(value);
        
        return formatter;
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
        try {
            formatter.commitEdit();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FormattedTextField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            return formatter.getValue();
        }
    }
}
