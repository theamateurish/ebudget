/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbase.dcare;

/**
 *
 * @author Linux
 * This has to be Improve in the near future.
 * This will be planned to integrate with JComboBox
 */
public class ItemDataList<O> extends java.util.ArrayList<O>{
    
    private int keyVal = -1;
    private String toolTip;
    
    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ItemDataList(java.util.Collection<? extends O> c) { super(c); }

    public ItemDataList() {super(); }
    
    public ItemDataList(int initialCapacity) { super(initialCapacity);}

    public String getToolTipText() { return toolTip; }

    public int getListIndex() { return keyVal; }
    
    @Override
    public O get(int index) { keyVal = index; return super.get(index); }

    @Override
    public O set(int index, O element) { return super.set(index, element); }
}
