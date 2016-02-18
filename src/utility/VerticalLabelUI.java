/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author felixiong
 */
public class VerticalLabelUI extends javax.swing.plaf.basic.BasicLabelUI {

    static {
        labelUI = new VerticalLabelUI(false);
    }
    private boolean clockwise;

    public VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    @Override
    public java.awt.Dimension getPreferredSize(javax.swing.JComponent c) {
        java.awt.Dimension dim = super.getPreferredSize(c);
        return new java.awt.Dimension(dim.height, dim.width);
    }
    
    private static java.awt.Rectangle paintIconR = new java.awt.Rectangle();
    private static java.awt.Rectangle paintTextR = new java.awt.Rectangle();
    private static java.awt.Rectangle paintViewR = new java.awt.Rectangle();
    private static java.awt.Insets paintViewInsets = new java.awt.Insets(0, 0, 0, 0);

    @Override
    public void paint(java.awt.Graphics g, javax.swing.JComponent c) {


        javax.swing.JLabel label = (javax.swing.JLabel) c;
        String text = label.getText();
        javax.swing.Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }

        java.awt.FontMetrics fm = g.getFontMetrics();
        paintViewInsets = c.getInsets(paintViewInsets);

        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;

        // Use inverted height & width
        paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        java.awt.geom.AffineTransform tr = g2.getTransform();
        if (clockwise) {
            g2.rotate(Math.PI / 2);
            g2.translate(0, -c.getWidth());
        } else {
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
        }

        if (icon != null) 
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        

        if (text != null) {
            int textX = paintTextR.x;
            int textY = paintTextR.y + fm.getAscent();

            if (label.isEnabled()) {
                paintEnabledText(label, g, clippedText, textX, textY);
            } else {
                paintDisabledText(label, g, clippedText, textX, textY);
            }
        }

        g2.setTransform(tr);
    }
}
