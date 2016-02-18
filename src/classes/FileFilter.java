/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;

/**
 *
 * @author Administrator
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bat");
    }

    public String getDescription() {
        return "BATCH files";
    }
}
