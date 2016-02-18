package mdiMenu;

/*
 * This JCheckBoxMenuItem descendant is used to track the child frame that
 * corresponds to a give menu.
 */
public class ChildMenuItem extends javax.swing.JCheckBoxMenuItem {

    private javax.swing.JInternalFrame frame;

    public ChildMenuItem(javax.swing.JInternalFrame frame) {
        super(frame.getTitle());
        this.frame = frame;
    }

    public javax.swing.JInternalFrame getFrame() {
        return frame;
    }
}
