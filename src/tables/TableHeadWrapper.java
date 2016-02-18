/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author Linux
 */
public class TableHeadWrapper {

    public TableHeadWrapper(javax.swing.JTable oTable) {
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        java.util.Enumeration emplyr = oTable.getColumnModel().getColumns();
        while (emplyr.hasMoreElements()) {
            ((javax.swing.table.TableColumn) emplyr.nextElement()).setHeaderRenderer(renderer);
        }
    }

    private class MultiLineHeaderRenderer extends javax.swing.JList implements javax.swing.table.TableCellRenderer {

        public MultiLineHeaderRenderer() {
            setOpaque(true);
            setForeground(javax.swing.UIManager.getColor("TableHeader.foreground"));
            setBackground(javax.swing.UIManager.getColor("TableHeader.background"));
            setBorder(javax.swing.UIManager.getBorder("TableHeader.cellBorder"));
            javax.swing.ListCellRenderer renderer = getCellRenderer();
            ((javax.swing.JLabel) renderer).setHorizontalAlignment(javax.swing.JLabel.CENTER);
            setCellRenderer(renderer);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setFont(table.getFont());
            String str = (value == null) ? "" : value.toString();
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.StringReader(str));
            String line1 = "";
            java.util.Vector<String> vector = new java.util.Vector<String>();
            try {
                while ((line1 = br.readLine()) != null) {
                    vector.addElement(line1);
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
            setListData(vector);
            return this;
        }
    }
}

