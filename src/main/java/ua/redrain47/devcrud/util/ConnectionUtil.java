package ua.redrain47.devcrud.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static BasicDataSource basicDataSource = new BasicDataSource();
    private static Properties properties = new Properties();

    static {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("./src/main/resources/" +
                            "db/db_connection.properties"));
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        basicDataSource.setUrl(properties.getProperty("url"));
        basicDataSource.setUsername(properties.getProperty("username"));
        basicDataSource.setPassword(properties.getProperty("password"));
    }

    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }
}
