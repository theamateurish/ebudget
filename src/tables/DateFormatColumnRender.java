package tables;

public class DateFormatColumnRender extends javax.swing.table.DefaultTableCellRenderer {

    public static final short SLASH_SEPARATED = 1;
    public static final short DASH_SEPARATED  = 2;
    public static final short SLASH_DATE_TIME = 3;
    public static final short DASH_DATE_TIME  = 4;
    public static final short SLASH_DATE_TIME_AMPM = 5;
    public static final short DASH_DATE_TIME_AMPM = 6;
    public static final short STANDARD_TIME = 7;
    public static final short MILITARY_TIME = 8;
    
    private int Attribute;
    private String Pattern;

    public DateFormatColumnRender (short pattern, int alignment) {
        Attribute = alignment;
        switch (pattern) {
            case SLASH_SEPARATED:
                Pattern = "MM/dd/yyyy";
                break;
            case DASH_SEPARATED:
                Pattern = "yyyy-MM-dd";
                break;
            case SLASH_DATE_TIME:
                Pattern = "MM/dd/yyyy HH:mm:ss";
                break;
            case DASH_DATE_TIME:
                Pattern = "yyyy-MM-dd HH:mm:ss";
                break;
            case SLASH_DATE_TIME_AMPM:
                Pattern = "MM/dd/yyyy hh:mm:ss a";
                break;
            case DASH_DATE_TIME_AMPM:
                Pattern = "yyyy-MM-dd hh:mm:ss a";
                break;
            case STANDARD_TIME:
                Pattern = "hh:mm:ss a";
                break;
            case MILITARY_TIME:
                Pattern = "HH:mm:ss";
                break;
            default:
                
        }
    }
    
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        javax.swing.JLabel renderedLabel = (javax.swing.JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        renderedLabel.setHorizontalAlignment(Attribute);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(Pattern);
        if (value != null) {
            try {
                if (value instanceof String)
                    renderedLabel.setText(formatter.format(new java.text.SimpleDateFormat(Pattern).parse(value.toString())));
                else
                    renderedLabel.setText(formatter.format(value));
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(this.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        return renderedLabel;
    }
}
