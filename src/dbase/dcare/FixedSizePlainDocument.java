/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbase.dcare;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author felixiong
 */
public class FixedSizePlainDocument extends PlainDocument {

    private int maxSize;

    public FixedSizePlainDocument(int limit) {
        maxSize = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if ((getLength() + str.length()) <= maxSize) {
            super.insertString(offs, str, a);
        } else {
            throw new BadLocationException("Insertion exceeds max size of document", offs);
        }
    }
}
