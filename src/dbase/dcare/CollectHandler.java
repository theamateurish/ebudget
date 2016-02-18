/*
 * AcctNUM.java
 *
 * Created on October 16, 2007, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dbase.dcare;

/**
 *
 * @author felixiong
 */
public class CollectHandler<E> {
    private transient E keyVal;   
    private transient E badgeVal;
    private transient E empVal;
    private transient E systemUser;
    private transient E modifiedDate;
    private String toolTip;
    
    /** Creates a new instance of AcctNUM */
    public CollectHandler(String tips) {
        toolTip = tips; }
    
    public String getToolTipText() {
        return toolTip;
    }
    
    public E getKeyValue() { return (E) keyVal; }
    public void setKeyValue(E keyno) {keyVal = keyno; }    

    public E getBadgeVal() {return (E) badgeVal; }
    public void setBadgeVal(E badgeNo) {badgeVal = badgeNo; }

    public E getEmpVal() {return (E) empVal;}
    public void setEmpVal(E empName) {empVal = empName;}
    
    public E getSystemUser() {return (E) systemUser;}
    public void setSystemUser(E sysUser) { sysUser = systemUser; }

    public E getModifiedDate() {return (E) modifiedDate;}
    public void setModifiedDate(E modDate) {modDate = modifiedDate; }   
       
    
}
