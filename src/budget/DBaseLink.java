package budget;

/**
 *
 * @author felix
 */
public class DBaseLink implements org.postgresql.core.BaseConnection {

    private final  org.postgresql.core.BaseConnection mJDBC;

    private static final String SERVER = System.getProperty("SERVER");

//    protected static void setServerName(String server) {SERVER = server;}
    
    public DBaseLink() throws java.sql.SQLException {
        org.postgresql.ds.PGSimpleDataSource pg = new org.postgresql.ds.PGSimpleDataSource();
        pg.setServerName(SERVER);
        //pg.setPortNumber(port != 0 ? port : 5432);
        pg.setDatabaseName("financial");
        pg.setUser("postgres");
        pg.setPassword("millennium@2000");
        //pg.setSsl(true);
        mJDBC = (org.postgresql.core.BaseConnection)pg.getConnection();
    }
    
    @Override
    public void cancelQuery() throws java.sql.SQLException {
        mJDBC.cancelQuery();
    }

    @Override
    public java.sql.ResultSet execSQLQuery(String string) throws java.sql.SQLException {
        return mJDBC.execSQLQuery(string);
    }

    @Override
    public java.sql.ResultSet execSQLQuery(String string, int i, int i1) throws java.sql.SQLException {
        return mJDBC.execSQLQuery(string, i, i1);
    }

    @Override
    public void execSQLUpdate(String string) throws java.sql.SQLException {
        mJDBC.execSQLUpdate(string);
    }

    @Override
    public org.postgresql.core.QueryExecutor getQueryExecutor() {
        return mJDBC.getQueryExecutor();
    }

//    @Override
//    public Object getObject(String string, String string1, byte[] bytes) throws java.sql.SQLException {
//        return mJDBC.getObject(string, string1, bytes);
//    }

    @Override
    public org.postgresql.core.Encoding getEncoding() throws java.sql.SQLException {
        return mJDBC.getEncoding();
    }

    @Override
    public org.postgresql.core.TypeInfo getTypeInfo() {
        return mJDBC.getTypeInfo();
    }

    @Override
    public boolean haveMinimumCompatibleVersion(String string) {
        return mJDBC.haveMinimumCompatibleVersion(string);
    }

    @Override
    public boolean haveMinimumServerVersion(String string) {
        return mJDBC.haveMinimumServerVersion(string);
    }

    @Override
    public byte[] encodeString(String string) throws java.sql.SQLException {
        return mJDBC.encodeString(string);
    }

    @Override
    public String escapeString(String string) throws java.sql.SQLException {
        return mJDBC.escapeString(string);
    }

    @Override
    public boolean getStandardConformingStrings() {
        return mJDBC.getStandardConformingStrings();
    }

    @Override
    public org.postgresql.jdbc2.TimestampUtils getTimestampUtils() {
        return mJDBC.getTimestampUtils();
    }

    @Override
    public org.postgresql.core.Logger getLogger() {
        return mJDBC.getLogger();
    }

    @Override
    public boolean getStringVarcharFlag() {
        return mJDBC.getStringVarcharFlag();
    }

    @Override
    public int getTransactionState() {
        return mJDBC.getTransactionState();
    }

//    @Override
//    public boolean binaryTransferSend(int ent) {
//        return mJDBC.binaryTransferSend(ent);
//    }

    @Override
    public org.postgresql.PGNotification[] getNotifications() throws java.sql.SQLException {
        return mJDBC.getNotifications();
    }

    //@Override
//    public org.postgresql.copy.CopyManager getCopyAPI() throws java.sql.SQLException {
//        return getCopyAPI();
//    }

    @Override
    public org.postgresql.largeobject.LargeObjectManager getLargeObjectAPI() throws java.sql.SQLException {
        return mJDBC.getLargeObjectAPI();
    }

    @Override
    public org.postgresql.fastpath.Fastpath getFastpathAPI() throws java.sql.SQLException {
        return mJDBC.getFastpathAPI();
    }

    @Override
    @Deprecated
    public void addDataType(String string, String string1) {
        mJDBC.addDataType(string, string1);
    }

    @Override
    public void addDataType(String string, Class type) throws java.sql.SQLException {
        mJDBC.addDataType(string, type);
    }

    @Override
    public void setPrepareThreshold(int ent) {
        mJDBC.setPrepareThreshold(ent);
    }

    @Override
    public int getPrepareThreshold() {
        return mJDBC.getPrepareThreshold();
    }

