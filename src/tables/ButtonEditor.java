/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author felix
 */

/**
 * @version 1.0 11/09/98
 */
public class ButtonEditor extends javax.swing.DefaultCellEditor {

    protected javax.swing.JButton button;
    //private String label;
    //private boolean isPushed;

    public ButtonEditor(javax.swing.JButton btnBox) {
        super(new javax.swing.JCheckBox());
        button = btnBox;
//        button = new JButton();
//        button.setOpaque(true);
//        button.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                fireEditingStopped();
//            }
//        });
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
//        if (isSelected) {
//            button.setForeground(table.getSelectionForeground());
//            button.setBackground(table.getSelectionBackground());
//        } else {
//            button.setForeground(table.getForeground());
//            button.setBackground(table.getBackground());
//        }
//        label = (value == null) ? "" : value.toString();
//        button.setText(label);
//        isPushed = true;
        return button;
    }

//    @Override
//    public Object getCellEditorValue() {
//        if (isPushed) {
//            //
//            //
//            JOptionPane.showMessageDialog(button, label + ": Ouch!");
//            // System.out.println(label + ": Ouch!");
//        }
//        isPushed = false;
//        return new String(label);
//    }

    @Override
    public boolean stopCellEditing() {
//        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
