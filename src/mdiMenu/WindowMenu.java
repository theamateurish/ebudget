package mdiMenu;

/**
 * Menu component that handles the functionality expected of a standard
 * "Windows" menu for MDI applications.
 */
public class WindowMenu { //extends javax.swing.JMenu

    private MDIDesktopPane desktop;
    private javax.swing.JMenu WindowMenu;
    //private int preserveItems;

    public WindowMenu(MDIDesktopPane desktop, javax.swing.JMenu popup, final int preserveItems) {
        this.desktop       = desktop;
        this.WindowMenu    = popup;
        //this.preserveItems = preserveItems;
        
        WindowMenu.addMenuListener(new javax.swing.event.MenuListener() {
            @Override
            public void menuCanceled(javax.swing.event.MenuEvent e) {
            }

            @Override
            public void menuDeselected(javax.swing.event.MenuEvent e) {
//                WindowMenu.removeAll();
                for (int idx = WindowMenu.getItemCount() - 1; idx >= preserveItems ; idx--)
                    WindowMenu.remove(idx);
            }

            @Override
            public void menuSelected(javax.swing.event.MenuEvent e) {
                buildChildMenus();
            }
        });
    }
    /* Sets up the children menus depending on the current desktop state */
    private void buildChildMenus() {
        int i;
        ChildMenuItem menu;
        javax.swing.JInternalFrame[] array = desktop.getAllFrames();
        WindowMenu.removeAll();
//        if (array.length > 0) {
//            WindowMenu.addSeparator();
//        }
        for (i = 0; i < array.length; i++) {
            menu = new ChildMenuItem(array[i]);
            menu.setState(i == 0);
            menu.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent ae) {
                    javax.swing.JInternalFrame frame = ((ChildMenuItem) ae.getSource()).getFrame();
                    frame.moveToFront();
                    try {
                        frame.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
            });
            if(array[i].isVisible()==true){
            menu.setIcon(array[i].getFrameIcon());
            WindowMenu.add(menu);
            }
        }
    }
}
