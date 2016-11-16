package com.anthonykim.smartfactory.imdg.db;

public class MySQLConnectionManager extends ConnectionManager {
    public MySQLConnectionManager() {
        super("mysql");
        final String JDBCDriver = "com.mysql.jdbc.Driver";
        final String JDBCDriverType = "jdbc:mysql://";
        final String URL = JDBCDriverType + dbServer + ":" + port + "/" + dbName;
        connMgr = DBConnectionPoolManager.getInstance();
        connMgr.init(poolName, JDBCDriver, URL, userID, password, maxConn, initConn, maxWait);
    }
}