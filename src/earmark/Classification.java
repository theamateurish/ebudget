package earmark;

/**
 *
 * @author felix
 */
public class Classification {

    private final String UID, Titulo, ParendID;
    private final Double Balances;
    private java.util.List<Classification> aChartList;

    public Classification(String uid, String titulo, Double balance, String rootID, java.util.List<Classification> charts) {
        UID = uid; Titulo = titulo; aChartList = charts; Balances = balance; ParendID = rootID;
    }

    public java.util.List<Classification> getChartList() {return aChartList;}
    public void setChartList(java.util.List<Classification> value) {aChartList = value;}

    //other setters and getters
    public String getUID() {return UID;}
    
    public String getTitulo() {return Titulo;}

    public Double getBalances() {return Balances;}

    public String getRootID() {return ParendID;}
}
