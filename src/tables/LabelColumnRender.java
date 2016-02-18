package tables;

public class LabelColumnRender extends javax.swing.table.DefaultTableCellRenderer {

    private int attribute;

    public LabelColumnRender (int attrib) {
        attribute = attrib;
    }
    
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel renderedLabel = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        renderedLabel.setHorizontalAlignment(attribute);
        return renderedLabel;
    }
}
