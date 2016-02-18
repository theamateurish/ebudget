/*
 * DataHandler.java
 *
 * Created on September 17, 2007, 3:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dbase;

import com.imagine.component.calendar.CalendarComboBox;
import datechooser.beans.DateChooserCombo;
import dbase.dcare.CollectHandler;
import utility.JspCalendar;


public class DataHandler {

    /** Return value of zero (0). */
    //public static final short ixIndexOff = 0;
    /** Return value of one (1). */
    //public static final short ixAutoIndex = 1;
    /** Return value of two (2). */
    //public static final short ixIndexOn = 2;
    
//    public static final short DoNotAlter = 0,
//                              UpdateOnly = 1,
//                              InsertOnly = 2,
//                              InsertUpdate = 3;
    
    private String datePattern;

    public void setDatePattern(String value) {datePattern = value;}
    
    private java.util.ArrayList<Object> CtrlObj = new java.util.ArrayList<Object>();
    private java.util.ArrayList<String> FieldName = new java.util.ArrayList<String>();
    private java.util.ArrayList<Object> Initial = new java.util.ArrayList<Object>();
    private java.util.ArrayList<enums.Index> IdxType = new java.util.ArrayList<enums.Index>();
    //private short IdxType[];
    private java.util.ArrayList<enums.Behave> Behavior = new java.util.ArrayList<enums.Behave>();
    //private short Behavior[];
    private boolean InNeed[];
    private String TableName;
    private boolean NotAlpha[];

    /** Creates a new instance of DataHandler */
    public DataHandler(String tableName) {TableName = tableName;}
    public String getTableName() {return TableName;}
    public void setTableName(String tables) {TableName = tables;}
    
