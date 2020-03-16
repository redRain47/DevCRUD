package ua.redrain47.devcrud.test_util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class TempDbManager {
    private static final String TEST_INIT_DB_SQL_PATH = "./src/main/resources" +
            "/db/initDB.sql";
    private static final String TEST_POPULATE_DB_SQL_PATH = "./src/main/resources" +
            "//db//populateDB.sql";
    private ScriptRunner scriptRunner;

    public TempDbManager(Connection connection) {
        this.scriptRunner = new ScriptRunner(connection);
    }

    public void createDatabase() throws IOException {
        scriptRunner.runScript(new FileReader(TEST_INIT_DB_SQL_PATH));
    }

    public void populateDatabase() throws IOException {
        scriptRunner.runScript(new FileReader(TEST_POPULATE_DB_SQL_PATH));
    }
}
