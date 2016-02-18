/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package treetable;

/**
 *
 * @author theamateurish
 */
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

public class Node extends DefaultMutableTreeTableNode {

	public Node(Object data) {
		super(data);
	}

	@Override
	public int getColumnCount() {
		return getData().length;
	}

	@Override
	public Object getValueAt(int columnIndex) {
		return getData()[columnIndex];
	}

	public Object[] getData() {
		return (Object[]) getUserObject();
	}

}