//     * <pre>
//     *      ixIndexOn - is an index field
//     *      ixAutoIndex - automatic generate index key after save.
//     *      ixIndexOff - is not an index field
//     * </pre>
    /**
     * @param <code>ctrlObj</code> Control Object
     * @param <code>fieldName</code> Field Name
     * @param <code>idxType</code> Type of Index
     * @param <code>behavior</code> Represent a bounded control link to a fieldName, therefore when save the data will written to a field.
     * @param <code>Required</code> an error occured when to save if a control that handle this is empty.
     * @param <code>notAlpha</code> a value of this field contain a numeric.
     * @param <code>initVal</code> an initial value.
    */
    public void AddControl(Object ctrlObj, String fieldName, enums.Index idxType, enums.Behave behavior, boolean Required, boolean notAlpha, Object initVal) {
        CtrlObj.add(ctrlObj);
        FieldName.add(fieldName);
        Initial.add(initVal);

        /*if (IdxType == null) {
            IdxType = (short[]) java.lang.reflect.Array.newInstance(short.class, 1);
        } else {
            IdxType = (short[]) resizeArray(IdxType, IdxType.length + 1);
        }*/

        /*if (Behavior == null) {
            Behavior = (short[]) java.lang.reflect.Array.newInstance(short.class, 1);
        } else {
            Behavior = (short[]) resizeArray(Behavior, Behavior.length + 1);
        }*/

        if (InNeed == null) {
            InNeed = (boolean[]) java.lang.reflect.Array.newInstance(boolean.class, 1);
        } else {
            InNeed = (boolean[]) resizeArray(InNeed, InNeed.length + 1);
        }

        if (NotAlpha == null) {
            NotAlpha = (boolean[]) java.lang.reflect.Array.newInstance(boolean.class, 1);
        } else {
            NotAlpha = (boolean[]) resizeArray(NotAlpha, NotAlpha.length + 1);
        }

        IdxType.add(idxType);
        //IdxType[IdxType.length - 1] = index;
        Behavior.add((idxType == enums.Index.AutoIndex ? enums.Behave.DoNotAlter : behavior));
        //Behavior[Behavior.length - 1] = (idxType == enums.Index.AutoIndex ? 0 : behavior);
        InNeed[InNeed.length - 1] = (idxType == enums.Index.AutoIndex ? false : Required);
        NotAlpha[NotAlpha.length - 1] = notAlpha;

        //JTextField TextField;
        //JComboBox ComboBox;
        //CalendarComboBox TextDate;
        //JRadioButton RadioButton;
        //JFormattedTextField FormatField;
        String className  = ctrlObj.getClass().getSimpleName(); //.equals("JComboBox");
        if (className.equals("JTextField")) {
            javax.swing.JTextField TextField = (javax.swing.JTextField) ctrlObj;
            if (TextField.getToolTipText() == null & behavior != enums.Behave.DoNotAlter)
                throw new FieldChecker("Field [" + fieldName + "] require Tooltip.");
            else
                TextField.setText(String.valueOf(initVal));

        } else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
            javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) ctrlObj;
            if (ComboBox.getToolTipText() == null & behavior != enums.Behave.DoNotAlter)
                throw new FieldChecker("Field [" + fieldName + "] require Tooltip.");
            else if (ComboBox.getName().equals("index")) // notAlpha)
                ComboBox.setSelectedIndex(Integer.parseInt(initVal.toString()));
            else
                ComboBox.setSelectedItem(initVal);

        } else if (className.equals("CalendarComboBox")) {
            CalendarComboBox TextDate = (CalendarComboBox) ctrlObj;
            if (TextDate.getToolTipText() == null & behavior != enums.Behave.DoNotAlter)
                throw new FieldChecker("Field [" + fieldName + "] require Tooltip.");
            else
                TextDate.setDate((java.util.Date) initVal);
//                TextDate.setDate((java.util.Date) (initVal.equals("1/01/1900")?"":initVal));

        } else if (className.equals("DateChooserCombo")) {
            DateChooserCombo ChoseDate = (DateChooserCombo) ctrlObj;
            if (ChoseDate.getToolTipText() == null & behavior != enums.Behave.DoNotAlter)
                throw new FieldChecker("Field [" + fieldName + "] require Tooltip.");
            else {
                if (initVal instanceof String) {
                    ChoseDate.setText("1/01/1900".equals(initVal.toString())?"":initVal.toString());
                }
                else {
                    ChoseDate.setText(("1/01/1900".equals(new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)ChoseDate.getDateFormat()).toPattern()).format(initVal))?"":new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)ChoseDate.getDateFormat()).toPattern()).format(initVal)));
                }
            }
            
        
        } else if (className.equals("JRadioButton")) {
            javax.swing.JRadioButton RadioButton = (javax.swing.JRadioButton) ctrlObj;
            if (RadioButton.getToolTipText() == null & behavior != enums.Behave.DoNotAlter)
                throw new FieldChecker("Field [" + fieldName + "] require Tooltip.");
            else
                RadioButton.setSelected(((Boolean) initVal).booleanValue());


        } else if (className.equals("JFormattedTextField")) {
            ((javax.swing.JFormattedTextField) ctrlObj).setValue(initVal);
            
            
        } else if (className.equals("CollectHandler")) {
            ((CollectHandler)ctrlObj).setKeyValue(initVal);
        }
    }

    public void ClearContent(boolean doNotClearIndex) {
        javax.swing.JComboBox ComboBox;

        for (int xyz = 0; xyz < CtrlObj.size(); xyz++) {
            COME_BACK:
            {
                if (doNotClearIndex)
                    if (IdxType.get(xyz) != enums.Index.IndexOff) //== DataHandler.ixAutoIndex) || (IdxType[xyz] == DataHandler.ixIndexOn))
                        break COME_BACK;
                
                Object ctrlCare = CtrlObj.get(xyz);
                String className  = ctrlCare.getClass().getSimpleName();
                if (className.equals("JTextField"))
                    ((javax.swing.JTextField) ctrlCare).setText(Initial.get(xyz).toString());

                else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
                    ComboBox = (javax.swing.JComboBox) ctrlCare;
                    
                    if (ComboBox.getAccessibleContext().getAccessibleName() == null)
                        if (ComboBox.getItemCount() > 0) ComboBox.setSelectedIndex(0);

                    else if (ComboBox.getAccessibleContext().getAccessibleName().equals("clear"))
                        ComboBox.removeAllItems();

                    if (ComboBox.getName().equals("data"))
                        //if (ComboBox.getItemCount() > 0) ComboBox.setSelectedIndex(0);
                        ComboBox.getModel().setSelectedItem(Initial.get(xyz));
                    else
                        if (ComboBox.getItemCount() > 0) ComboBox.setSelectedIndex(0);
                                        
                } else if (className.equals("CalendarComboBox"))
                    ((CalendarComboBox) ctrlCare).setDate((java.util.Date) Initial.get(xyz));
                
                else if (className.equals("CollectHandler")) {
                    if (Initial.get(xyz)instanceof String)
                        ((CollectHandler<String>) ctrlCare).setKeyValue(Initial.get(xyz).toString());
                    else if (Initial.get(xyz)instanceof Integer)
                        ((CollectHandler<Integer>) ctrlCare).setKeyValue(Integer.valueOf(Initial.get(xyz).toString()));
                    else if (Initial.get(xyz)instanceof Short)
                        ((CollectHandler<Short>) ctrlCare).setKeyValue(Short.valueOf(Initial.get(xyz).toString()));
                    
                } else if (className.equals("JFormattedTextField"))
                    ((javax.swing.JFormattedTextField) ctrlCare).setValue(Initial.get(xyz));
                
                else if (className.equals("JSpinner"))
                    ((javax.swing.JSpinner) ctrlCare).setValue(Initial.get(xyz));
                
                else if (className.equals("DateChooserCombo")) {
                    if (Initial.get(xyz) instanceof String)
                        ((DateChooserCombo) ctrlCare).setText(Initial.get(xyz).toString());
                    else
                        ((DateChooserCombo) ctrlCare).setText(new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)((DateChooserCombo) ctrlCare).getDateFormat()).toPattern()).format(Initial.get(xyz)));
                        //((DateChooserCombo) ctrlCare).setText(Initial.get(xyz).toString());
                } else if (className.equals("JTextArea"))
                    ((javax.swing.JTextArea) ctrlCare).setText((Initial.get(xyz).toString()));

            }
        }
    }

    private Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);

        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }

        return newArray;
    }

    public String FieldList() {
        String fields = "";
        for (int xyz = 0; xyz < FieldName.size(); xyz++)
            fields += FieldName.get(xyz) + ", ";
        
        return fields.substring(0, (fields.length() - 2));
    }
    
    public boolean InvalidEntry() {
        boolean dataEmpty = false;
        String  toolTip   = "";
        for (int xyz = 0; xyz < CtrlObj.size(); xyz++) {
            if (Behavior.get(xyz) != enums.Behave.DoNotAlter) {
                Object ctrlCare = CtrlObj.get(xyz);
                String className  = ctrlCare.getClass().getSimpleName();
                if (className.equals("JTextField")) {
                    javax.swing.JTextField TextField = (javax.swing.JTextField) ctrlCare;
                    if (InNeed[xyz])
                        dataEmpty = TextField.getText().isEmpty();
                    else 
                        dataEmpty = false;
                    toolTip   = TextField.getToolTipText();


                } else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
                    javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) ctrlCare;
                    if (InNeed[xyz])
                        if (ComboBox.getName() != null) 
                            dataEmpty = String.valueOf(ComboBox.getSelectedItem()).isEmpty();
                        else
                            dataEmpty = (ComboBox.getSelectedIndex() < 0);
                    else
                        dataEmpty = false;
                    toolTip = ComboBox.getToolTipText();

                } else if (className.equals("CollectHandler")) {
                    CollectHandler collection = (CollectHandler) ctrlCare;
                    if (InNeed[xyz])
                        if (collection.getKeyValue() instanceof String)
                            dataEmpty = ((CollectHandler<String>)collection).getKeyValue().isEmpty();
                        else if (collection.getKeyValue() instanceof Object)
                            dataEmpty = ((CollectHandler<Object>)collection).getKeyValue() == null;
                        else
                            dataEmpty = false;
                    else
                        dataEmpty = false;
                    toolTip = collection.getToolTipText();

                } else if (className.equals("JTextArea")) {
                    javax.swing.JTextArea TextArea = (javax.swing.JTextArea) ctrlCare;
                    if (InNeed[xyz])
                        dataEmpty = TextArea.getText().isEmpty();
                    else
                        dataEmpty = false;
                    toolTip = TextArea.getToolTipText();

                } else if (className.equals("JFormattedTextField")) {
                    javax.swing.JFormattedTextField TextFormat = (javax.swing.JFormattedTextField) ctrlCare;
                    if (InNeed[xyz])
                        dataEmpty = TextFormat.getText().isEmpty();
                    else
                        dataEmpty = false;
                    toolTip = TextFormat.getToolTipText();
                }
                if (dataEmpty) {
                    javax.swing.JOptionPane.showMessageDialog(null, toolTip + " is required.", "Verifying Fields", javax.swing.JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
        
        return dataEmpty;
    }
    
    private boolean IsNumeric(Object obj, Class<? extends Object> clazz) {
        try {
            if (clazz.equals(Byte.class))
                return Byte.parseByte(obj.toString()) == 0;
            
            else if (clazz.equals(Double.class))
                return Double.parseDouble(obj.toString()) == 0.00;
            
            else if (clazz.equals(Float.class))
                return Float.parseFloat(obj.toString()) == 0.00f;
            
            else if (clazz.equals(Integer.class))
                return Integer.parseInt(obj.toString()) == 0;
            
            else if (clazz.equals(Long.class))
                return Long.parseLong(obj.toString()) == 0;
            
            else if (clazz.equals(Short.class))
                return Short.parseShort(obj.toString()) == 0;
            
            else if (clazz.equals(Boolean.class))
                return false; //Boolean.parseBoolean(str);

            else
                return true;

        } catch (NumberFormatException nfe) {
            java.util.logging.Logger.getLogger(DataHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, nfe);
            return false;
        }
    }

    public String Criteria() {
        String temp = "";
        javax.swing.JTextField TextField;
        //JFormattedTextField TextDate;
        CalendarComboBox TextDate;
        CollectHandler collection;

        for (int xyz = 0; xyz < IdxType.size(); xyz++) {
            //if ((IdxType.get(xyz) == enums.Index.AutoIndex) || (IdxType[xyz] == DataHandler.ixIndexOn)) { //&& IsBound[xyz]) {
            if (IdxType.get(xyz) != enums.Index.IndexOff) {
                Object ctrlCare = CtrlObj.get(xyz);
                String className  = ctrlCare.getClass().getSimpleName();
                if (className.equals("JTextField")) {
                    TextField = (javax.swing.JTextField) ctrlCare;
                    if (!TextField.getText().isEmpty()) 
                        temp += "(" + FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + TextField.getText() + (NotAlpha[xyz] ? ") and " : "') and ");


                } else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
                    javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) ctrlCare;
                    if (ComboBox.getName().equals("index"))
                        temp += "(" + FieldName.get(xyz) + " = " + ComboBox.getSelectedIndex() + ") and ";
                    else
                        temp += "(" + FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + ComboBox.getSelectedItem() + (NotAlpha[xyz] ? "" : "'") + ") and ";

                } else if (className.equals("CalendarComboBox")) {//JFormattedTextField")) {
                    //TextDate = (JFormattedTextField) ctrlCare;
                    TextDate = (CalendarComboBox) ctrlCare;
                    temp += "(" + FieldName.get(xyz) + " = '" + new JspCalendar(TextDate.getDate()).getDateFormated(datePattern) + "') and ";

                } else if (className.equals("JSpinner")) {
                    javax.swing.JSpinner Spinner = (javax.swing.JSpinner) ctrlCare;
                    if (Spinner.getEditor().getClass().getSimpleName().equalsIgnoreCase("DateEditor")) {
                        String pattern = ((javax.swing.JSpinner.DateEditor)Spinner.getEditor()).getFormat().toPattern();
                        temp += "(" + FieldName.get(xyz) + " = '" + new java.text.SimpleDateFormat(pattern).format(Spinner.getValue()) + "') and ";
                    } else
                        temp += "(" + FieldName.get(xyz) + " = " + Spinner.getValue() + ") and ";

                } else if (className.equals("JFormattedTextField")) {
                    javax.swing.JFormattedTextField Formatted = (javax.swing.JFormattedTextField) ctrlCare;
                    temp += "(" + FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + Formatted.getValue() + (NotAlpha[xyz] ? "" : "'") + ") and ";
                    
                    
                } else if (className.equals("CollectHandler")) {
                    collection = (CollectHandler) ctrlCare;
                    temp += "(" + FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + collection.getKeyValue() + (NotAlpha[xyz] ? "" : "'") + ") and ";
                }
            }
        }
        return temp.substring(0, (temp.length() - 5));
    }

    public String SaveRecord() {
        String data = "", field = "";

        for (int xyz = 0; xyz < IdxType.size(); xyz++) {
            if ((IdxType.get(xyz) != enums.Index.AutoIndex) &&
                (Behavior.get(xyz) == enums.Behave.InsertOnly | Behavior.get(xyz) == enums.Behave.InsertUpdate)) {
                Object ctrlCare = CtrlObj.get(xyz);
                String className  = ctrlCare.getClass().getSimpleName();
                if (className.equals("JTextField")) {
                    javax.swing.JTextField TextField = (javax.swing.JTextField) ctrlCare;
                    if (InNeed[xyz] || !TextField.getText().isEmpty()) {
                        field += FieldName.get(xyz) + ", ";
                        data += (NotAlpha[xyz] ? "" : "'") + TextField.getText().replaceAll("'", "''").trim() + (NotAlpha[xyz] ? "" : "'") + ", ";
                    }
  
                } else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
                    javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) ctrlCare;
                    if (InNeed[xyz] || !ComboBox.getSelectedItem().toString().isEmpty()) {
                        field += FieldName.get(xyz) + ", ";
                        if (ComboBox.getName().equalsIgnoreCase("data"))
                            data += (NotAlpha[xyz] ? "" : "'") + ComboBox.getSelectedItem().toString().replaceAll("'", "''") + (NotAlpha[xyz] ? "" : "'") + ", ";
                        else
                            data += ComboBox.getSelectedIndex() + ", ";
                    }

                } else if (className.equals("CalendarComboBox")) {
                    CalendarComboBox TextDate = (CalendarComboBox) ctrlCare;
                    if (InNeed[xyz]) {// || !(new java.text.SimpleDateFormat(datePattern).format((java.util.Date)Initial.get(xyz))).equalsIgnoreCase(new java.text.SimpleDateFormat(datePattern).format(TextDate.getDate().getTime()))) {
                        field += FieldName.get(xyz) + ", ";
                        data += "'" + new java.text.SimpleDateFormat(datePattern).format(TextDate.getDate()) + "', ";
                    }
                    
                } else if (className.equals("DateChooserCombo")) {
                    DateChooserCombo ChoseDate = (DateChooserCombo) ctrlCare;
                    if (InNeed[xyz] | !ChoseDate.getText().isEmpty()) {
                        field += FieldName.get(xyz) + ", ";
                        data += "'" + ChoseDate.getText() + "', ";
                    }

                } else if (className.equals("CollectHandler")) {
                    CollectHandler collection = (CollectHandler) ctrlCare;
                    if (InNeed[xyz] || !IsNumeric(collection.getKeyValue(), collection.getKeyValue().getClass())) {
                        field += FieldName.get(xyz) + ", ";
                        data += (NotAlpha[xyz] ? "" : "'") + collection.getKeyValue() + (NotAlpha[xyz] ? "" : "'") + ", ";
                    }

                } else if (className.equals("JRadioButton")) {
                    javax.swing.JRadioButton RadioButton = (javax.swing.JRadioButton) ctrlCare;
                    //if (InNeed[xyz]) {
                        field += FieldName.get(xyz) + ", ";
                        data += (RadioButton.isSelected() ? 1 : 0) + ", ";
                    //}

                } else if (className.equals("JCheckBox")) {
                    javax.swing.JCheckBox CheckBox = (javax.swing.JCheckBox) ctrlCare;
                    //if (InNeed[xyz]) {
                        field += FieldName.get(xyz) + ", ";
                        data += (CheckBox.isSelected() ? 1 : 0) + ", ";

                } else if (className.equals("JTextArea")) {
                    javax.swing.JTextArea TextArea = (javax.swing.JTextArea) ctrlCare;
                    if (InNeed[xyz] || !TextArea.getText().isEmpty()) {
                        field += FieldName.get(xyz) + ", ";
                        data += (NotAlpha[xyz] ? "" : "'") + TextArea.getText().replaceAll("'", "''") + (NotAlpha[xyz] ? "" : "'") + ", ";
                    }

                } else if (className.equals("JFormattedTextField")) {
                    javax.swing.JFormattedTextField TextFormat = (javax.swing.JFormattedTextField) ctrlCare;
                    if (InNeed[xyz] || TextFormat.getValue() != null) { //!TextFormat.getText().isEmpty()) {
                        field += FieldName.get(xyz) + ", ";
                        data += (NotAlpha[xyz] ? "" : "'") + TextFormat.getValue() + (NotAlpha[xyz] ? "" : "'") + ", ";
                    }
                    
                } else if (className.equals("JSpinner")) {
                    javax.swing.JSpinner Spinner = (javax.swing.JSpinner) ctrlCare;
                    //if (InNeed[xyz]) {
                    field += FieldName.get(xyz) + ", ";
                    if (Spinner.getEditor().getClass().getSimpleName().equalsIgnoreCase("DateEditor")) {
                        String pattern = ((javax.swing.JSpinner.DateEditor)Spinner.getEditor()).getFormat().toPattern();
                        data += "'" + new java.text.SimpleDateFormat(pattern).format(Spinner.getValue()) + "', ";
                    } else //if (Spinner.getEditor().getClass().getSimpleName().equalsIgnoreCase("NumberEditor"))
                        data += Spinner.getValue() + ", ";
                }
            }
        }
        field = field.substring(0, (field.length() - 2));
        data = data.substring(0, (data.length() - 2));
        return "insert into " + TableName + "(" + field + ") values (" + data + ")";
    }

    public String UpdateRecord() {
        String temp = "";

        for (int xyz = 0; xyz < IdxType.size(); xyz++) {
            if (IdxType.get(xyz) == enums.Index.AutoIndex) { //|| (IdxType[xyz] == DataHandler.ixIndexOn)) {
            /**
             *
             * NOTHING CAN DO THIS LINE
             *
            */
            } else if (Behavior.get(xyz) == enums.Behave.UpdateOnly | Behavior.get(xyz) == enums.Behave.InsertUpdate) {
                Object ctrlCare = CtrlObj.get(xyz);
                String className  = ctrlCare.getClass().getSimpleName();
                if (className.equals("JTextField")) {
                    javax.swing.JTextField TextField = (javax.swing.JTextField) ctrlCare;
                    //if (InNeed[xyz] || !TextField.getText().isEmpty())
                    if (!TextField.getText().isEmpty())
                        temp += FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + TextField.getText().replaceAll("'", "''").trim() + (NotAlpha[xyz] ? "" : "'") + ", ";


                } else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo")) {
                    javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) ctrlCare;
                    //if (InNeed[xyz] || !ComboBox.getSelectedItem().toString().isEmpty()) {
                    if (ComboBox.getName().equalsIgnoreCase("data")) {
                        if (!ComboBox.getSelectedItem().toString().isEmpty())
                            temp += FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + ComboBox.getSelectedItem().toString().replaceAll("'", "''") + (NotAlpha[xyz] ? "" : "'") + ", ";
                    } else
                        temp += FieldName.get(xyz) + " = " + ComboBox.getSelectedIndex() + ", ";
                    //}

                } else if (className.equals("CalendarComboBox")) {
                    CalendarComboBox TextDate = (CalendarComboBox) ctrlCare;
                    //if (InNeed[xyz]) // || !(new java.text.SimpleDateFormat(datePattern).format((java.util.Date)Initial.get(xyz))).equalsIgnoreCase(new java.text.SimpleDateFormat(datePattern).format(TextDate.getDate())))
                    temp += FieldName.get(xyz) + " = '" + new java.text.SimpleDateFormat(datePattern).format(TextDate.getDate()) + "', ";

                } else if (className.equals("DateChooserCombo")) {
                    DateChooserCombo ChoseDate = (DateChooserCombo) ctrlCare;
                    if (InNeed[xyz] | !ChoseDate.getText().isEmpty())
                        temp += FieldName.get(xyz) + " = '" + ChoseDate.getText() + "', ";

                } else if (className.equals("JRadioButton")) {
                    javax.swing.JRadioButton RadioButton = (javax.swing.JRadioButton) ctrlCare;
                    //if (InNeed[xyz])
                        temp += FieldName.get(xyz) + " = " + (RadioButton.isSelected() ? 1 : 0) + ", ";

                } else if (className.equals("JCheckBox")) {
                    javax.swing.JCheckBox CheckBox = (javax.swing.JCheckBox) ctrlCare;
                        temp += FieldName.get(xyz) + " = " + (CheckBox.isSelected() ? 1 : 0) + ", ";

                } else if (className.equals("JTextArea")) {
                    javax.swing.JTextArea TextArea = (javax.swing.JTextArea) ctrlCare;
                    //if (InNeed[xyz] || !TextArea.getText().isEmpty())
                    if (!TextArea.getText().isEmpty())
                        temp += FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'") + TextArea.getText().replaceAll("'", "''") + (NotAlpha[xyz] ? "" : "'") + ", ";

                } else if (className.equals("CollectHandler")) {
                    CollectHandler collection = (CollectHandler) ctrlCare;
                    //if (InNeed[xyz] || !IsNumeric(collection.getKeyValue(), collection.getKeyValue().getClass()))
                    if (NotAlpha[xyz])
                        temp += FieldName.get(xyz) + " = " + collection.getKeyValue() + ", ";
                    else {
                        if (!collection.getKeyValue().toString().isEmpty())
                            temp += FieldName.get(xyz) + " = '" + collection.getKeyValue() + "', ";
                    }
                } else if (className.equals("JFormattedTextField")) {
                    javax.swing.JFormattedTextField TextFormat = (javax.swing.JFormattedTextField) ctrlCare;
                    if (InNeed[xyz] || TextFormat.getValue() != null) //!TextFormat.getText().isEmpty())
                        temp += FieldName.get(xyz) + " = " + (NotAlpha[xyz] ? "" : "'")  + TextFormat.getValue() + (NotAlpha[xyz] ? "" : "'")  + ", ";
                    //else
                    //    temp += FieldName.get(xyz) + " = 0, ";

                } else if (className.equals("JSpinner")) {
                    javax.swing.JSpinner Spinner = (javax.swing.JSpinner) ctrlCare;
                    //if (InNeed[xyz])
                    if (Spinner.getEditor().getClass().getSimpleName().equalsIgnoreCase("DateEditor")) {
                        String pattern = ((javax.swing.JSpinner.DateEditor)Spinner.getEditor()).getFormat().toPattern();
                        temp += FieldName.get(xyz) + " = '" + new java.text.SimpleDateFormat(pattern).format(Spinner.getValue()) + "', ";
                    } else //if (Spinner.getEditor().getClass().getSimpleName().equalsIgnoreCase("NumberEditor"))
                        temp += FieldName.get(xyz) + " = " + Spinner.getValue() + ", ";

                }
            }
        }
        return "update " + TableName + " set " + temp.substring(0, (temp.length() - 2)) + " where " + this.Criteria();
    }

    public boolean DataOpener(java.sql.ResultSet rst) throws java.sql.SQLException, java.text.ParseException {
        if (rst.next())
            ClearContent(true);
        else
            return false;

        int columns = rst.getMetaData().getColumnCount();
        int arColumn[] = new int[columns];
        //boolean checker = false;

        //if (rst.next()) {
            for (int xyz = 1; xyz <= columns; xyz++) {
                if (arColumn[xyz - 1] == 0) {
                    arColumn[xyz - 1] = rst.getMetaData().getColumnType(xyz);
                }
                COME_BACK: {
                    if (rst.getObject(xyz) == null) {// Dinhi ra pwedi e validate kun null ba na o dili.
                        break COME_BACK;
                    }
                    
                        
                    switch (arColumn[xyz - 1]) {

                        case java.sql.Types.BIT:
                            javax.swing.JRadioButton RadioButton = (javax.swing.JRadioButton) CtrlObj.get(xyz - 1);
                            RadioButton.setSelected(rst.getBoolean(xyz));
                            break;

                        default:
                            //System.out.println("Default: field type - " + arColumn[xyz - 1] + "; field value - " + rst.getString(xyz) + "; field name - " + rst.getMetaData().getColumnName(xyz));
                            if (IdxType.get(xyz - 1) == enums.Index.IndexOff)
                                KnowingTheObject(CtrlObj.get(xyz - 1), rst.getObject(xyz));
                    }
                }
            }
            //checker = true;
        //}
        return true;
    }
    private void KnowingTheObject(Object controls, Object values) throws java.text.ParseException {
        /**
         * //if (values == null) return;
         * 
         * Null validation dili e specify diri ky naay uban mga objects nga 
         * wla nagkinahanglan ani na procedure.
         * Kini na procedure tawagon lamang kun ang klasi sa object dili
         * specific. Ang object na my specific lamang katong mga object sama sa
         * check box, radio button, og toggle button na naghupot lamang og duha
         * ka value na true o false ba.
         */
        String className  = controls.getClass().getSimpleName();
        if (className.equals("JFormattedTextField"))
            ((javax.swing.JFormattedTextField)controls).setValue(values);
            
        else if (className.equals("JTextField"))
            ((javax.swing.JTextField)controls).setText(String.valueOf(values));
            
        else if (className.equals("JComboBox") | className.equals("AutoCompleteCombo"))
        {
            javax.swing.JComboBox ComboBox = (javax.swing.JComboBox) controls;
            if (ComboBox.getName().equalsIgnoreCase("data"))
                ComboBox.setSelectedItem(values);
            else
                ComboBox.setSelectedIndex(Integer.parseInt(values.toString()));
            
        }
        else if (className.equals("JTextArea"))
            ((javax.swing.JTextArea)controls).setText(String.valueOf(values));
            
        else if (className.equals("JSpinner"))
        {
            javax.swing.JSpinner Spinner = (javax.swing.JSpinner) controls;
            if (values != null) 
                Spinner.setValue(values);
            else
                Spinner.setValue(0);
            
        }
        else if (className.equals("CalendarComboBox"))
            ((CalendarComboBox)controls).setDate(new java.text.SimpleDateFormat(datePattern).parse(values.toString(), new java.text.ParsePosition(0)));
            
        else if (className.equals("DateChooserCombo")) {
//            if (values.getClass().getSimpleName().equalsIgnoreCase("PGobject")) {// (IsDate(values, values.getClass())) {
//                org.postgresql.util.PGobject pgobject = (org.postgresql.util.PGobject)values;
//                String newval;
//                if (pgobject.getType().equalsIgnoreCase("abstime"))
//                    if (pgobject.getValue().indexOf('+') > 0)
//                        newval = new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)((DateChooserCombo)controls).getDateFormat()).toPattern()).format(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss+SS").parse(values.toString()));
//                    else
//                        newval = new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)((DateChooserCombo)controls).getDateFormat()).toPattern()).format(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SS").parse(values.toString()));
//                else
//                    newval = values.toString();
//                ((DateChooserCombo)controls).setText(newval);
//                
            if ((values instanceof java.sql.Timestamp) ||
                       (values instanceof java.sql.Time) ||
                       (values instanceof java.sql.Date))
                ((DateChooserCombo)controls).setText(new java.text.SimpleDateFormat(((java.text.SimpleDateFormat)((DateChooserCombo)controls).getDateFormat()).toPattern()).format(values));
            else 
                ((DateChooserCombo)controls).setText(values.toString());

        }
        else if (className.equals("CollectHandler"))
        {
            if (values.getClass().getSimpleName().equalsIgnoreCase("Timestamp"))
                ((CollectHandler<String>)controls).setKeyValue(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(values));
            else if (values.getClass().getSimpleName().equalsIgnoreCase("Time"))
                ((CollectHandler<String>)controls).setKeyValue(new java.text.SimpleDateFormat("HH:mm:ss.SSS").format(values));
            else if (values.getClass().getSimpleName().equalsIgnoreCase("Date"))
                ((CollectHandler<String>)controls).setKeyValue(new java.text.SimpleDateFormat("yyyy-MM-dd").format(values));
            else if (values instanceof Short)
                ((CollectHandler<Short>)controls).setKeyValue(Short.parseShort(values.toString()));    
            else if (values instanceof Integer)
                ((CollectHandler<Integer>)controls).setKeyValue(Integer.parseInt(values.toString()));    
            else if (values instanceof Long)
                ((CollectHandler<Long>)controls).setKeyValue(Long.parseLong(values.toString()));    
            else if (values instanceof String)
                ((CollectHandler<String>)controls).setKeyValue(values.toString());
            else
                ((CollectHandler<Object>)controls).setKeyValue(values);
            
        }
    }
    
//    private boolean IsDate(Object obj, Class<? extends Object> clazz) {
//        try {
//            if (clazz.equals(Byte.class) ||
//                    clazz.equals(Double.class) ||
//                    clazz.equals(Float.class) ||
//                    clazz.equals(Integer.class) ||
//                    clazz.equals(Long.class) ||
//                    clazz.equals(Short.class) ||
//                    clazz.equals(Boolean.class) ||
//                    clazz.equals(String.class))
//                return false;
//
//            else
//                return true;
//
//        } catch (NumberFormatException nfe) {
//            return false;
//        }
//    }
}
