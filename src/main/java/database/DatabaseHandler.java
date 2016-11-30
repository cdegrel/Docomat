package database;

import com.ibatis.common.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String PATH_DATABASE = "jdbc:sqlite:db/docomat.db";
    private static final String PATH_SCRIPT_SQL = "db/create.sql";
    private static Connection instance;

    private DatabaseHandler() {
        if(new File("db/docomat.db").exists()){
            connection();
            return;
        }
        connection();
        createDatabase();
    }

    public static synchronized Connection getInstance() {
        if(instance == null) new DatabaseHandler();
        return instance;
    }

    private void connection(){
        try {
            Class.forName("org.sqlite.JDBC");
            instance = DriverManager.getConnection(PATH_DATABASE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase() {
        ScriptRunner sr = new ScriptRunner(instance, false, false);
        try {
            Reader reader = new BufferedReader(new FileReader(PATH_SCRIPT_SQL));
            sr.runScript(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
