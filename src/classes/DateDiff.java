/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;


import java.text.SimpleDateFormat;

/**
 *
 * @author Owner
 */
public class DateDiff {
    SimpleDateFormat dfyear=new SimpleDateFormat("yyyy");
    SimpleDateFormat dfmonth=new SimpleDateFormat("MM");
    int monthdiff=0;
    int yeardiff=0;

    public DateDiff(java.util.Date d1,java.util.Date d2){

        String yearfr=dfyear.format(d1);
        String yearto=dfyear.format(d2);
        String monthfr=dfmonth.format(d1);
        String monthto=dfmonth.format(d2);

        if(d1.before(d2)){
            yeardiff= Integer.parseInt(yearto) - Integer.parseInt(yearfr);
            if(Integer.parseInt(monthto)>Integer.parseInt(monthfr)){
                monthdiff= (yeardiff * 12) + Math.abs(Integer.parseInt(monthfr) - Integer.parseInt(monthto));
            }else if(Integer.parseInt(monthto)<=Integer.parseInt(monthfr)){
                monthdiff= (yeardiff * 12) - Math.abs(Integer.parseInt(monthfr) - Integer.parseInt(monthto));
            }
        }

    }

    public int getMonth(){return monthdiff;}

    public int getYear(){return yeardiff;}
   
}