    @Override
    public java.sql.Statement createStatement() throws java.sql.SQLException {
        return mJDBC.createStatement();
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws java.sql.SQLException {
        return mJDBC.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws java.sql.SQLException {
        mJDBC.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws java.sql.SQLException {
        return mJDBC.getAutoCommit();
    }

    @Override
    public void commit() throws java.sql.SQLException {
        mJDBC.commit();
    }

    @Override
    public void rollback() throws java.sql.SQLException {
        mJDBC.rollback();
    }

    @Override
    public void close() throws java.sql.SQLException {
        mJDBC.close();
    }

    @Override
    public boolean isClosed() throws java.sql.SQLException {
        return mJDBC.isClosed();
    }

    @Override
    public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
        return mJDBC.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws java.sql.SQLException {
        mJDBC.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws java.sql.SQLException {
        return mJDBC.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws java.sql.SQLException {
        mJDBC.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws java.sql.SQLException {
        return mJDBC.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws java.sql.SQLException {
        mJDBC.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws java.sql.SQLException {
        return mJDBC.getTransactionIsolation();
    }

    @Override
    public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
        return mJDBC.getWarnings();
    }

    @Override
    public void clearWarnings() throws java.sql.SQLException {
        mJDBC.clearWarnings();
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public java.util.Map<String, Class<?>> getTypeMap() throws java.sql.SQLException {
        return mJDBC.getTypeMap();
    }

    @Override
    public void setTypeMap(java.util.Map<String, Class<?>> map) throws java.sql.SQLException {
        mJDBC.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws java.sql.SQLException {
        mJDBC.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws java.sql.SQLException {
        return mJDBC.getHoldability();
    }

    @Override
    public java.sql.Savepoint setSavepoint() throws java.sql.SQLException {
        return mJDBC.setSavepoint();
    }

    @Override
    public java.sql.Savepoint setSavepoint(String name) throws java.sql.SQLException {
        return mJDBC.setSavepoint(name);
    }

    @Override
    public void rollback(java.sql.Savepoint savepoint) throws java.sql.SQLException {
        mJDBC.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(java.sql.Savepoint savepoint) throws java.sql.SQLException {
        mJDBC.releaseSavepoint(savepoint);
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws java.sql.SQLException {
        return mJDBC.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, columnIndexes);
    }

    @Override
    public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws java.sql.SQLException {
        return mJDBC.prepareStatement(sql, columnNames);
    }

    @Override
    public java.sql.Clob createClob() throws java.sql.SQLException {
        return mJDBC.createClob();
    }

    @Override
    public java.sql.Blob createBlob() throws java.sql.SQLException {
        return mJDBC.createBlob();
    }

    @Override
    public java.sql.NClob createNClob() throws java.sql.SQLException {
        return mJDBC.createNClob();
    }

    @Override
    public java.sql.SQLXML createSQLXML() throws java.sql.SQLException {
        return mJDBC.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws java.sql.SQLException {
        return mJDBC.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws java.sql.SQLClientInfoException {
        mJDBC.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(java.util.Properties properties) throws java.sql.SQLClientInfoException {
        mJDBC.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws java.sql.SQLException {
        return mJDBC.getClientInfo(name);
    }

    @Override
    public java.util.Properties getClientInfo() throws java.sql.SQLException {
        return mJDBC.getClientInfo();
    }

    @Override
    public java.sql.Array createArrayOf(String typeName, Object[] elements) throws java.sql.SQLException {
        return mJDBC.createArrayOf(typeName, elements);
    }

    @Override
    public java.sql.Struct createStruct(String typeName, Object[] attributes) throws java.sql.SQLException {
        return mJDBC.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws java.sql.SQLException {
        mJDBC.setSchema(schema);
    }

    @Override
    public String getSchema() throws java.sql.SQLException {
        return mJDBC.getSchema();
    }

    @Override
    public void abort(java.util.concurrent.Executor executor) throws java.sql.SQLException {
        mJDBC.abort(executor);
    }

    @Override
    public void setNetworkTimeout(java.util.concurrent.Executor executor, int milliseconds) throws java.sql.SQLException {
        mJDBC.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws java.sql.SQLException {
        return mJDBC.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
        return mJDBC.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
        return mJDBC.isWrapperFor(iface);
    }

    @Override
    public org.postgresql.copy.CopyManager getCopyAPI() throws java.sql.SQLException {
        return mJDBC.getCopyAPI();
    }

    @Override
    public Object getObject(String string, String string1, byte[] bytes) throws java.sql.SQLException {
        return mJDBC.getObject(string, string1, bytes);
    }

    @Override
    public boolean binaryTransferSend(int i) {
        return mJDBC.binaryTransferSend(i);
    }
    
}
