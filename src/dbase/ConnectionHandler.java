/*
 * ConnectionHandler.java
 *
 * Created on June 15, 2007, 4:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package dbase;

/**
 *
 * @author tigers
 */
public class ConnectionHandler {

    private static String Server;
    private static String Database;
    private static String Password;
    private static String UserAcct;
    private static String selectMethod;
    private static short dbType;
    public final static short dbMySQL = 1;
    public final static short dbMSSQL = 2;
    public final static short dbPGSQL = 3;

    /** Creates a new instance of ConnectionHandler */
    public ConnectionHandler(String server, String user, String password, String database, short dbType) {
        try {
            switch (dbType) {
                case dbMySQL:
                    Class.forName("com.mysql.jdbc.Driver");
                    break;

                case dbMSSQL:
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); // 2000 version
//                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    break;

                case dbPGSQL:
                    Class.forName("org.postgresql.Driver");
                    break;
            }
            ConnectionHandler.dbType = dbType;

            Server = server;
            Password = (password.compareTo("`") == 0 ? "" : password);
            UserAcct = user;
            Database = database;
            selectMethod = "cursor";

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public java.sql.Connection OpenLink() throws java.sql.SQLException {
        return OpenLink(0);
    }
    public java.sql.Connection OpenLink(int port) throws java.sql.SQLException {
        switch (dbType) {
            case dbMySQL:
                return java.sql.DriverManager.getConnection("jdbc:mysql://" + Server + ":" + (port != 0 ? port : 3306) + "/" + Database, UserAcct, Password);

            case dbMSSQL:                
//                com.microsoft.sqlserver.jdbc.SQLServerDataSource ds = new com.microsoft.sqlserver.jdbc.SQLServerDataSource();
//                ds.setServerName(Server);
//                ds.setPortNumber((port != 0 ? port : 1433));
//                ds.setDatabaseName(Database);
//                ds.setUser("sa");
//                ds.setPassword(Password);
//                return ds.getConnection();
                String connectionURL="jdbc:microsoft:sqlserver://" + Server + ":" + (port != 0 ? port : 1433) + ";databaseName=" + Database + ";selectMethod=" + selectMethod ;
                return java.sql.DriverManager.getConnection(connectionURL,UserAcct,Password);                

            default:
                return java.sql.DriverManager.getConnection("jdbc:postgresql://" + Server + ":" + (port != 0 ? port : 5432) + "/" + Database, UserAcct, Password);
        }
    }
}
