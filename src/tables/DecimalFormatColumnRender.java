package tables;

public class DecimalFormatColumnRender extends javax.swing.table.DefaultTableCellRenderer {

    public static final short COMMA_SEPARATED = 0;
    public static final short COMMA_NONE      = 1;
    
    private int Attribute;
    private String Pattern;

    public DecimalFormatColumnRender (short pattern, short decimals, int alignment) {
        Attribute = alignment;
        StringBuffer decplace = new StringBuffer();
        if (decimals > 0) decplace.append(".");
        for (short abc = 1; abc <= decimals; abc++)
            decplace.append("0");
        
        switch (pattern) {
            case COMMA_SEPARATED:
                Pattern = "#,##0" + decplace + ";(#,##0" + decplace + ")";
                break;
            default:
                Pattern = "#0" + decplace + ";(#0" + decplace + ")";
        }
    }
    
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel renderedLabel = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        renderedLabel.setHorizontalAlignment(Attribute);
        java.text.DecimalFormat formatter = new java.text.DecimalFormat(Pattern);
        if (value != null) renderedLabel.setText(formatter.format(value));
            //table.setValueAt(Double.parseDouble(renderedLabel.getText().replaceAll(",", "")), row, column);
        return renderedLabel;
    }
}
