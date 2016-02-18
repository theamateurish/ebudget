/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author felixiong
 */
public class BinaryExtractor {

    private final byte JPEG_HEAD1 = -1; //0xFF
    private final byte JPEG_HEAD2 = -40; //0xD8

    /**
     * @param pathFileName  a complete path and filename with extension of .jpg
     * @param data  an array of bytes downloaded from MSSQL Server 2000
     */
    public void ExtractBinaryRemoveHeader(String pathFileName, byte[] data) throws FileNotFoundException, IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(pathFileName);

        int xyz = 0;
        for (xyz = 0; xyz <= data.length; xyz++) {
            if ((data[xyz] == JPEG_HEAD1) & (data[xyz + 1] == JPEG_HEAD2)) {
                break;
            }
        }
        fos.write(data, xyz, data.length - xyz);
        fos.flush();
        fos.close();
    }

    public String ExtractBinary(String pathFileName, byte[] data) throws FileNotFoundException, IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(pathFileName);        
        fos.write(data);  
        fos.flush();
        fos.close();

        return pathFileName;
    }

    public byte[] UploadBinary(String pathFileName) throws java.io.FileNotFoundException, java.io.IOException {
        // Insert the image into the second Blob
        byte[] data;
        java.io.File file = new java.io.File(pathFileName);
        if (!file.exists()) 
            file.createNewFile();
        
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        
        return data;
    }
}
