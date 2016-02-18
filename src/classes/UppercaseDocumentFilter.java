/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
/**
 *
 * @author Owner
 */
public class UppercaseDocumentFilter extends DocumentFilter {
        //
        // Override insertString method of DocumentFilter to make the text format
        // to uppercase.
        //
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                                 String text, AttributeSet attr)
                throws BadLocationException {

            fb.insertString(offset, text.toUpperCase(), attr);
        }

        //
       // Override replace method of DocumentFilter to make the text format
        // to uppercase.
        //
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs)
                throws BadLocationException {

            fb.replace(offset, length, text.toUpperCase(), attrs);
       }
   }
