/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;


public class MsgBox {
    
    public static final int QUESTION_MESSAGE = javax.swing.JOptionPane.QUESTION_MESSAGE;
    public static final int WARNING_MESSAGE = javax.swing.JOptionPane.WARNING_MESSAGE;
    public static final int YES_MESSAGE = javax.swing.JOptionPane.YES_OPTION;
    public static final int NO_MESSAGE = javax.swing.JOptionPane.NO_OPTION;

    public static int showYesNo(java.awt.Component component, String message, String title, int iconMsg) {
        //Custom button text
        Object[] options = {"Yes", "No"};

        return javax.swing.JOptionPane.showOptionDialog(
                component,
                message, title,
                javax.swing.JOptionPane.YES_NO_OPTION,
                iconMsg, //javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[options.length - 1]);
    }

    public static int showConfirm(java.awt.Component component, String message, String title) {
         return javax.swing.JOptionPane.showConfirmDialog(
                component,
                message, title,
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    public static void showInfo(java.awt.Component component, String message, String title) {
        javax.swing.JOptionPane.showMessageDialog(component, message, title, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(java.awt.Component component, String message, String title) {
        javax.swing.JOptionPane.showMessageDialog(component, message, title, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarning(java.awt.Component component, String message, String title) {
        javax.swing.JOptionPane.showMessageDialog(component, message, title, javax.swing.JOptionPane.WARNING_MESSAGE);
    }     


}
