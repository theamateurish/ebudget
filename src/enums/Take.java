/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package enums;

/**
 * Prepare an action taken on what are going to do before
 * firing up to a client-server database.
 * <br></br>
 * <br></br>
 * use for dbase.SQLExecute only on activy parameter.
 */
public enum Take {
    /**
     * Set a field to allow for Inser and Update.
     */
    InsertUpdate,


    /**
     * Set a field to allow for Update Only.
     */
    UpdateOnly,


    /**
     * Set a field to allow for Inser Only.
     */
    InsertOnly,


    /**
     * A field is use just for condition only
     */
    ConditionOnly
}
