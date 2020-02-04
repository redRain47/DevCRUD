package ua.redrain47.hw11.test_util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionUtil {
    private static BasicDataSource basicDataSource = new BasicDataSource();
    private static final String DATABASE_URL = "jdbc:h2:mem:test;MODE=MYSQL"; //;DB_CLOSE_ON_EXIT=TRUE;FILE_LOCK=NO
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    static {
        basicDataSource.setUrl(DATABASE_URL);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

    private H2ConnectionUtil() {}
}
