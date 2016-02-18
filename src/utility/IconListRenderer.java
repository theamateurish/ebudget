/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author theamateurish
 */
public class IconListRenderer extends JLabel implements ListCellRenderer {
    private java.util.ArrayList <String>userType=new java.util.ArrayList<String>();
    private ImageIcon imageIcon;
    public IconListRenderer(java.util.ArrayList <String> privilege) {
        setOpaque(true);
        userType=privilege;
    }
    
    @Override
   public Component getListCellRendererComponent(
         JList list, Object value, int index,
         boolean isSelected, boolean hasFocus)
   {
      if(userType.size()>0){ 
        if(userType.get(0).equals("User") | userType.get(0).equals("Viewer")){
          imageIcon = new ImageIcon(getClass().getResource("/images/user.png"));
        }else if(userType.get(0).contains("Administrator")){
          imageIcon = new ImageIcon(getClass().getResource("/images/profile.png"));
        }
      }
      setIcon(imageIcon);
      setFont(list.getFont());
      setText(value.toString());
      if (isSelected) {
          setBackground(list.getSelectionBackground());
          setForeground(list.getSelectionForeground());
      } else {
          setBackground(list.getBackground());
          setForeground(list.getForeground());
      }
      return this;
   }
}