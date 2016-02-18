package jrscripplet;

/**
 *
 * @author theamateurish
 */
public class StringUtilities extends net.sf.jasperreports.engine.JRDefaultScriptlet{
    
   public String CapsFirst(String str) {
    String[] words = str.split(" ");
    StringBuilder ret = new StringBuilder();
    for(int i = 0; i < words.length; i++) {
        
            
            
            if(words[i].contains("(") | words[i].contains(")")){
                ret.append(words[i].toUpperCase());
            }else{
                ret.append(Character.toUpperCase(words[i].charAt(0)));
                ret.append(words[i].substring(1));
            }
            
            if(i < words.length - 1) {
                ret.append(' ');
            }
        
    }
    return ret.toString();
}
}
