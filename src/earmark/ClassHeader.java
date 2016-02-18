package earmark;

public class ClassHeader extends org.jdesktop.swingx.treetable.AbstractTreeTableModel {

    private final static String[] COLUMN_NAMES = {"Title", "CODE", "ParentID"};

    private final java.util.List<earmark.Classification> mainClass;

    public ClassHeader() {
        this.mainClass = null;
    }
    
    public ClassHeader(java.util.List<earmark.Classification> mainoffcList) {
        super(new Object());
        mainClass = mainoffcList;
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
        return node instanceof earmark.Classification & getChildCount(node) == 0;//  clazz.SubOffice;
    }

    @Override
    public Object getValueAt(Object node, int column) {
        earmark.Classification classed = (earmark.Classification) node;
        switch (column) {
            case 0:
                return classed.getTitulo();
            case 1:
                return classed.getUID();
            default:
                return classed.getRootID();
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof earmark.Classification) {
            earmark.Classification main = (earmark.Classification) parent;
            return main.getChartList().get(index);
        }
        return mainClass.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof earmark.Classification) {
            earmark.Classification dept = (earmark.Classification) parent;
            if (dept.getChartList()== null)
                return 0;
            else
                return dept.getChartList().size();
        }
        return mainClass.size();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        earmark.Classification main = (earmark.Classification) parent;
        earmark.Classification sub = (earmark.Classification) child;
        return main.getChartList().indexOf(sub);
    }

}
