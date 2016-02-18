package earmark;

import java.util.List;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class OfficeHeader extends AbstractTreeTableModel {

    private final static String[] COLUMN_NAMES = {"Office", "ID", "SUB-ID", "UID"};

    private final List<earmark.Opesina> aOffice;

    public OfficeHeader() {
        aOffice = null;
    }
    
    public OfficeHeader(List<earmark.Opesina> mainoffcList) {
        super(mainoffcList);
        aOffice = mainoffcList;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof earmark.Opesina & getChildCount(node) == 0;//  clazz.SubOffice;
    }

    @Override
    public Object getValueAt(Object node, int column) {
        if (node == null) return null;
        earmark.Opesina office = (earmark.Opesina) node;
        switch (column) {
            case 0:
                return office.getNgalan();
            case 1:
                return office.getCode();
            case 2:
                if (office.getSubid() == null)
                    return null;
                else
                    return new java.text.DecimalFormat("00").format(office.getSubid());
            default:
                return office.getUID();
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof earmark.Opesina) {
            earmark.Opesina main = (earmark.Opesina) parent;
            return main.getSubOffcList().get(index);
        }
        return aOffice.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof earmark.Opesina) {
            earmark.Opesina dept = (earmark.Opesina) parent;
            if (dept.getSubOffcList() == null)
                return 0;
            else
                return dept.getSubOffcList().size();
        }
        return aOffice.size();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        earmark.Opesina main = (earmark.Opesina) parent;
        earmark.Opesina sub = (earmark.Opesina) child;
        return main.getSubOffcList().indexOf(sub);
    }

}
