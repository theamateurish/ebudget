package budget;

/**
 *
 * @author felix
 */
public class Budgetary {

    //private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Budgetary.class.getName());
    private static java.nio.channels.FileLock fileLock;
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static void main(String[] args) throws SecurityException, java.io.IOException, InterruptedException, java.lang.reflect.InvocationTargetException {

        /*com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER.log(java.util.logging.Level.INFO, "Logger Name: {0}", com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER.getName());

        com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER.warning("Can cause ArrayIndexOutOfBoundsException");

        //An array of size 3
        int[] a = {1, 2, 3};
        int index = 2;
        com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER.log(java.util.logging.Level.CONFIG, "index is set to {0}", index);

        try {
            System.out.println(a[index]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER.log(java.util.logging.Level.SEVERE, "Exception occur", ex);
        }*/
        
        System.setProperty("SERVER", args[0]);
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Budgetary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // If an instance of program is running, exit!
        try {
            fileLock = new java.io.FileOutputStream(System.getProperty("java.io.tmpdir") + "/budget.file").getChannel().tryLock();
            if (fileLock == null) {
                classes.MsgBox.showInfo(null, "Other Budget System application is already running.", "Previous Instance");
                System.exit(0);
            }

        } catch (java.io.IOException exc) {
            // Another process is listening on that port.
            // Probably anoher instance of your app.
            classes.MsgBox.showInfo(null, "Another Budget System application is running.", "Previous Instance");
            java.util.logging.Logger.getLogger(Budgetary.class.getName()).log(java.util.logging.Level.SEVERE, null, exc);
            return;
        }
        
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginDialog().setVisible(true);
            }
        });

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });        
    }
}
