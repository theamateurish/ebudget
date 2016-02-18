package mdiMenu;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.*;

/**
 * An extension of WDesktopPane that supports often used MDI functionality. This
 * class also handles setting scroll bars for when windows move too far to the
 * left or bottom, providing the MDIDesktopPane is in a ScrollPane.
 */
public class MDIDesktopPane extends javax.swing.JDesktopPane implements Scrollable {

    private static int FRAME_OFFSET = 0;
    private MDIDesktopManager manager;
    private java.awt.Image backImage = null; //member variable
    private boolean mStrech = false;

    public MDIDesktopPane() {
        manager = new MDIDesktopManager(this);
        setDesktopManager(manager);
        setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        checkDesktopSize();
    }

    public java.awt.Component add(javax.swing.JInternalFrame frame) {
        javax.swing.JInternalFrame[] array = getAllFrames();
        java.awt.Point p;
        int w;
        int h;

        java.awt.Component retval = super.add(frame);
        checkDesktopSize();
        if (array.length > 0) {
            p = array[0].getLocation();
            p.x = p.x + FRAME_OFFSET;
            p.y = p.y + FRAME_OFFSET;
        } else {
            p = new java.awt.Point(0, 0);
        }
        frame.setLocation(p.x, p.y);
        if (frame.isResizable()) {
            w = getWidth() - (getWidth() / 3);
            h = getHeight() - (getHeight() / 3);
            if (w < frame.getMinimumSize().getWidth()) {
                w = (int) frame.getMinimumSize().getWidth();
            }
            if (h < frame.getMinimumSize().getHeight()) {
                h = (int) frame.getMinimumSize().getHeight();
            }
            frame.setSize(w, h);
        }
        moveToFront(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            frame.toBack();
        }
        return retval;
    }

    @Override
    public void remove(java.awt.Component c) {
        super.remove(c);
        checkDesktopSize();
    }

    /**
     * Cascade all internal frames
     */
    public void cascadeFrames() {

        int x = 0;
        int y = 0;
        javax.swing.JInternalFrame allFrames[] = getAllFrames();

        manager.setNormalSize();
        int frameHeight = (getBounds().height - 5) - allFrames.length * FRAME_OFFSET;
        int frameWidth = (getBounds().width - 5) - allFrames.length * FRAME_OFFSET;
        for (int i = allFrames.length - 1; i >= 0; i--) {
            allFrames[i].setSize(frameWidth, frameHeight);
            allFrames[i].setLocation(x, y);
            x = x + FRAME_OFFSET;
            y = y + FRAME_OFFSET;
        }
    }

    /**
     * Tile all internal frames
     */
    public void tileFrames() {
        java.awt.Component allFrames[] = getAllFrames();
        manager.setNormalSize();
        int frameHeight = getBounds().height / allFrames.length;
        int y = 0;
        for (int i = 0; i < allFrames.length; i++) {
            allFrames[i].setSize(getBounds().width, frameHeight);
            allFrames[i].setLocation(0, y);
            y = y + frameHeight;
        }
    }

    /**
     * Sets all component size properties ( maximum, minimum, preferred) to the
     * given dimension.
     */
    public void setAllSize(java.awt.Dimension d) {
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
    }

    /**
     * Sets all component size properties ( maximum, minimum, preferred) to the
     * given width and height.
     */
    public void setAllSize(int width, int height) {
        setAllSize(new java.awt.Dimension(width, height));
    }

    private void checkDesktopSize() {
        if (getParent() != null && isVisible()) {
            manager.resizeDesktop();
        }
    }

    public javax.swing.JInternalFrame PackUpFrame(javax.swing.JInternalFrame variable, String classFrame) throws java.lang.Exception {
        if (variable == null) {
            variable = (javax.swing.JInternalFrame) Class.forName(classFrame).newInstance();
            add(variable);

        } else if (variable.isClosed()) {
            variable = (javax.swing.JInternalFrame) Class.forName(classFrame).newInstance();
            add(variable);
        } else {
            moveToFront(variable);
        }

        return variable;
    }

    @Override
    protected void paintComponent(java.awt.Graphics graph) {
        super.paintComponent(graph);
        if(backImage != null) {
            if (mStrech)
                graph.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
            else
                graph.drawImage(backImage, (backImage.getWidth(this) >= getWidth() ? 0 : (getWidth() - backImage.getWidth(this)) / 2), (backImage.getHeight(this) >= getHeight() ? 0 : (getHeight() - backImage.getHeight(this)) / 2), backImage.getWidth(this), backImage.getHeight(this), this);
        }else
            graph.drawString("Background not found", getWidth() / 2 , getHeight() / 2);
    }

    public void setBackgroundImage(java.net.URL background) {
        backImage = new javax.swing.ImageIcon(background).getImage();
    }

    public void setBackgroundImage(java.net.URL background, boolean strech) {
        mStrech = strech;
        backImage = new javax.swing.ImageIcon(background).getImage();
    }


    public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle r, int axis, int dir) {
    return 50;
    }

    public int getScrollableBlockIncrement(Rectangle r, int axis, int dir) {
    return 200;
    }

    public boolean getScrollableTracksViewportWidth() {
    return false;
    }
    
    public boolean getScrollableTracksViewportHeight() {
    return false;
    }
}