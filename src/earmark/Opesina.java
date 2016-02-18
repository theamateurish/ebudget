package earmark;

/**
 *
 * @author felix
 */
public class Opesina {

    private final Integer mCode;
    private final String mUID, mNgalan, mRequest;
    private final Short  mSubID;
    private java.util.List<Opesina> aSubOffcList;

    public Opesina(String uid, String ngalan, Integer code, Short sub, String request, java.util.List<Opesina> suboffc) {
        mUID = uid; mNgalan = ngalan; mCode = code; mSubID = sub; aSubOffcList = suboffc; mRequest = request;
    }

    public java.util.List<Opesina> getSubOffcList() {return aSubOffcList;}
    public void setSubOffcList(java.util.List<Opesina> value) {aSubOffcList = value;}

    //other setters and getters
    public String getUID() {return mUID;}
    //public void setUID(String value) {mUID = value;}
    
    public String getNgalan() {return mNgalan;}
    //public void setNgalan(String value) {mNgalan = value;}
    
    public int getCode() {return mCode;}
    //public void setCode(int value) {mCode = value;}

    public Short getSubid() {return mSubID;}
    //public void setSubid(Short value) {mSubID = value;}
    
    public String getRequest() {return mRequest;}
    
}
