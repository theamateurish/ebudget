
package connect;

import classes.MyProject;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class DBConnection {
    private Connection mJDBC;
    private static String serverName = System.getProperty("SERVER");
    private static String databaseName= "financial";
    private static String userName = "postgres";
    private static String password="millennium@2000";   
//    private static String password="postgre";
    private static Integer portNo=5432;
    
            
    public static boolean main(String args[]) {
        serverName=args[0];
//        password=args[1];
//        portNo=Integer.parseInt(args[2]);
       
        
        try {

            if ((new java.io.FileOutputStream(System.getProperty("java.io.tmpdir") + "/"+new MyProject().getTitle()+".file")).getChannel().tryLock() == null) {
                JOptionPane.showMessageDialog(null, new MyProject().getTitle()+" is already open.");
                System.exit(0);
            }

        } catch (java.net.BindException exc) {
            // Another process is listening on that port.
            // Probably anoher instance of your app.
            JOptionPane.showMessageDialog(null, new MyProject().getTitle()+" is already open.");
            return true;
        } catch (java.io.IOException exc) {
            // could not listen on that port for some other reason
            return true;
        }        
        return false;
    }
    
    public DBConnection() throws java.sql.SQLException, ClassNotFoundException {
        
        Class.forName("org.postgresql.Driver");
        String dbUrl="jdbc:postgresql://"+serverName+":"+portNo+"/"+databaseName;
        mJDBC=DriverManager.getConnection(dbUrl,userName,password);
    }
    
    public java.sql.ResultSet executeQuery(String sql) throws java.sql.SQLException {return mJDBC.createStatement().executeQuery(sql);}

    public boolean execute(String sql) throws java.sql.SQLException {return mJDBC.createStatement().execute(sql);}

    public int executeUpdate(String sql) throws java.sql.SQLException {
        return mJDBC.createStatement().executeUpdate(sql);
    }

    public void close() throws java.sql.SQLException {mJDBC.close();}

    public void setAutoCommit(boolean autoCommit) throws java.sql.SQLException {mJDBC.setAutoCommit(autoCommit);}

    public void commit() throws java.sql.SQLException {mJDBC.commit();}

    public void rollback() throws java.sql.SQLException {mJDBC.rollback();}
    
    public java.sql.PreparedStatement prepareStatement(String value) throws java.sql.SQLException {  return mJDBC.prepareStatement(value);}
    
    public java.sql.Connection getLink() throws java.sql.SQLException { return mJDBC; }

    public static void connectDB(String database,String user,String pass) throws java.sql.SQLException,ClassNotFoundException {        
        Class.forName("org.postgresql.Driver");
        String dbUrl="jdbc:postgresql://"+serverName+":"+portNo+"/"+database;
        DriverManager.getConnection(dbUrl,user,pass);
    }
    
    public static String getServerName() {return serverName;}

    public static String getDatabaseName() {return databaseName;}

    public static String getUserName() {return userName;}

    public static String getPassword() {return password;}         
    
    public static Integer getPort() {return portNo;} 
    
}
