/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package treetable;

/**
 *
 * @author theamateurish
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class TreeTableBak {

	private String[] headings;
	private DefaultMutableTreeTableNode root;
	private DefaultTreeTableModel model;
	private JXTreeTable table;
        private Object[][] value,allot_class;
        
        
	public TreeTableBak(Object[][] value,String[] header) {
            this.value=value;
            headings=header;
         }

	public JXTreeTable getTreeTable() {
                
                //Create as many nodes as there are rows of data.
                ChildNode[] node = new ChildNode[value.length];
                
                
                for (int i = 0; i < value.length; i++) {        
                    node[i] = new ChildNode(new String[] {value[i][2].toString(),value[i][3].toString(),value[i][4].toString()}); 
                }
                
                root =  node[0];   //Set the root node
                
                //Cycle through the table above and assign nodes to nodes
                for (int i = 0; i < value.length; i++) {
                    for (int j = 1; j < value.length; j++) {
                            if (value[i][0].equals(value[j][1])) {
                                node[i].add(node[j]);  
                            }    
                    }   
                    if(node[i].isLeaf()){   
                        String[] fppcode=(String[]) node[i].getUserObject();
                        allot_class=new classes.AllotmentClass().allot_class(fppcode[1]);
                        for(int k=0;k<allot_class.length;k++){                                
                            node[i].add(new ChildNode(new String[]{allot_class[k][0].toString(),allot_class[k][1].toString(),allot_class[k][2].toString()}));                                   
                        }
                    }
                }
		model = new DefaultTreeTableModel(root,Arrays.asList(headings));
		table = new JXTreeTable(model);
		table.setShowGrid(true, true);
		table.setColumnControlVisible(true);

		table.packAll();

		return table;
	}
        

}
