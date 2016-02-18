package mdiMenu;

/**
 * Private class used to replace the standard DesktopManager for JDesktopPane.
 * Used to provide scrollbar functionality.
 */
public class MDIDesktopManager extends javax.swing.DefaultDesktopManager {

    private MDIDesktopPane desktop;

    public MDIDesktopManager(MDIDesktopPane desktop) {
        this.desktop = desktop;
    }

    @Override
    public void endResizingFrame(javax.swing.JComponent f) {
        super.endResizingFrame(f);
        resizeDesktop();
    }

    @Override
    public void endDraggingFrame(javax.swing.JComponent f) {
        super.endDraggingFrame(f);
        resizeDesktop();
    }

    public void setNormalSize() {
        javax.swing.JScrollPane scrollPane = getScrollPane();
        int x = 0;
        int y = 0;
        java.awt.Insets scrollInsets = getScrollPaneInsets();

        if (scrollPane != null) {
            java.awt.Dimension d = scrollPane.getVisibleRect().getSize();
            if (scrollPane.getBorder() != null) {
                d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top - scrollInsets.bottom);
            }

            d.setSize(d.getWidth() - 20, d.getHeight() - 20);
            desktop.setAllSize(x, y);
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }

    private java.awt.Insets getScrollPaneInsets() {
        javax.swing.JScrollPane scrollPane = getScrollPane();
        if (scrollPane == null) {
            return new java.awt.Insets(0, 0, 0, 0);
        } else {
            return getScrollPane().getBorder().getBorderInsets(scrollPane);
        }
    }

    private javax.swing.JScrollPane getScrollPane() {
        if (desktop.getParent() instanceof javax.swing.JViewport) {
            javax.swing.JViewport viewPort = (javax.swing.JViewport) desktop.getParent();
            if (viewPort.getParent() instanceof javax.swing.JScrollPane) {
                return (javax.swing.JScrollPane) viewPort.getParent();
            }
        }
        return null;
    }

    protected void resizeDesktop() {
        int x = 0;
        int y = 0;
        javax.swing.JScrollPane scrollPane = getScrollPane();
        java.awt.Insets scrollInsets = getScrollPaneInsets();

        if (scrollPane != null) {
            javax.swing.JInternalFrame allFrames[] = desktop.getAllFrames();
            for (int i = 0; i < allFrames.length; i++) {
                if (allFrames[i].getX() + allFrames[i].getWidth() > x) {
                    x = allFrames[i].getX() + allFrames[i].getWidth();
                }
                if (allFrames[i].getY() + allFrames[i].getHeight() > y) {
                    y = allFrames[i].getY() + allFrames[i].getHeight();
                }
            }
            java.awt.Dimension d = scrollPane.getVisibleRect().getSize();
            if (scrollPane.getBorder() != null) {
                d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top - scrollInsets.bottom);
            }

            if (x <= d.getWidth()) {
                x = ((int) d.getWidth()) - 20;
            }
            if (y <= d.getHeight()) {
                y = ((int) d.getHeight()) - 20;
            }
            desktop.setAllSize(x, y);
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }
}
