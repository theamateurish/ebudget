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
public class ButtonRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(false);
    }

    public ButtonRenderer(java.net.URL name) {
        setOpaque(false);
        setIcon(new javax.swing.ImageIcon(name));
        setBorderPainted(false);
        setMargin(new java.awt.Insets(0, 0, 0, 0));
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        if (isSelected) {
//            setForeground(table.getSelectionForeground());
//            setBackground(table.getSelectionBackground());
//        } else {
//            setForeground(table.getForeground());
//            setBackground(UIManager.getColor("Button.background"));
//        }
        //setText((value == null) ? "" : value.toString());
        return this;
    }
}
