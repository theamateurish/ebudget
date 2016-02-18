/*
 * VERSION 1.0 CLASS
 * BEGIN
 *   MultiUse = -1  'True
 *   Persistable = 0  'NotPersistable
 *   DataBindingBehavior = 0  'vbNone
 *   DataSourceBehavior  = 0  'vbNone
 *   MTSTransactionMode  = 0  'NotAnMTSObject
 * END
 */

package utility;
/**
 * Attribute VB_Name = "ClassWordNum"
 * Attribute VB_GlobalNameSpace = False
 * Attribute VB_Creatable = True
 * Attribute VB_PredeclaredId = False
 * Attribute VB_Exposed = True
 * Attribute VB_Ext_KEY = "SavedWithClassBuilder6" ,"Yes"
 * Attribute VB_Ext_KEY = "Top_Level" ,"Yes"
 */
public class WordNum {
    private String[] words = {" One", " Two", " Three", " Four", " Five"," Six", " Seven", " Eight", " Nine",
                              " Ten", " Eleven", " Twelve", " Thirten", " Fourten", " Fiften", " Sixten", " Seventen", " Eighten", " Nineten",
                              " Twenty", "", "", "", "", "", "", "", "", "", " Thirty", "", "", "", "", "", "", "", "", "",
                              " Forty", "", "", "", "", "", "", "", "", "", " Fifty", "", "", "", "", "", "", "", "", "",
                              " Sixty",  "", "", "", "", "", "", "", "", "", " Seventy",  "", "", "", "", "", "", "", "", "",
                              " Eighty", "", "", "", "", "", "", "", "", "", " Ninety"
                             },
                     places = {"", " Thousand", " Million", " Billion", " Thrillion"};

    public String CurrencyToWord(String amount, int decimallen, String currency) {
        if (currency == null) currency = "Pesos";
        if (currency.isEmpty()) currency = "Pesos";
        
        amount = amount.replaceAll("-", "");
        amount = amount.replaceAll(",", "");
        decimallen = java.lang.Math.abs(decimallen);
        
        String temp, decimal = "", balor, figure;
        int post, length;
        java.util.ArrayList<String> marr = new java.util.ArrayList<String>(), sarr = new java.util.ArrayList<String>();

        
        if (amount.isEmpty()) return "(0) zero";
        if (Double.valueOf(amount) == 0d) return "(0) zero";


        post = amount.indexOf(".");

        if (post > 0) {
            temp = amount.substring(0, post);

            decimal = amount.substring(post + 1); //, post + 3);
            if (decimal.length() > decimallen)
                decimal = decimal.substring(0, decimallen);
            else
                decimal += Replicate(decimallen - decimal.length(), '0');
            length = temp.length();
            
        } else {
            temp = amount;
            length = temp.length();
        }

        
        while (length > 0) {
            if (length >= 2) {
                if (length == 2)
                    marr.add(temp.substring(0, 2));
                else
                    marr.add(temp.substring((length - 3), (length - 3) + 3));
                
                length -= 3;

            } else {
                marr.add(temp.substring(0, length));
                length -= 1;
            }
        }

        balor = "";
        for (int ctr = 0; ctr < marr.size(); ctr++) {
            figure = marr.get(ctr);
            
            sarr.clear();
            for (post = 0; post < figure.length(); post++)
                sarr.add(figure.substring(post, post + 1));
            

            length = sarr.size();
            temp = "";
            if (length == 3) {
                if (!sarr.get(0).equals("0")) 
                    temp = words[Integer.parseInt(sarr.get(0)) - 1] + " Hundred";
                
                temp += processTen(sarr.get(1), sarr.get(2));

            } else if (length == 2)
                temp += processTen(sarr.get(0), sarr.get(1));
            else
                temp += words[Integer.parseInt(sarr.get(0)) - 1];
            
            if (temp.length() > 0)
                balor = temp + places[ctr] + balor;
        }

        balor += " " + currency;

        if (decimal.length() > 0)
            return balor + " and " + decimal + "/" + "1" + Replicate(decimallen, '0') + " centavo(s)";
        else
            return balor;
    }

//'///////////////////////////////////////////////////////////////
//'Private Function denominator(declen As Byte) As String
//'    Dim temp As String, i As Byte
//'
//'    denominator = "1" + String(declen, "0")
//'    For i = 1 To declen
//'        temp = temp + "0"
//'    Next i
//'
//'   denominator = temp
//'End Function
//'///////////////////////////////////////////////////////////////

    private String processTen(String sub1, String sub2) {
        if (sub1.equals("0")) {
            if (sub2.equals("0"))
                return "";
            else
                return words[Integer.parseInt(sub2) - 1];

        } else if (!sub1.equals("1")) {
            if (!sub2.equals("0"))
                return words[Integer.parseInt(sub1 + "0") - 1] + words[Integer.parseInt(sub2) - 1];
            else
                return words[Integer.parseInt(sub1 + sub2) - 1];
        } else {
            if (sub2.equals("0"))
                return words[9];
            else
                return words[Integer.parseInt(sub1 + sub2) - 1];
        }
    }

    private String Replicate(int pieces, char character) {
        if (pieces <= 0) return "";
        
        char[] arr = new char[pieces];
        java.util.Arrays.fill(arr, character);
        return String.valueOf(arr);
    }
}