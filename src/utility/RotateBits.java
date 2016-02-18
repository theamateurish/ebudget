/*
 * RotateBits.java
 *
 * Created on February 26, 2007, 9:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utility;

/**
 *
 * @author thetiger
 */
public class RotateBits {
    static short ROTATION = 1;

    /**
     * Creates a new instance of RotateBits
     */
//    public RotateBits() {
//    }

    /** 
     * Rotates value n bits left through the sign.
     *
     * @param value Value to rotated.
     * @param n how many bits to rotate left.
     * must be in range 0..31
     * @return Value rotated.
     */
    static public char RotateLeft ( short value, short n ) {
        return (char) (( value << n ) | ( value >>> (8 - n) ));
    }

    /**
     * Rotates value n bits right through the sign.
     *
     * @param value Value to rotated.
     * @param n how many bits to rotate night.
     * must be in range 0..31
     * @return Value rotated.
     */
    static public char RotateRight ( short value, short n ) {
        return (char) (( value >>> n ) | ( value << (16 - n) ));
    }

    /**
     * Encrypt a string into special binary code
     * @param value Value to be encrypted.
     */
    static public String EncryptKey(String value) {
        char twist[] = value.toCharArray();
        
        for (int x = 0; x < twist.length; x++)
            twist[x] = RotateLeft((short)twist[x], ROTATION);

        return String.copyValueOf(twist);
    }
    
    static public String DecryptKey(String value) {
        char twist[] = value.toCharArray();
        
        for (int x = 0; x < twist.length; x++)
            twist[x] = RotateRight((short)twist[x], ROTATION);

        return String.copyValueOf(twist);
    }    
}
