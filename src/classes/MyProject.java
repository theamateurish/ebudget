/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import lombok.Data;

/**
 *
 * @author Owner
 */
@Data
 public class MyProject {

        private int year; 
        private String title;
        private String author;

                public MyProject() {
                    year = 2015;
                    title = "FiMS";
                    author = "GRRellon";
                }
    }
