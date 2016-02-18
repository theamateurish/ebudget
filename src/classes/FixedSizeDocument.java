/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.swing.text.*;


/**
 *
 * @author Owner
 */
public class FixedSizeDocument extends PlainDocument
{
   private int limit;
    // optional uppercase conversion
    private boolean toUppercase = false;

    public FixedSizeDocument(int limit) {
        super();
        this.limit = limit;
    }

    public FixedSizeDocument(int limit, boolean upper) {
        super();
        this.limit = limit;
        toUppercase = upper;
    }

    public void insertString
            (int offset, String  str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            if (toUppercase) str = str.toUpperCase();
            super.insertString(offset, str, attr);
        }
    }
}
